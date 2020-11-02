package com.camilo.myifood.register.restaurant.infra

import org.eclipse.microprofile.openapi.annotations.media.Schema
import javax.validation.ConstraintViolation

open class ConstraintViolationImpl (

        @Schema(description = "Path of attribute, example: startDate, endDate or person.address.number", required = false)
        var attribute: String? = "",

        @Schema(description = "Message to show what's happen with the validation associate with the ", required = false)
        var message: String? = "",
        var violation: ConstraintViolation<*>? = null
) {
    init {
        violation?.let {
            message = it.message
            attribute = it.propertyPath.toString().split("\\.").drop(2).joinToString(".")
        }

    }

    companion object {
        fun of(violation: ConstraintViolation<*>, violationAsString: String? = "") = ConstraintViolationImpl(violation = violation)
    }
}