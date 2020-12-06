package com.camilo.myifood.register.restaurant.dto

import javax.validation.constraints.NotEmpty

data class LocationDTO(
    @NotEmpty
    var latitude: Double? = null,
    @NotEmpty
    var longitude: Double? = null
) {
}
