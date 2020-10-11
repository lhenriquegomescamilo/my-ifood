package com.camilo.myifood.register.restaurant
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import javax.persistence.*

@Entity
@Table(name = "tb_location")
class Location : PanacheEntityBase {

    companion object : PanacheCompanion<Location, Long>;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    val latitude: Double? = null
    val longitude: Double? = null
}