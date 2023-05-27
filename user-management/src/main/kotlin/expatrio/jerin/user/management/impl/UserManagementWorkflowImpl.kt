package expatrio.jerin.user.management.impl

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.data.UserAttributeDbAccess
import expatrio.jerin.protocol.http.model.UserRoles
import expatrio.jerin.user.management.UserManagementWorflow
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

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

        return try {
            userAttributeDbAccess.getAllUsersByRole(userRole = userRole.name)
        } catch (e: Exception) {
            throw IllegalStateException() // throw custom exception
        }
    }

    override fun createCustomer(
        userName: String,
        phoneNumber: String
    ): UserAttribute {
        log.info { "[UserManagementWorkflowImpl - createCustomer] Received request to create customer for user" +
                " with phone number: $phoneNumber" }

        val userId = generateRandomUUID()

        return try {
            userAttributeDbAccess.createCustomer(
                userAttribute = UserAttribute(
                    userId = userId,
                    userRole = expatrio.jerin.common.models.UserRoles.CUSTOMER,
                    userName = userName,
                    phoneNumber = phoneNumber
                )
            )
        } catch (e: Exception) {
            throw IllegalStateException() // throw custom exception
        }
    }

    override fun updateCustomer(userId: String, phoneNumber: String): UserAttribute {
        TODO("Not yet implemented")
    }

    override fun deleteCustomer(userId: String) {
        TODO("Not yet implemented")
    }

    private fun generateRandomUUID(): String {
        return UUID.randomUUID().toString()
    }
}