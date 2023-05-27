package expatrio.jerin.build.model

data class DataSourceProperties(
    val url: String,
    val username: String,
    val password: String,
    val driverClassName: String?,
    val schema: String = "public"
)

data class BuildConfig(
    val reflectiveDataSource: DataSourceProperties
)
