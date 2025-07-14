package fr.wolfdev.domain.user

import io.github.serpro69.kfaker.faker
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

class UserTest : DescribeSpec({
    val faker = faker {}

    describe("User") {
        describe("creation") {
            it("should create a valid user") {
                val id = UUID.fromString(faker.random.nextUUID())
                val email = faker.internet.safeEmail()
                val passwordHash = faker.crypto.sha512()
                val role = "USER"

                val user = User(id, email, passwordHash, role)

                user.id shouldBe id
                user.email shouldBe email
                user.passwordHash shouldBe passwordHash
                user.role shouldBe role
            }

            it("should throw an exception for invalid email") {
                val id = UUID.fromString(faker.random.nextUUID())
                val email = faker.random.randomString()
                val passwordHash = faker.crypto.sha512()
                val role = "USER"

                shouldThrow<IllegalArgumentException> {
                    User(id, email, passwordHash, role)
                }
            }

            it("should throw an exception for an invalid role") {
                val id = UUID.fromString(faker.random.nextUUID())
                val email = faker.internet.safeEmail()
                val passwordHash = faker.crypto.sha512()
                val role = "INVALID"

                shouldThrow<IllegalArgumentException> {
                    User(id, email, passwordHash, role)
                }
            }
        }
    }
})
