package com.camilo.myifood.message

import com.camilo.myifood.dto.RestaurantDTO
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import javax.json.bind.JsonbBuilder

@ApplicationScoped
class RestaurantCreated {

    @Incoming("restaurants")
    fun recievedMessage(json: String) {
        val create = JsonbBuilder.create()
        val restaurant = create.fromJson(json, RestaurantDTO::class.java)
        println("----------------------------------")
        println("json")
        println("----------------------------------")
        println(restaurant)
    }
}