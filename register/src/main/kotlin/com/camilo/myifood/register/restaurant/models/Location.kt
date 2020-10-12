package com.camilo.myifood.register.restaurant.models
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import javax.persistence.*

@Entity
@Table(name = "tb_location")
class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
): PanacheEntityBase {

    companion object : PanacheCompanion<Location, Long>;


}