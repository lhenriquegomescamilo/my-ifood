package com.camilo.myifood.register.restaurant.dto

class CreateNewRestaurantDTO(
    var owner: String? = null,

    var cnpj: String? = null,

    var name: String? = null,

    var location: LocationDTO? = null
) {
    constructor() : this(null, null, null, null)
}