package com.camilo.myifood.register.restaurant.infra

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValidDTOValidator: ConstraintValidator<ValidDTO, DTO> {
    override fun initialize(constraintAnnotation: ValidDTO?) {
        super.initialize(constraintAnnotation)
    }

    override fun isValid(dto: DTO, contraintValidatorContext: ConstraintValidatorContext) = dto.isValid(contraintValidatorContext)

}