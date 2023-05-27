package expatrio.jerin.common.models

enum class UserRoles {
    ADMIN,
    CUSTOMER
}

data class UserAttribute(
    val userId: String,
    val userRole: UserRoles
)