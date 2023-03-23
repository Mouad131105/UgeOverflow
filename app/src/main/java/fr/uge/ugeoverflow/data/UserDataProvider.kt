package fr.uge.ugeoverflow.data

import fr.uge.ugeoverflow.model.Address
import fr.uge.ugeoverflow.model.User
import java.util.*
import kotlin.random.Random

object UserDataProvider {
    private val random = Random(System.currentTimeMillis())

    private val firstNames =
        listOf("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivy", "Jack")
    private val lastNames = listOf(
        "Smith",
        "Johnson",
        "Brown",
        "Garcia",
        "Miller",
        "Davis",
        "Wilson",
        "Taylor",
        "Clark",
        "Lee"
    )

    private val addresses = listOf(
        Address(
            id = UUID.randomUUID(),
            street = "1 Main St",
            city = "New York",
            country = "USA",
            zipCode = "10001"
        ),
        Address(
            id = UUID.randomUUID(),
            street = "2 Main St",
            city = "San Francisco",
            country = "USA",
            zipCode = "94102"
        ),
        Address(
            id = UUID.randomUUID(),
            street = "3 Main St",
            city = "Paris",
            country = "France",
            zipCode = "75001"
        ),
        Address(
            id = UUID.randomUUID(),
            street = "4 Main St",
            city = "London",
            country = "UK",
            zipCode = "SW1A 2AA"
        ),
        Address(
            id = UUID.randomUUID(),
            street = "5 Main St",
            city = "Berlin",
            country = "Germany",
            zipCode = "10115"
        ),
        Address(
            id = UUID.randomUUID(),
            street = "6 Main St",
            city = "Tokyo",
            country = "Japan",
            zipCode = "100-0005"
        ),
        Address(
            id = UUID.randomUUID(),
            street = "7 Main St",
            city = "Sydney",
            country = "Australia",
            zipCode = "2000"
        )
    )

    private fun generateUser(): User {
        val id = UUID.randomUUID()
        val firstName = firstNames.random(random)
        val lastName = lastNames.random(random)
        val username = "$firstName.$lastName"
        val email = "$username@gmail.com"
        val address = addresses.random(random)
        return User(id, firstName, lastName, username, email, address)
    }

    fun generateUsers(count: Int = 20): List<User> {
        return List(count) { generateUser() }
    }
}

