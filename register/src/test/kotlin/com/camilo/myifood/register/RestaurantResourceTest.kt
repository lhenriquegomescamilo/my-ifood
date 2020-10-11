package com.camilo.myifood.register

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.approvaltests.Approvals
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager::class)
class RestaurantResourceTest {

    @Test
    fun `should return the list of restaurants`() {
        given()
            .`when`().get("/restaurants")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().asString()
    }

}