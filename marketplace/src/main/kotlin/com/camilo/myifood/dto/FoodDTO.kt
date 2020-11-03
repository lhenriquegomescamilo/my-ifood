package com.camilo.myifood.dto

import io.vertx.mutiny.sqlclient.Row
import java.math.BigDecimal

data class FoodDTO(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val price: BigDecimal? = null,
    val idRestaurant: Long? = null
){
    companion object{
        fun from (row: Row): FoodDTO = FoodDTO(
            id = row.getLong("id"),
            name = row.getString("name"),
            description = row.getString("description"),
            price = row.getBigDecimal("price"),
            idRestaurant = row.getLong("idRestaurant")



        )
    }
}
