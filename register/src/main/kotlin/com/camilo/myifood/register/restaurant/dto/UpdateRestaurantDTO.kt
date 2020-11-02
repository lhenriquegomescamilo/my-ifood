package com.camilo.myifood.register.restaurant.dto

class UpdateRestaurantDTO(

    var owner: String? = null,

    var cnpj: String? = null,

    var name: String? = null,

    var location: LocationDTO? = null
)