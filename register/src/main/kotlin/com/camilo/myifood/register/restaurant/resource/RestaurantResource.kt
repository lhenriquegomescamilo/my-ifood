package com.camilo.myifood.register.restaurant.resource

import com.camilo.myifood.register.restaurant.dto.CreateOrUpdateRestaurantDTO
import com.camilo.myifood.register.restaurant.dto.RestaurantConverter
import com.camilo.myifood.register.restaurant.infra.ConstraintViolationImpl
import com.camilo.myifood.register.restaurant.infra.ConstraintViolationResponse
import com.camilo.myifood.register.restaurant.models.Restaurant
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import javax.transaction.Status
import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import kotlin.reflect.KClass

@Tag(name = "restaurants")
@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
class RestaurantResource {

    @GET
    @Path("{id}")
    fun findById(@PathParam("id") restaurantId: Long) = Restaurant.findById(restaurantId)

    @GET
    fun findAllPaged() = Restaurant.findAll().range(0, 20).list()

    @PUT
    @Path("{id}")
    @Transactional
    fun updateById(@PathParam("id") restaurantId: Long, @Valid dto: CreateOrUpdateRestaurantDTO): Response? {
        findEntityById(restaurantId)?.let {
            it.name = dto.name
            it.persist()
        }
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    @POST
    @Transactional
    @APIResponse(
        responseCode = "400",
        content = [
            (Content(mediaType = "application/json", schema = Schema(allOf = [ConstraintViolationImpl::class])))
        ]
    )
    fun create(@Valid restaurantDto:  CreateOrUpdateRestaurantDTO): Response? {
        val restaurant = RestaurantConverter.toRestaurant(restaurantDto)
        Restaurant.persist(restaurant)
        return Response.status(Response.Status.CREATED).build()
    }

    private fun findEntityById(restaurantId: Long): Restaurant? =
        Restaurant.findById(restaurantId) ?: throw NotFoundException()

    @DELETE
    @Path("{id}")
    @Transactional
    fun deleteById(@PathParam("id") restaurantId: Long) =
        findEntityById(restaurantId)?.also { Restaurant.deleteById(restaurantId) }
}