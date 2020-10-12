package com.camilo.myifood.register.restaurant.dto

class LocationDTO(
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    constructor() : this(null, null)
}
