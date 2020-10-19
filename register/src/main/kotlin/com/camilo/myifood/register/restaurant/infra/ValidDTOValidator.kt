package com.camilo.myifood.register.restaurant.infra

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValidDTOValidator: ConstraintValidator<ValidDTO, DTO> {
    override fun isValid(dto: DTO, contraintValidatorContext: ConstraintValidatorContext) = dto.isValid(contraintValidatorContext)

}