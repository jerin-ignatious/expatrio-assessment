package expatrio.jerin.common.exception

open class UserManagementException(message: String, cause: Throwable? = null) : RuntimeException(message) {
    init {
        cause?.let { initCause(cause) }
    }
}

class FetchAllUsersFailedException(userRole: String) :
    UserManagementException("Failed to fetch all users with role: $userRole")

class CreateCustomerFailedException(phoneNumber: String) :
    UserManagementException("Failed to create customer for phone number: $phoneNumber")

class UpdateCustomerFailedException(userId: String) :
    UserManagementException("Failed to update user attribute for userId: $userId")

class DeleteCustomerFailedException(userId: String) :
    UserManagementException("Failed to delete user with userId: $userId")
