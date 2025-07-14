package fr.wolfdev.infrastructure.user

import fr.wolfdev.domain.config.dbQuery
import fr.wolfdev.domain.user.User
import fr.wolfdev.domain.user.UserRepository
import io.github.serpro69.kfaker.faker
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.v1.r2dbc.SchemaUtils
import java.util.*

class UserRepositoryTest : DescribeSpec({
    val faker = faker {}
    lateinit var repository: UserRepository

    beforeTest {
        dbQuery {
            SchemaUtils.create(Users)
        }
        repository = UserRepositoryImpl()
    }

    afterTest {
        dbQuery {
            SchemaUtils.drop(Users)
        }
    }

    describe("UserRepository") {
        describe("findByEmail") {
            it("should return user when exists") {
                val user = User(
                    UUID.fromString(faker.random.nextUUID()),
                    faker.internet.safeEmail(),
                    faker.crypto.sha512(),
                    "USER"
                )
                repository.save(user)

                val result = repository.findByEmail(user.email)

                result shouldBe user
            }

            it("should return null when the user does not exist") {
                val result = repository.findByEmail(faker.internet.safeEmail())

                result shouldBe null
            }
        }

        describe("save") {
            it("should successfully save a user") {
                val user = User(
                    UUID.fromString(faker.random.nextUUID()),
                    faker.internet.safeEmail(),
                    faker.crypto.sha512(),
                    "USER"
                )

                val savedUser = repository.save(user)

                savedUser shouldBe user
            }

            it("should throw exception when saving user with duplicate email") {
                val email = faker.internet.safeEmail()
                val user1 = User(
                    UUID.fromString(faker.random.nextUUID()),
                    email,
                    faker.crypto.sha512(),
                    "USER"
                )
                val user2 = User(
                    UUID.fromString(faker.random.nextUUID()),
                    email,
                    faker.crypto.sha512(),
                    "USER"
                )
                repository.save(user1)

                shouldThrow<Exception> {
                    repository.save(user2)
                }
            }
        }

        describe("findById") {
            it("should return user when exists") {
                val user = User(
                    UUID.fromString(faker.random.nextUUID()),
                    faker.internet.safeEmail(),
                    faker.crypto.sha512(),
                    "USER"
                )
                repository.save(user)

                val result = repository.findById(user.id)

                result shouldBe user
            }

            it("should return null when the user does not exist") {
                val result = repository.findById(UUID.fromString(faker.random.nextUUID()))

                result shouldBe null
            }
        }
    }
})
