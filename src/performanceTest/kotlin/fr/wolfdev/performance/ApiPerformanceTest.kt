package fr.wolfdev.performance

import io.kotest.core.spec.style.FunSpec
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.time.measureTime

class ApiPerformanceTest : FunSpec({
    val client = HttpClient()

    test("should handle multiple concurent requests") {
        coroutineScope {
            val duration = measureTime {
                val request = List(100) {
                    async { client.get("http://localhost:8080/api/health") }
                }
                request.awaitAll()
            }
            println("100 concurrent requests completed in $duration")
        }
    }
})
