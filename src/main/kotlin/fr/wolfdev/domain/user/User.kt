package fr.wolfdev.domain.user

import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val passwordHash: String,
    val role: String,
) {
    init {
        require(isValidEmail(email)) { "Email invalide" }
        require(role in validRoles) { "Invalid role" }
    }

    private fun isValidEmail(email: String) = email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))

    companion object {
        private val validRoles = setOf("USER", "ADMIN")
    }
}
