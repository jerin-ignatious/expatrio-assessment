package expatrio.jerin.build

object Dependency {

    object Library {
        const val embeddedPostgresql = "com.playtika.testcontainers:embedded-postgresql:2.0.20"
        const val feign = "io.github.openfeign:feign-okhttp:10.9"
        const val flyway = "org.flywaydb:flyway-core:6.5.2"
        const val jackson = "com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0"
        const val kotlinLogging = "io.github.microutils:kotlin-logging:1.7.9"
        const val mockito = "org.mockito:mockito-core:3.3.0"
        const val postgres = "org.postgresql:postgresql:42.5.0"
        const val apacheCommons = "commons-io:commons-io:2.6"
        const val springdocWebfluxCore = "org.springdoc:springdoc-openapi-webflux-core:1.2.30"
        const val bouncyCastle = "org.bouncycastle:bcprov-jdk15on:1.66"
        const val kotlinxCoroutinesReactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.8"
        const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
    }

    object BOM {
        const val springBoot = "org.springframework.boot:spring-boot-dependencies:2.6.1"
        const val springCloud = "org.springframework.cloud:spring-cloud-dependencies:2021.0.0"
        const val jackson = "com.fasterxml.jackson:jackson-bom:2.12.5"
    }
}
