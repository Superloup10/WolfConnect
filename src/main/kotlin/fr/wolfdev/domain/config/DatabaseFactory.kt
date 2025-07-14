package fr.wolfdev.domain.config

import io.ktor.server.config.ApplicationConfig
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.IsolationLevel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabaseConfig
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import java.time.Duration

object DatabaseFactory {
    private fun createConnectionFactory(config: ApplicationConfig): ConnectionFactory {
        val connectionFactory = ConnectionFactories.get(
            ConnectionFactoryOptions
                .builder()
                .option(ConnectionFactoryOptions.DRIVER, "pool")
                .option(ConnectionFactoryOptions.PROTOCOL, "postgresql")
                .option(ConnectionFactoryOptions.HOST, config.property("db.host").getString())
                .option(ConnectionFactoryOptions.PORT, config.property("db.port").getString().toInt())
                .option(ConnectionFactoryOptions.DATABASE, config.property("db.database").getString())
                .option(ConnectionFactoryOptions.USER, config.property("db.username").getString())
                .option(ConnectionFactoryOptions.PASSWORD, config.property("db.password").getString())
                .build()
        )

        return ConnectionPool(
            ConnectionPoolConfiguration
                .builder(connectionFactory)
                .maxIdleTime(Duration.ofMillis(config.property("db.pool.idleTimeout").getString().toLong()))
                .maxSize(config.property("db.pool.maxPoolSize").getString().toInt())
                .minIdle(config.property("db.pool.minimumIdle").getString().toInt())
                .maxCreateConnectionTime(
                    Duration.ofMillis(
                        config.property("db.pool.connectionTimeout").getString().toLong()
                    )
                ).build()
        )
    }

    fun init(config: ApplicationConfig) {
        val connectionFactory = createConnectionFactory(config)
        R2dbcDatabase.connect(
            connectionFactory,
            R2dbcDatabaseConfig {
                defaultR2dbcIsolationLevel = IsolationLevel.REPEATABLE_READ
                defaultMaxAttempts = MAX_ATTEMPTS
                explicitDialect = PostgreSQLDialect()
            }
        )
    }
}

const val MAX_ATTEMPTS = 3

suspend fun <T> dbQuery(block: suspend () -> T): T = suspendTransaction(Dispatchers.IO) { block() }
