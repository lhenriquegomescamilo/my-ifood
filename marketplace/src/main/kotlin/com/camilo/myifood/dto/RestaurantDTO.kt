package com.camilo.myifood.dto

import java.util.*

data class RestaurantDTO(
    var id: Long? = null,

    var owner: String? = null,

    var cnpj: String? = null,

    var name: String? = null,

    var createdAt: Date? = null,

    var updatedAt: Date? = null,

    var foods: List<FoodDTO>? = null
)
