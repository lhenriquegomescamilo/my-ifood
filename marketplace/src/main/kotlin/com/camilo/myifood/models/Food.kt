package com.camilo.myifood.models

import com.camilo.myifood.dto.FoodDTO
import io.smallrye.mutiny.Multi
import io.vertx.mutiny.pgclient.PgPool
import java.math.BigDecimal
import java.util.stream.StreamSupport

data class Food(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val restaurant: Restaurant? = null,
    val price: BigDecimal? = null,
    val idRestaurant: Long? = null
) {
    companion object {
        fun findAll(pgPool: PgPool): Multi<FoodDTO> =
            pgPool.preparedQuery("select id, description, name, price, restaurant_id from food ").execute()
                .onItem().transformToMulti { rowSet ->
                    Multi.createFrom().items {
                        StreamSupport.stream(rowSet.spliterator(), false)
                    }
                }
                .onItem().transform { FoodDTO.from(it) }


    }
}
