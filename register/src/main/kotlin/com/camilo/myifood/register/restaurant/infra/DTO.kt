package com.camilo.myifood.register.restaurant.infra

import javax.validation.ConstraintValidatorContext

interface DTO {
    fun isValid(contraintValidationContext: ConstraintValidatorContext) = true
}