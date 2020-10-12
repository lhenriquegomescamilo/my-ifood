package com.camilo.myifood.register.restaurant.dto

import com.camilo.myifood.register.restaurant.models.Restaurant
import org.mapstruct.Mapper

@Mapper(componentModel = "cdi")
interface RestaurantMapper {

    fun toRestaurant(dto: CreateNewRestaurantDTO): Restaurant
}