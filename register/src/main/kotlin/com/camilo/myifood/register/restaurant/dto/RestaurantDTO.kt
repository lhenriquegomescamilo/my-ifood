package com.camilo.myifood.register.restaurant.dto

import com.camilo.myifood.register.restaurant.models.Location
import java.util.*

data class RestaurantDTO(
    var id: Long? = null,
    var owner: String? = null,
    var cnpj: String? = null,
    var name: String? = null,
    var location: Location? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var foods: List<FoodDTO>? = null
)
