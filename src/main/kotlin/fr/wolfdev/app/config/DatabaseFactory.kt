package fr.wolfdev.app.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.v1.jdbc.Database

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val hikariConfig = HikariConfig().apply {
            driverClassName = config.property("db.driver").getString()
            jdbcUrl = config.property("db.jdbcUrl").getString()
            username = config.property("db.username").getString()
            password = config.property("db.password").getString()

            maximumPoolSize = config.property("db.maxPoolSize").getString().toInt()
            minimumIdle = config.property("db.minimumIdle").getString().toInt()
            connectionTimeout = config.property("db.connectionTimeout").getString().toLong()
            idleTimeout = config.property("db.idleTimeout").getString().toLong()
            leakDetectionThreshold = config.property("db.leakDetectionThreshold").getString().toLong()

            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        Database.connect(HikariDataSource(hikariConfig))
    }
}
