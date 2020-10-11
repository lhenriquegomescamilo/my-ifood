package com.camilo.myifood.register.restaurant

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_restaurant")
class Restaurant : PanacheEntityBase {


    companion object : PanacheCompanion<Restaurant, Long>;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var owner: String? = null

    var cnpj: String? = null

    var name: String? = null

    @OneToOne
    var location: Location? = null

    @CreationTimestamp
    var createdAt: Date? = null

    @UpdateTimestamp
    var updatedAt: Date? = null

    @OneToMany
    var dish: List<Dish>? = null
}