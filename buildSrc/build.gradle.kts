plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    gradleApi()

    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib-jdk8
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.8.8")
}