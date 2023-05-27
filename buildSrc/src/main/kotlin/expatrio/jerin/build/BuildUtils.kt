package expatrio.jerin.build

import expatrio.jerin.build.model.BuildConfig
import expatrio.jerin.build.model.DataSourceProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.gradle.api.Project
import org.gradle.api.logging.Logging
import java.io.File
import java.util.*

class BuildUtils {
    companion object {
        private val log = Logging.getLogger(BuildUtils::class.java)

        private val YamlLocation = "server/src/main/resources/application.yml"

        object Envs {
            const val JsonPropertyOverrides = "SPRING_APPLICATION_JSON"
            const val BuildOverrideLocation = "BUILD_CONFIG_OVERRIDE"
        }

        object Prefixes {
            const val ReflectDataSourcePrefix = "expatrio.jerin.build.reflective-data-source"
        }

        private val YamlBackedObjectMapper = ObjectMapper(YAMLFactory())

        init {
            loadJsonOverridesAsSystemProps()
        }

        @JvmStatic
        fun getBuildConfig(project: Project): BuildConfig {
            val rootPropertyLookup = Properties()

            val rootProject = project.rootProject

            val validLocations = listOf(File("${rootProject.projectDir}/$YamlLocation")) +
                    (
                            System.getenv(Envs.BuildOverrideLocation)?.let {
                                val buildConfigOverrideFile = File(it)

                                if (buildConfigOverrideFile.exists()) {
                                    listOf(buildConfigOverrideFile)
                                } else {
                                    throw IllegalArgumentException(
                                        "A `${Envs.BuildOverrideLocation}` was specified," +
                                                " but the file mentioned ${System.getenv(Envs.BuildOverrideLocation)} was not found"
                                    )
                                }
                            } ?: emptyList()
                            )

            validLocations
                .filter { it.exists() }
                .forEach { configFile ->
                    log.info("Project `${project.name}`, found config file ${configFile.absolutePath}")

                    mapYamlToFlattenedProperties(configFile).forEach {
                        rootPropertyLookup.setProperty(it.key as String, it.value as String)
                    }
                }

            return BuildConfig(
                reflectiveDataSource = constructReflectiveDataSourceProperties(
                    properties = rootPropertyLookup
                )
            )
        }

        private fun constructReflectiveDataSourceProperties(
            properties: Properties,
            prefix: String = Prefixes.ReflectDataSourcePrefix
        ): DataSourceProperties =
            DataSourceProperties(
                url = properties.getProperty("$prefix.url"),
                username = properties.getProperty("$prefix.username"),
                password = properties.getProperty("$prefix.password"),
                driverClassName = properties.getProperty("$prefix.driver-class-name"),
                schema = properties.getProperty("$prefix.schema")
            )

        private fun loadJsonOverridesAsSystemProps(): Unit =
            System.getenv(Envs.JsonPropertyOverrides)?.let {
                ObjectMapper().readTree(it)
                    .fields()
                    .forEachRemaining { fieldValue ->
                        System.setProperty(fieldValue.key, fieldValue.value.asText())
                    }
            } ?: Unit

        private fun mapYamlToFlattenedProperties(configYaml: File): Properties =
            JavaPropsMapper().writeValueAsProperties(
                YamlBackedObjectMapper.readTree(configYaml)
            )
    }
}
