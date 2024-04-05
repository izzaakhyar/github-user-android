package com.example.githubuserapp.data.retrofit

import com.example.githubuserapp.data.response.GithubUserResponse
import com.example.githubuserapp.data.response.UserDetailResponse
import com.example.githubuserapp.data.response.UserFollowResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUserGithub(
        @Query("q") q: String
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getDetailUserFollowers(@Path("username") username: String): Call<List<UserFollowResponseItem>>

    @GET("users/{username}/following")
    fun getDetailUserFollowing(@Path("username") username: String): Call<List<UserFollowResponseItem>>
}