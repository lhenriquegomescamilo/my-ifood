package com.camilo.myifood.register.restaurant.infra

import javax.validation.ConstraintViolationException

class ConstraintViolationResponse(
    var constraintViolationException: ConstraintViolationException? = null,
    var violations: List<ConstraintViolationImpl>? = constraintViolationException?.constraintViolations?.map {
        ConstraintViolationImpl.of(
            violation = it
        )
    }

) {
    companion object {
        fun of(exception: ConstraintViolationException) =
            ConstraintViolationResponse(constraintViolationException = exception)
    }
}