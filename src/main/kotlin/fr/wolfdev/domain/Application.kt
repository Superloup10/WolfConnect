package fr.wolfdev.domain

import fr.wolfdev.domain.config.DatabaseFactory
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.slf4j.LoggerFactory

fun Application.module() {
    val logger = LoggerFactory.getLogger("WolfConnect")

    DatabaseFactory.init(environment.config)

    routing {
        get("/") {
            logger.info("GET / reçu")
            call.respondText("Bienvenue sur WolfConnect")
        }
    }
}
