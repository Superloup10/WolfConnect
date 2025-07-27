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
    useJUnitPlatform()
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

val dbName = System.getenv("DB_NAME")
val dbHost = System.getenv("DB_HOST")
val dbPort = System.getenv("DB_PORT")
val dbMasterUser = System.getenv("DB_MASTER_USERNAME")
val dbMasterPassword = System.getenv("DB_MASTER_PASSWORD")
val dbAppUser = System.getenv("DB_APP_USERNAME")

val sqlVars = mutableMapOf<Any, Any>(
    "var-master" to dbMasterUser,
    "var-user" to dbAppUser
)

flyway {
    url = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
    user = dbMasterUser
    password = dbMasterPassword
    baselineOnMigrate = true
    locations = arrayOf("classpath:db/migration")
    placeholders = sqlVars
    placeholderReplacement = true
    mixed = true
    validateOnMigrate = true
    outOfOrder = false
}
