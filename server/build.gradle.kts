import expatrio.jerin.build.Dependency

plugins {
    id("org.springframework.boot")

    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {

    // internal modules
//    implementation(project(":common"))
//    implementation(project(":dao"))
//    implementation(project(":internal-services"))
//    implementation(project(":integrations"))
//    implementation(project(":internal-services:api:client-reactive"))
//    implementation(project(":external-partners"))
//    implementation(project(":orchestrator"))
//    implementation(project(":protocol:http:rest-controller"))
//    implementation(project(":protocol:http:server-stub"))

    // external dependencies
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation(Dependency.Library.kotlinLogging)
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("io.github.openfeign:feign-okhttp")
    implementation("io.github.openfeign:feign-core")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springdoc:springdoc-openapi-webflux-core:1.6.15")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-security")

//     BOM imports
    implementation(platform(Dependency.BOM.springBoot))
    implementation(platform(Dependency.BOM.springCloud))
}

tasks.bootJar {
    mainClass.set("expatrio.jerin.server.ExpatrioApp")
}

val bootRun: org.springframework.boot.gradle.tasks.run.BootRun by tasks
bootRun.apply {
    val bootRunJvmArgs = emptyList<String>()
    jvmArgs = bootRunJvmArgs
    args = getDefaultJVMArgs()
}

fun getDefaultJVMArgs(): List<String> {
    return listOf(
        "--spring.config.additional-location=" +
            "file:${project.projectDir.absolutePath}/../"
    )
}
