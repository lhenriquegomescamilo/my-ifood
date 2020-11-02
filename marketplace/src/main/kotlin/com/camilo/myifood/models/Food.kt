package com.camilo.myifood.models

import java.math.BigDecimal

data class Food(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val restaurant: Restaurant? = null,
    val price: BigDecimal? = null,
    val idRestaurant: Long? = null
)
