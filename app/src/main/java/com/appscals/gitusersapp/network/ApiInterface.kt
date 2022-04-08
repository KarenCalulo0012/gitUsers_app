package com.appscals.gitusersapp.network

import com.appscals.gitusersapp.model.User
import com.appscals.gitusersapp.model.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    companion object {
        var apiService: ApiInterface? = null
        fun getInstance(): ApiInterface {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiInterface::class.java)
            }
            return apiService!!
        }
    }

    @GET("users")
    suspend fun getAllUsers(): List<User>

    @GET("users/{username}")
    suspend fun getUserInfo(@Path("username") username: String): UserInfo
}