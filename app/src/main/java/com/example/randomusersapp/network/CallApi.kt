package com.example.randomusersapp.network

import com.example.randomusersapp.domain.model.RandomUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CallApi {

    @GET("api/")
    suspend fun getRandomUsers(@Query("results") results: Int): Response<RandomUsersResponse>
}