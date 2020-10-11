package com.camilo.myifood.register.restaurant

import io.quarkus.panache.common.Parameters
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Tag(name = "foods")
@Path("/restaurants/{idRestaurant}/foods")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FoodResource {

    @GET
    @Path("{id}")
    fun findById(
        @PathParam("idRestaurant") idRestaurant: Long,
        @PathParam("id") idFood: Long
    ): Food? {
        val params = Parameters.with("idFood", idFood).and("idRestaurant", idRestaurant)
        return Food.find("id = :idFood and idRestaurant = :idRestaurant", params).firstResult()
    }

    @GET
    fun findByAllFromRestaurant(
        @PathParam("idRestaurant") idRestaurant: Long
    ): List<Food> {
        val params = Parameters.with("idRestaurant", idRestaurant)
        return Food.find("idRestaurant = :idRestaurant", params).list()
    }

    @POST()
    @Transactional
    fun createFoodToRestaurant(food: Food): Response? {
        Food.persist(food)
        return Response.status(Response.Status.CREATED).build()
    }

    @DELETE
    @Path("{id}")
    @Transactional
    fun deleteFoodFromRestaurant(
        @PathParam("idRestaurant") idRestaurant: Long,
        @PathParam("id") idFood: Long
    ): Response? {
        findFoodFromRestaurant(idFood, idRestaurant)
        val params = Parameters.with("idFood", idFood).and("idRestaurant", idRestaurant)
        Food.delete("idRestaurant = :idRestaurant", params)
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    fun findFoodFromRestaurant(idFood: Long, idRestaurant: Long?): Food {
        val firstResult: Food? = Food.find(
            "id = :idFood and idRestaurant = :idRestaurant",
            Parameters.with("idFood", idFood).and("idRestaurant", idRestaurant)
        )
            .firstResult()
        return firstResult ?: throw NotFoundException()
    }

    @PUT
    @Path("{id}")
    @Transactional
    fun updateFoodFromRestaurant(
        @PathParam("idRestaurant") idRestaurant: Long,
        @PathParam("id") idFood: Long,
        food: Food
    ): Response? {
        val foodFromDatabase = findFoodFromRestaurant(idFood, idRestaurant)
        foodFromDatabase.name = food.name
        foodFromDatabase.persist()
        return Response.status(Response.Status.NO_CONTENT).build()
    }

}