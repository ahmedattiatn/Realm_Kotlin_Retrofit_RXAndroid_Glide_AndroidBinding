package com.example.ahmedatia.myproject

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.ahmedatia.myproject.databinding.ActivityMainBinding
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)
        // Because of Realm we use a special Gson instance, which basically adds an exclusion
        // strategy for skipping Realm generated fields. Otherwise Gson doesn’t work
        // with the model.
        val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }

            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaredClass == RealmObject::class.java
            }
        }).create()

        val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/")
                .build()

        val gitHubService: GithubService = retrofit.create(GithubService::class.java)

        val realm = Realm.getDefaultInstance()

        val savedUser: GithubModel? = RealmQuery.createQuery(realm,
                GithubModel::class.java).findFirst()

        updateViews(binding, savedUser)

        gitHubService.GetGithubUser("ahmedrizwan")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            user->
                            realm.beginTransaction()
                            realm.copyToRealmOrUpdate(user)
                            realm.commitTransaction()
                            updateViews(binding, user)

                        },
                        {
                            error ->
                            Log.e("Eroor", error.message)
                        }

                )


    }
    // my User NAme is null
    //https://api.github.com/users/ahmedattiatn

    private fun updateViews(binding: ActivityMainBinding, savedUser: GithubModel?) {
        Glide.with(this).load(savedUser?.avatar_url).into(binding.userImage)
        binding.userName.text = savedUser?.name
                .toString()
    }
}
