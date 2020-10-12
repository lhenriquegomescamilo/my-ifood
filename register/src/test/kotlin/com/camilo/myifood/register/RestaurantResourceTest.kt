package com.camilo.myifood.register

import com.camilo.myifood.register.restaurant.models.Restaurant
import com.github.database.rider.cdi.api.DBRider
import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.apache.http.HttpStatus
import org.approvaltests.Approvals
import org.junit.Assert
import org.junit.jupiter.api.Test

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager::class)
class RestaurantResourceTest {

    @Test
    @DataSet(value = ["restaurants.yml"])
    fun `should return the list of restaurants`() {
        val result = given()
            .`when`().get("/restaurants")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().asString()
        Approvals.verifyJson(result)
    }

    @Test
    @DataSet(value = ["restaurants.yml"])
    fun `should return the one of by id`() {
        given()
            .with().pathParam("id", 1)
            .`when`().get("/restaurants/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().asString()
    }

    @Test
    @DataSet(value = ["restaurants.yml"])
    fun `should update by id`() {
        val dto = Restaurant()
        dto.name = "The new name"

        val restaurantId = 1L
        given()
            .contentType(ContentType.JSON)
            .with().pathParam("id", restaurantId).body(dto)
            .`when`().put("/restaurants/{id}")
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
            .extract().asString()
        val findById = Restaurant.findById(restaurantId)
        Assert.assertEquals(dto.name, findById?.name)
    }


}