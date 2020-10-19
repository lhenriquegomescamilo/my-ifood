package com.camilo.myifood.register.restaurant.dto

import com.camilo.myifood.register.restaurant.infra.DTO
import com.camilo.myifood.register.restaurant.models.Restaurant
import org.hibernate.validator.constraints.br.CNPJ
import java.util.Objects.nonNull
import javax.validation.ConstraintValidatorContext
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
) : DTO {
    override fun isValid(contraintValidationContext: ConstraintValidatorContext): Boolean {
        contraintValidationContext.disableDefaultConstraintViolation()
        return if (nonNull(cnpj) && Restaurant.find("cnpj", cnpj!!).count() > 0) {
            contraintValidationContext.buildConstraintViolationWithTemplate("CNPJ already exists")
                .addPropertyNode("cnpj")
                .addConstraintViolation()
            true
        } else {
            false
        }
    }
}