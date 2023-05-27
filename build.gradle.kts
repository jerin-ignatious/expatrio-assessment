import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.openapi.generator") version "5.4.0" apply false
    id("org.springframework.boot") version "2.6.1" apply false

    java

    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
}

allprojects {
    apply(plugin = "java")

    group = "expatrio.jerin"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        if (project.name == "dao") {
            dependsOn(":dao:flywayMigrate")
            dependsOn(":dao:jooq-codegen-primary")
        }
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

// Note that most modules are libraries rather than spring boot applications. So rather than using following plugins:
//     id("io.spring.dependency-management") version "1.0.9.RELEASE"
//     id("org.springframework.boot") version "2.3.3.RELEASE"
// and then disabling spring boot jar and enabling normal jar in each module by,
//     val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
//     bootJar.enabled = false
//     val jar: Jar by tasks
//     jar.enabled = true // Spring boot disables jar by default
// we are choosing to using BOMs.
