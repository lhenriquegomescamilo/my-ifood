package com.camilo.myifood.models

data class Restaurant(
    val id: Long,
    val name: String,
    val localization: Localization,
    val food: Food
)