package com.camilo.myifood.register

import com.github.database.rider.cdi.api.DBRider
import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.core.api.configuration.Orthography
import com.github.database.rider.core.api.dataset.DataSet
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.approvaltests.Approvals
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
            .`when`().get("/restaurants/1")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().body()
    }


}