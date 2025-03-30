package com.example.randomusersapp.domain.model

import java.io.Serializable

data class RandomUsersResponse(
    val info: Info,
    val results: List<Result>
): Serializable