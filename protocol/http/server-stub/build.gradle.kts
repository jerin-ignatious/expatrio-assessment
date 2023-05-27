import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import expatrio.jerin.build.Dependency

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(Dependency.Library.kotlinxCoroutinesCore)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }

    implementation(platform(Dependency.BOM.springBoot))
}

val serverPath = "$rootDir/protocol/http/server-stub"

val preGenerateCleanup by tasks.registering(Delete::class) {
    delete = setOf(
        fileTree(serverPath) {
            setExcludes(listOf("build.gradle.kts", "build", "templates")) // Gradle's cache, helps in faster re-compiles
        }
    )
    doLast {
        println("Cleaned server-stub dir")
    }
}

val generateServerStub by tasks.registering(GenerateTask::class) {
    dependsOn(preGenerateCleanup)

    doFirst {
        println("Root Directory is $rootDir")
        println("Project Dir is $projectDir")
    }

    inputSpec.set("$rootDir/protocol/http/specs.yml")
    outputDir.set(serverPath)

    // config
    generatorName.set("kotlin-spring")
    apiPackage.set("expatrio.jerin.protocol.http.api")
    modelPackage.set("expatrio.jerin.protocol.http.model")
    configOptions.set(
        mapOf(
            "reactive" to "true",
            "dateLibrary" to "java8",
            "useTags" to "true", // Use tags for the naming
            "interfaceOnly" to "true", // Generating the Controller API interface and the models only
            "gradleBuildFile" to "false",
            "exceptionHandler" to "false",
            "enumPropertyNaming" to "UPPERCASE", // Generate enums in uppercase. 'x-enum-varnames' is no more required in api specs.
            "useBeanValidation" to "false"
        )
    )
}

val postGenerateCleanup by tasks.registering(Delete::class) {
    dependsOn(generateServerStub)

    delete = setOf(
        fileTree(serverPath) {
            setExcludes(listOf("build.gradle.kts", "build", "src", "templates")) // Gradle's cache, helps in faster re-compiles
        },
        fileTree("$serverPath/src/main/kotlin/org") // delete Application
    )
    doLast {
        println("Cleaned unnecessary files post generation")
    }
}

tasks.named<KotlinCompile>("compileKotlin") {
    dependsOn(postGenerateCleanup)
}
