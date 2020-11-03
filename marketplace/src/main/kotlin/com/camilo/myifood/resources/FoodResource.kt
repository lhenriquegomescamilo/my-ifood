package com.camilo.myifood.resources

import com.camilo.myifood.dto.FoodDTO
import com.camilo.myifood.models.Food
import io.smallrye.mutiny.Multi
import io.vertx.mutiny.pgclient.PgPool
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/foods")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FoodResource(

) {
    @Inject lateinit var pgPool: PgPool
    @GET
    fun findAll(): Multi<FoodDTO> {
        return Food.findAll(pgPool)
    }
}