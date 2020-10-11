package com.camilo.myifood.register.restaurant

import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

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
    fun create(restaurant: Restaurant): Response? {
        Restaurant.persist(restaurant);
        return Response.status(Response.Status.CREATED).build()
    }

    @PUT
    @Path("{id}")
    @Transactional
    fun updateById(restaurantId: Long, dto: Restaurant) {
        findEntityById(restaurantId)?.let {
            it.name = dto.name
            it.persist();
        }
    }

    private fun findEntityById(restaurantId: Long): Restaurant? =
        Restaurant.findById(restaurantId) ?: throw NotFoundException()

    @DELETE
    @Path("{id}")
    @Transactional
    fun deleteById(@PathParam("id") restaurantId: Long) =
        findEntityById(restaurantId)?.also { Restaurant.deleteById(restaurantId) }
}