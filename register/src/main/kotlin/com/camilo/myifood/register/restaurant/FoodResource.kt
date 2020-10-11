package com.camilo.myifood.register.restaurant

import io.quarkus.panache.common.Parameters
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("/restaurants/{idRestaurant}/foods")
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

    @DELETE
    @Path("{id}")
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