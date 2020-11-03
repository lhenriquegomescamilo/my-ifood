package com.camilo.myifood

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test

@QuarkusTest
class FoodResourceTest {

    @Test
    fun `show return a list of foods`() {
        given()
            .`when`().get("foods")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .asString()
    }

}