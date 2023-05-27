package expatrio.jerin.common.exception

open class AuthenticationException(message: String, cause: Throwable? = null) : RuntimeException(message) {
    init {
        cause?.let { initCause(cause) }
    }
}

class IncorrectRoleException(phoneNumber: String, userRole: String) :
    AuthenticationException("This Action is not permitted for role: $userRole, phoneNumber: $phoneNumber")

class InvalidUserCredentials(phoneNumber: String) :
    AuthenticationException("User with phone: $phoneNumber has entered invalid credentials.")

class UserLoginFailedException(phoneNumber: String) :
    AuthenticationException("Login failed for user with phone: $phoneNumber")

class AuthTokenVerificationFailedException() :
    AuthenticationException("Auth token verification failed.")
