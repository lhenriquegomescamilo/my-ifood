package com.camilo.myifood.register.restaurant.infra

import javax.validation.ConstraintViolationException

data class ConstraintViolationResponse (
        val constraintViolationException: ConstraintViolationException? = null,
        val violations: List<ConstraintViolationImpl>? = constraintViolationException?.constraintViolations?.map { ConstraintViolationImpl.of(violation = it) }

) {
    companion object {
        fun of(exception: ConstraintViolationException) = ConstraintViolationResponse(exception)
    }


}