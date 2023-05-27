package expatrio.jerin.authentication.impl

import expatrio.jerin.authentication.AuthWorkflow
import expatrio.jerin.common.exception.AuthTokenVerificationFailedException
import expatrio.jerin.common.exception.EntityNotFoundException
import expatrio.jerin.common.exception.IncorrectRoleException
import expatrio.jerin.common.exception.InvalidUserCredentials
import expatrio.jerin.common.exception.UserLoginFailedException
import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.common.models.UserRoles
import expatrio.jerin.data.UserAttributeDbAccess
import expatrio.jerin.data.UserCredentialsDbAccess
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.Date

@Component
class AuthWorkflowImpl(
    private val userAttributeDbAccess: UserAttributeDbAccess,
    private val userCredentialsDbAccess: UserCredentialsDbAccess
) : AuthWorkflow {
    private val log = KotlinLogging.logger {}
    private val secretKey = "your-secret-key"
    private val expirationTimeMillis = 1800000

    override fun verifyJWT(token: String) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        } catch (e: Exception) {
            throw AuthTokenVerificationFailedException()
        }
    }

    override fun login(
        phoneNumber: String,
        password: String,
        userRole: UserRoles
    ): String {
        log.info { "[AuthWorkflowImpl - login] Received login request for phone number: $phoneNumber" }

        return try {
            val userAttribute = userAttributeDbAccess.fetchByPhoneNumber(phoneNumber = phoneNumber)
            checkIfUserIsAdmin(userAttribute = userAttribute)
            val userId = userAttribute.userId
            val storedPassword = userCredentialsDbAccess.fetchByUserId(userId = userId).password
            validateUserCredentials(
                phoneNumber = phoneNumber,
                password = password,
                storedPassword = storedPassword
            )
            generateJwtToken(userId = userId)
        } catch (e: Exception) {
            when (e) {
                is EntityNotFoundException -> {
                    log.info { "[AuthWorkflowImpl - login] Entity not found with phone: $phoneNumber" }
                    throw UserLoginFailedException(phoneNumber)
                }
                is IncorrectRoleException -> {
                    log.info { "[AuthWorkflowImpl - login] user with phone: $phoneNumber is not an Admin" }
                    throw e
                }
                is InvalidUserCredentials -> {
                    log.info { "[AuthWorkflowImpl - login] Invalid credentials for phone number: $phoneNumber" }
                    throw e
                }
                else -> {
                    log.info { "[AuthWorkflowImpl - login] Unknown exception $e encountered for phone number: $phoneNumber" }
                    throw UserLoginFailedException(phoneNumber)
                }
            }
        }
    }

    private fun generateJwtToken(userId: String): String {
        val expirationDate = Date(System.currentTimeMillis() + expirationTimeMillis)

        return Jwts.builder()
            .setSubject(userId)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    private fun checkIfUserIsAdmin(userAttribute: UserAttribute) {
        if (userAttribute.userRole != UserRoles.ADMIN) throw IncorrectRoleException(
            phoneNumber = userAttribute.phoneNumber,
            userRole = userAttribute.userRole.name
        )
    }

    private fun validateUserCredentials(
        phoneNumber: String,
        password: String,
        storedPassword: String
    ) {
        if (password != storedPassword) throw InvalidUserCredentials(phoneNumber = phoneNumber)
    }
}
