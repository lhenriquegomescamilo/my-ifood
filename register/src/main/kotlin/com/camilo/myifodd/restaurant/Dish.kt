package com.camilo.myifodd.restaurant

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "tb_dish")
class Dish : PanacheEntityBase {

    companion object : PanacheCompanion<Dish, Long>;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String? = null

    var description: String? = null

    @ManyToOne
    var restaurant: Restaurant? = null

    var price: BigDecimal? = null

}
