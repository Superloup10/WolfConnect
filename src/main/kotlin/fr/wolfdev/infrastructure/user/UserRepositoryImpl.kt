package fr.wolfdev.infrastructure.user

import fr.wolfdev.domain.config.dbQuery
import fr.wolfdev.domain.user.User
import fr.wolfdev.domain.user.UserRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.selectAll
import java.util.*

object Users : Table("users") {
    val id = uuid("id")
    val email = text("email").uniqueIndex()
    val passwordHash = text("password_hash")
    val role = text("role")

    override val primaryKey = PrimaryKey(id)
}

class UserRepositoryImpl : UserRepository {
    override suspend fun save(user: User): User =
        dbQuery {
            Users.insert {
                it[id] = user.id
                it[email] = user.email
                it[passwordHash] = user.passwordHash
                it[role] = user.role
            }
            user
        }

    override suspend fun findByEmail(email: String): User? =
        dbQuery {
            Users
                .selectAll()
                .where { Users.email eq email }
                .map(::resultRowToUser)
                .singleOrNull()
        }

    override suspend fun findById(id: UUID): User? =
        dbQuery {
            Users
                .selectAll()
                .where { Users.id eq id }
                .map(::resultRowToUser)
                .singleOrNull()
        }

    private fun resultRowToUser(row: ResultRow) =
        User(
            id = row[Users.id],
            email = row[Users.email],
            passwordHash = row[Users.passwordHash],
            role = row[Users.role]
        )
}
