package com.camilo.myifood.register.restaurant.dto

import javax.validation.constraints.NotEmpty

class LocationDTO(
    @NotEmpty
    val latitude: Double? = null,
    @NotEmpty
    val longitude: Double? = null
) {
    constructor() : this(null, null)
}
