package expatrio.jerin.user.management

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.protocol.http.model.UserRoles

interface UserManagementWorflow {
    fun getAllUsers(userRole: UserRoles): List<UserAttribute>

    fun createCustomer(
        userName: String,
        phoneNumber: String
    ): UserAttribute

    fun updateCustomer(
        userId: String,
        phoneNumber: String
    ): UserAttribute

    fun deleteCustomer(userId: String)
}
