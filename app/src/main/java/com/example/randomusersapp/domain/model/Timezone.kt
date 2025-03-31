package com.example.randomusersapp.domain.model

import java.io.Serializable

data class Timezone(
    val description: String,
    val offset: String
): Serializable