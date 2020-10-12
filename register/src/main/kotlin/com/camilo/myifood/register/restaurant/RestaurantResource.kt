package com.camilo.myifood.register.restaurant

import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.resteasy.annotations.Body
import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

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

    @POST
    @Transactional
    fun create(restaurant: Restaurant): Response? {
        Restaurant.persist(restaurant);
        return Response.status(Response.Status.CREATED).build()
    }

    @PUT
    @Path("{id}")
    @Transactional
    fun updateById(@PathParam("id") restaurantId: Long, dto: Restaurant): Response? {
        findEntityById(restaurantId)?.let {
            it.name = dto.name
            it.persist();
        }
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    private fun findEntityById(restaurantId: Long): Restaurant? =
        Restaurant.findById(restaurantId) ?: throw NotFoundException()

    @DELETE
    @Path("{id}")
    @Transactional
    fun deleteById(@PathParam("id") restaurantId: Long) =
        findEntityById(restaurantId)?.also { Restaurant.deleteById(restaurantId) }
}