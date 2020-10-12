package com.camilo.myifood.register.restaurant.dto

import org.hibernate.validator.constraints.br.CNPJ
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

class CreateOrUpdateRestaurantDTO(

    @NotEmpty
    var owner: String? = null,

    @NotEmpty
    @CNPJ
    var cnpj: String? = null,

    @NotEmpty
    @Size(min = 2, max = 30)
    var name: String? = null,

    var location: LocationDTO? = null
) {
}