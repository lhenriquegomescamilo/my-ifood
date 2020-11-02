package com.camilo.myifood.register.restaurant.resource

import com.camilo.myifood.register.restaurant.dto.CreateRestaurantDTO
import com.camilo.myifood.register.restaurant.dto.RestaurantConverter
import com.camilo.myifood.register.restaurant.dto.UpdateRestaurantDTO
import com.camilo.myifood.register.restaurant.models.Restaurant
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.annotation.security.RolesAllowed

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
class RestaurantResource {

    @GET
    @Path("{id}")
    fun findById(@PathParam("id") restaurantId: Long) = Restaurant.findById(restaurantId)

    @GET
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
            APIResponse(responseCode = "400", description = "When something is wrong, like a CNPJ alread exists")
        ]
    )
    fun create(@Valid restaurantDto: CreateRestaurantDTO): Response? {
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