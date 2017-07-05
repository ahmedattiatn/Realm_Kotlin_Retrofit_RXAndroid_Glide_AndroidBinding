package com.example.ahmedatia.myproject

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by ahmedatia on 05/07/2017.
 */
interface GithubService {
    @GET("users/{username}")
    fun GetGithubUser(@Path("username") username: String): Observable<GithubModel>
}
