package expatrio.jerin.user.management.impl

import expatrio.jerin.common.exception.CreateCustomerFailedException
import expatrio.jerin.common.exception.DeleteCustomerFailedException
import expatrio.jerin.common.exception.FetchAllUsersFailedException
import expatrio.jerin.common.exception.UpdateCustomerFailedException
import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.data.UserAttributeDbAccess
import expatrio.jerin.protocol.http.model.UserRoles
import expatrio.jerin.user.management.UserManagementWorkflow
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

/**
 * Author: Jerin Ignatious
 */
@Component
class UserManagementWorkflowImpl(
    private val userAttributeDbAccess: UserAttributeDbAccess
) : UserManagementWorkflow {
    private val log = KotlinLogging.logger {}

    override fun getAllUsers(userRole: UserRoles): List<UserAttribute> {
        log.info { "[UserManagementWorkflowImpl - getAllUsers] Received request to fetch all user with role: $userRole" }

        return try {
            userAttributeDbAccess.getAllUsersByRole(userRole = userRole.name)
        } catch (e: Exception) {
            log.info { "[UserManagementWorkflowImpl - getAllUsers] Failed to fetch users with role: $userRole," +
                    " exception: $e" }
            throw FetchAllUsersFailedException(userRole.name)
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
            log.info { "[UserManagementWorkflowImpl - createCustomer] Failed to create customer for user" +
                    " with phone: $phoneNumber, exception: $e" }
            throw CreateCustomerFailedException(phoneNumber)
        }
    }

    override fun updateCustomer(
        userId: String,
        phoneNumber: String
    ): UserAttribute {
        log.info { "[UserManagementWorkflowImpl - updateCustomer] Received request to update phone number" +
                " for user: $userId" }

        return try {
            userAttributeDbAccess.updateCustomer(
                userId = userId,
                phoneNumber = phoneNumber
            )
        } catch (e: Exception) {
            log.info { "[UserManagementWorkflowImpl - updateCustomer] Failed to update user attribute for userId: $userId," +
                    " exception: $e" }
            throw UpdateCustomerFailedException(userId)
        }
    }

    override fun deleteCustomer(userId: String) {
        log.info { "[UserManagementWorkflowImpl - deleteCustomer] Received request to delete user attribute" +
                " for user: $userId" }

        try {
            userAttributeDbAccess.deleteCustomer(userId = userId)
        } catch (e: Exception) {
            log.info { "[UserManagementWorkflowImpl - deleteCustomer] Failed to delete user with userId: $userId," +
                    " exception: $e" }
            throw DeleteCustomerFailedException(userId)
        }
    }

    private fun generateRandomUUID(): String {
        return UUID.randomUUID().toString()
    }
}
