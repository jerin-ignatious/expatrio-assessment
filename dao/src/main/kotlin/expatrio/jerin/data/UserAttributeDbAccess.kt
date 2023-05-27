package expatrio.jerin.data

import expatrio.jerin.common.models.UserAttribute

interface UserAttributeDbAccess {
    fun getAllUsersByRole(userRole: String): List<UserAttribute>

    fun fetchByPhoneNumber(phoneNumber: String): UserAttribute

    fun createCustomer(userAttribute: UserAttribute): UserAttribute

    fun updateCustomer(
        userId: String,
        phoneNumber: String
    ): UserAttribute

    fun deleteCustomer(userId: String)
}
