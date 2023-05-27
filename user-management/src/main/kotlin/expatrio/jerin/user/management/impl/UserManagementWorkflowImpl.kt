package expatrio.jerin.user.management.impl

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.data.UserAttributeDbAccess
import expatrio.jerin.protocol.http.model.UserRoles
import expatrio.jerin.user.management.UserManagementWorflow
import mu.KotlinLogging
import org.springframework.stereotype.Component

/**
 * Author: Jerin Ignatious
 */
@Component
class UserManagementWorkflowImpl(
    private val userAttributeDbAccess: UserAttributeDbAccess
) : UserManagementWorflow {
    private val log = KotlinLogging.logger {}

    override fun getAllUsers(userRole: UserRoles): List<UserAttribute> {
        log.info { "[UserManagementWorkflowImpl - getAllUsers] Received request to fetch all user with role: $userRole" }
        return userAttributeDbAccess.getAllUsersByRole(userRole = userRole.name)
    }

    override fun createCustomer(userName: String, phoneNumber: String): UserAttribute {
        TODO("Not yet implemented")
    }

    override fun updateCustomer(userId: String, phoneNumber: String): UserAttribute {
        TODO("Not yet implemented")
    }

    override fun deleteCustomer(userId: String) {
        TODO("Not yet implemented")
    }
}
