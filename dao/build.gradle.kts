import com.rohanprabhu.gradle.plugins.kdjooq.JooqEdition
import com.rohanprabhu.gradle.plugins.kdjooq.database
import com.rohanprabhu.gradle.plugins.kdjooq.generator
import com.rohanprabhu.gradle.plugins.kdjooq.jdbc
import com.rohanprabhu.gradle.plugins.kdjooq.jooqCodegenConfiguration
import com.rohanprabhu.gradle.plugins.kdjooq.target
import expatrio.jerin.build.BuildUtils
import expatrio.jerin.build.Dependency
import expatrio.jerin.build.toPackageNameSafeString

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.flywaydb.flyway") version "6.5.2"
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.2"
}

val buildConfig = BuildUtils.getBuildConfig(project)

flyway {
    url = buildConfig.reflectiveDataSource.url
    user = buildConfig.reflectiveDataSource.username
    password = buildConfig.reflectiveDataSource.password
    locations = arrayOf("filesystem:${project.projectDir}/src/main/resources/db-migration")
    sqlMigrationPrefix = "V"
    table = "${project.name}_schema_version"
    baselineVersion = "0"
    baselineOnMigrate = true
    schemas = arrayOf(buildConfig.reflectiveDataSource.schema)
}

jooqGenerator {
    jooqEdition = JooqEdition.OpenSource
    jooqVersion = "3.14.0"

    configuration("primary", project.sourceSets.main.get()) {
        configuration = jooqCodegenConfiguration {
            jdbc {
                username = buildConfig.reflectiveDataSource.username
                password = buildConfig.reflectiveDataSource.password
                driver = buildConfig.reflectiveDataSource.driverClassName ?: "org.postgresql.Driver"
                url = buildConfig.reflectiveDataSource.url
                schema = buildConfig.reflectiveDataSource.schema
            }

            generator {
                target {
                    packageName = "expatrio.jerin.generated.${projectDir.name.toPackageNameSafeString()}.jooq"
                    directory = "${project.buildDir}/generated/jooq/primary"
                }

                database {
                    name = "org.jooq.meta.postgres.PostgresDatabase"
                    inputSchema = buildConfig.reflectiveDataSource.schema
                }
            }
        }
    }
}

dependencies {

    // internal modules
    implementation(project(":common"))

    // external dependencies
    implementation(Dependency.Library.jackson)
    implementation(Dependency.Library.kotlinLogging)
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("io.github.openfeign:feign-core")
    implementation("io.github.openfeign:feign-jackson")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-openfeign-core")
    implementation("org.springframework.boot:spring-boot-starter-jooq")

    jooqGeneratorRuntime("org.jooq:jooq-codegen:3.13.5")
    jooqGeneratorRuntime("org.jooq:jooq:3.13.5")
    jooqGeneratorRuntime("org.jooq:jooq-meta:3.13.5")
    jooqGeneratorRuntime(Dependency.Library.postgres)
    runtimeOnly(Dependency.Library.postgres)

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
