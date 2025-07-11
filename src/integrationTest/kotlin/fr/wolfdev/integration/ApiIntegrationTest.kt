package fr.wolfdev.integration

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication

class ApiIntegrationTest : FunSpec({
    test("complete API flow test") {
        testApplication {
            val response = client.get("/api/health")
            response.status shouldBe HttpStatusCode.OK
        }
    }
})
