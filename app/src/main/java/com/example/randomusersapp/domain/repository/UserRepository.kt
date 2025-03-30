package com.example.randomusersapp.domain.repository

import com.example.randomusersapp.domain.model.RandomUsersResponse
import retrofit2.Response

interface UserRepository {

    suspend fun getUserList(results: Int): Response<RandomUsersResponse>

}