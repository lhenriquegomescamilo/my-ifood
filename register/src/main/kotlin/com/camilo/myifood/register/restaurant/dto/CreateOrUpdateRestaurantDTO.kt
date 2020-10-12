package com.camilo.myifood.register.restaurant.dto

class CreateOrUpdateRestaurantDTO(
    var owner: String? = null,

    var cnpj: String? = null,

    var name: String? = null,

    var location: LocationDTO? = null
) {
    constructor() : this(null, null, null, null)
}