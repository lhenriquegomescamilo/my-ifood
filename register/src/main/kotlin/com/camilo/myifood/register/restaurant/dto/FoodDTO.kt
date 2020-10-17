package com.camilo.myifood.register.restaurant.dto

import java.math.BigDecimal

data class FoodDTO(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val restaurant: RestaurantDTO? = null,

    val price: BigDecimal? = null,
    val idRestaurant: Long? = null
)