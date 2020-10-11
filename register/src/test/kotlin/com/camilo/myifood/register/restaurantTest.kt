package com.camilo.myifood.register

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class restaurantTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/restaurants")
          .then()
             .statusCode(200)
             .body(`is`("hello"))
    }

}