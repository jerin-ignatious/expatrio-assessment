import expatrio.jerin.build.Dependency

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {

    // external dependencies
    implementation(Dependency.Library.jackson)
    implementation(Dependency.Library.kotlinLogging)
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("io.github.openfeign:feign-core")
    implementation("io.github.openfeign:feign-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-openfeign-core")
    // external test dependencies
    testImplementation(Dependency.Library.mockito)
    testImplementation("io.github.openfeign:feign-okhttp")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    // BOM imports
    implementation(platform(Dependency.BOM.springBoot))
    implementation(platform(Dependency.BOM.springCloud))
    implementation(enforcedPlatform(Dependency.BOM.jackson))
}
