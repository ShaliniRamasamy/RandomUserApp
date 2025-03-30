package com.example.randomusersapp.data.repository

import com.example.randomusersapp.domain.repository.UserRepository
import com.example.randomusersapp.domain.model.RandomUsersResponse
import com.example.randomusersapp.network.CallApi
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class UserRepositoryImpl(private var api: CallApi): UserRepository {

    override suspend fun getUserList(results: Int): Response<RandomUsersResponse> {
        val response = try {
            api.getRandomUsers(results)
        } catch (e: Exception) {
//            return null
        }
        return response as Response<RandomUsersResponse>
    }
}