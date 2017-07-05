package com.example.ahmedatia.myproject

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by ahmedatia on 04/07/2017.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}
