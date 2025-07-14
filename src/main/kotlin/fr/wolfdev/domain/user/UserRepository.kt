package fr.wolfdev.domain.user

import java.util.*

interface UserRepository {
    suspend fun save(user: User): User

    suspend fun findByEmail(email: String): User?

    suspend fun findById(id: UUID): User?
}
