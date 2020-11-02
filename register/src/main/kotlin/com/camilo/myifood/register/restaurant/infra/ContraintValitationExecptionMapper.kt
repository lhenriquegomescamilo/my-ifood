package com.camilo.myifood.register.restaurant.infra

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

//@Provider
open class ContraintValitationExecptionMapper: ExceptionMapper<ConstraintViolationException> {
    override fun toResponse(exception: ConstraintViolationException) = Response.status(Response.Status.BAD_REQUEST)
            .entity(ConstraintViolationResponse.of(exception)).build()
}