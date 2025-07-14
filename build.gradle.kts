import org.gradle.kotlin.dsl.register

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktor)
    alias(libs.plugins.flyway)
    alias(libs.plugins.kover)
}

group = "fr.wolfdev"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-sensitive-resolution")
    }
}

tasks.test {
    /*dependsOn("createTestDb")
    dependsOn("flywayMigrate")*/

    useJUnitPlatform()

    /*flyway {
        url = "jdbc:postgresql://localhost:5432/wolfconnect_test"
    }*/
}

detekt {
    config.setFrom(files("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

ktlint {
    version.set("1.6.0")
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

sourceSets {
    create("integrationTest") {
        kotlin {
            compileClasspath += main.get().output + test.get().output
            runtimeClasspath += main.get().output + test.get().output
        }
    }

    create("performanceTest") {
        kotlin {
            compileClasspath += main.get().output + test.get().output
            runtimeClasspath += main.get().output + test.get().output
        }
    }
}

configurations {
    val integrationTestImplementation by getting {
        extendsFrom(testImplementation.get())
    }

    val performanceTestImplementation by getting {
        extendsFrom(testImplementation.get())
    }
}

val integrationTest = tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    useJUnitPlatform()
    mustRunAfter(tasks.test)
}

val performanceTest = tasks.register<Test>("performanceTest") {
    description = "Runs performance tests."
    group = "verification"
    testClassesDirs = sourceSets["performanceTest"].output.classesDirs
    classpath = sourceSets["performanceTest"].runtimeClasspath
    useJUnitPlatform()
    mustRunAfter(integrationTest)
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.databases)
    runtimeOnly(libs.logback)
    runtimeOnly(libs.bundles.databases.runtime)
    testImplementation(libs.bundles.test)
    "performanceTestImplementation"(libs.bundles.performance.test)
    "integrationTestImplementation"(libs.bundles.test)
    detektPlugins(libs.detekt.formatting)
}

flyway {
    url = "jdbc:postgresql://localhost:5432/wolfconnect"
    user = "postgres"
    password = "postgres"
    baselineOnMigrate = true
    locations = arrayOf("classpath:db/migration")
    mixed = true
    validateOnMigrate = true
    outOfOrder = false
}

/*tasks.register<Test>("createTestDb") {
    doLast {
        exec {
            "psql",
            "-U", "postgres",
            "-c", "CREATE DATABASE wolfconnect_test WITH TEMPLATE wolfconnect;"
        }
    }
}*/
