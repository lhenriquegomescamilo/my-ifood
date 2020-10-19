package com.camilo.myifood.register.restaurant.infra

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPE, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidDTOValidator::class])
@MustBeDocumented
annotation class ValidDTO(
    val message: String = "{com.camilo.myifood.register.restaurant.infra.ValidDTO.message}",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)