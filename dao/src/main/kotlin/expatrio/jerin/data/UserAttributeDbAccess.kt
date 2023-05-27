package expatrio.jerin.data

import expatrio.jerin.common.models.UserAttribute

interface UserAttributeDbAccess {
    fun getAllUsersByRole(userRole: String): List<UserAttribute>

    fun createCustomer(userAttribute: UserAttribute): UserAttribute
}
