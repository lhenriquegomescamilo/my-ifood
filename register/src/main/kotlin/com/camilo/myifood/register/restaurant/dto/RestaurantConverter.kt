package com.camilo.myifood.register.restaurant.dto

import com.camilo.myifood.register.restaurant.models.Location
import com.camilo.myifood.register.restaurant.models.Restaurant

class RestaurantConverter {
    companion object {
        fun toRestaurant(dto: CreateRestaurantDTO) = Restaurant(
            owner = dto.owner,
            cnpj = dto.cnpj,
            name = dto.name,
            location = Location(latitude = dto.location?.latitude, longitude = dto.location?.longitude)
        )
    }
}