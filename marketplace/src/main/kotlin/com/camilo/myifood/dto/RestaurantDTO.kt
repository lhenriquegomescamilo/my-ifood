package com.camilo.myifood.dto

import io.vertx.mutiny.pgclient.PgPool
import io.vertx.mutiny.sqlclient.Tuple
import java.util.*

data class RestaurantDTO(
    var id: Long? = null,
    var owner: String? = null,
    var cnpj: String? = null,
    var name: String? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var foods: List<FoodDTO>? = null,
    var location: LocationDTO? = null


) {
    fun persist(pgPool: PgPool) {

        pgPool.preparedQuery("insert into location (id,latitude, longitude) values($1, $2, $3) ")
            .execute(Tuple.of(location?.id, location?.latitude, location?.longitude)).await()
            .indefinitely()

        pgPool.preparedQuery("insert into restaurant (id,name, location_id) values($1, $2, $3) ")
            .execute(Tuple.of(id, name, location?.id)).await()
            .indefinitely()
    }


}
