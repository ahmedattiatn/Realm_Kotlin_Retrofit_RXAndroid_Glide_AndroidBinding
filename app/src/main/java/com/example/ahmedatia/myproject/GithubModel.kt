package com.example.ahmedatia.myproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by ahmedatia on 05/07/2017.
 */
@RealmClass
//L'annotation "open" sur une classe est l'inverse de  "final" de Java: elle permet
// à d'autres d'hériter de cette classe.
// Par défaut, toutes les classes de Kotlin sont "final"
open class GithubModel : RealmObject() {
    //PrimaryKey annotation is also a Realm annotation
    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int = 0

    //avatar_url
    // name
    @SerializedName("name")
    @Expose
    open var name: String? = null
    //To allow nulls, we can declare a variable as nullable string, written String?:

    @SerializedName("avatar_url")
    @Expose
    open var avatar_url: String? = null
}