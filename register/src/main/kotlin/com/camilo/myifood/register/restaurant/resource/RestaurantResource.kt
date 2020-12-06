package com.camilo.myifood.register.restaurant.resource

import com.camilo.myifood.register.restaurant.dto.CreateRestaurantDTO
import com.camilo.myifood.register.restaurant.dto.RestaurantConverter
import com.camilo.myifood.register.restaurant.dto.UpdateRestaurantDTO
import com.camilo.myifood.register.restaurant.models.Restaurant
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.SimplyTimed
import org.eclipse.microprofile.metrics.annotation.Timed
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.json.bind.JsonbBuilder

import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Tag(name = "restaurants")
@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("owner")
@SecurityScheme(
    securitySchemeName = "ifood-oauth",
    type = SecuritySchemeType.OAUTH2,
    flows = OAuthFlows(
        password = OAuthFlow(
            tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token"
        )
    )
)
class RestaurantResource(

) {

    @Inject
    @Channel("restaurants")
    lateinit var emitter: Emitter<String>

    @GET
    @Path("{id}")
    fun findById(@PathParam("id") restaurantId: Long) = Restaurant.findById(restaurantId)

    @GET
    @Counted(name = "Quantity of search restaurant")
    @SimplyTimed(name = "Time simple of search")
    @Timed(name = "Time complete of search")
    fun findAllPaged() = Restaurant.findAll().range(0, 20).list()

    @PUT
    @Path("{id}")
    @Transactional
    fun updateById(@PathParam("id") restaurantId: Long, @Valid dto: UpdateRestaurantDTO): Response? {
        findEntityById(restaurantId)?.let {
            it.name = dto.name
            it.persist()
        }
        return Response.status(Response.Status.NO_CONTENT).build()
    }

    @POST
    @Transactional
    @APIResponses(
        value = [
            APIResponse(responseCode = "201", description = "Create with successful"),
            APIResponse(responseCode = "400", description = "When something is wrong, like a CNPJ alread exists"),
            APIResponse(
                content = [Content(
                    schema = Schema(type = SchemaType.OBJECT, implementation = CreateRestaurantDTO::class)
                )]
            )

        ],
    )
    fun create(@Valid restaurantDto: CreateRestaurantDTO): Response? {
        val restaurant = RestaurantConverter.toRestaurant(restaurantDto)
        Restaurant.persist(restaurant)
        val jsonBuilder = JsonbBuilder.create()
        val json = jsonBuilder.toJson(restaurant)
        emitter.send(json)
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