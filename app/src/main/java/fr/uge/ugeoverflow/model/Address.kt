package fr.uge.ugeoverflow.model

import java.util.*

data class Address(
    private val id : UUID?,
    private val  street : String?="",
    private val  city : String?="",
    private val  country : String?="",
    private val zipCode : String?=""

){

    val getStreet: String? get() = street
    val getCity: String? get() = city
    val getCountry: String? get() = country
    val getZipCode: String? get() = zipCode
}

class AddressBuilder{
    private var id: UUID? = null
    private var street : String ?= ""
    private var city : String ?= ""
    private var country : String ?= ""
    private var zipCode : String ?= ""

    fun id(id: UUID) = apply { this.id = id }
    fun street(street: String) = apply { this.street = street }
    fun city(city: String) = apply { this.city = city }
    fun country(country: String) = apply { this.country = country }
    fun zipCode(zipCode: String) = apply { this.zipCode = zipCode }

    fun build(): Address {
        return Address(id = id, street = street, city = city, country = country,
            zipCode = zipCode
        )
    }
    companion object {
        fun builder() = AddressBuilder()
    }
}