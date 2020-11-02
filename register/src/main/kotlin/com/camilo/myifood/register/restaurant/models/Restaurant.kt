package com.camilo.myifood.register.restaurant.models

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tb_restaurant")
data class Restaurant(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var owner: String? = null,

    @field:Column(unique = true)
    var cnpj: String? = null,

    var name: String? = null,

    @field:OneToOne(cascade = [CascadeType.ALL])
    var location: Location? = null,

    @field:CreationTimestamp
    var createdAt: Date? = null,

    @field:UpdateTimestamp
    var updatedAt: Date? = null,

    @field:OneToMany
    var foods: List<Food>? = null
) : PanacheEntityBase {

    companion object : PanacheCompanion<Restaurant, Long>;

}