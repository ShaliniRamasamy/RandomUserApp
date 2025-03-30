package com.example.randomusersapp.domain.model

import java.io.Serializable

data class Location(
    val city: String,
    val coordinates: Coordinates?= null,
    val country: String,
    val postcode: String,
    val state: String,
    val street: Street,
    val timezone: Timezone?= null
): Serializable