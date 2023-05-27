package expatrio.jerin.authentication.impl

import expatrio.jerin.authentication.AuthWorkflow
import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.common.models.UserRoles
import expatrio.jerin.data.UserAttributeDbAccess
import expatrio.jerin.data.UserCredentialsDbAccess
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date

@Component
class AuthWorkflowImpl(
    private val userAttributeDbAccess: UserAttributeDbAccess,
    private val userCredentialsDbAccess: UserCredentialsDbAccess
) : AuthWorkflow {
    private val secretKey = "your-secret-key"
    private val expirationTimeMillis = 1800000

    override fun verifyJWT(token: String) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        } catch (e: Exception) {
            throw RuntimeException() // throw custom exception
        }
    }

    override fun login(
        phoneNumber: String,
        password: String,
        userRole: UserRoles
    ): String {
        val userAttribute = userAttributeDbAccess.fetchByPhoneNumber(phoneNumber = phoneNumber)
        checkIfUserIsAdmin(userAttribute = userAttribute)
        val storedPassword = userCredentialsDbAccess.fetchByUserId(userId = userAttribute.userId).password
        validateUserCredentials(
            password = password,
            storedPassword = storedPassword
        )
        return generateJwtToken(userId = userAttribute.userId)
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
        if(userAttribute.userRole != UserRoles.ADMIN) throw RuntimeException() // throw custom exception
    }

    private fun validateUserCredentials(
        password: String,
        storedPassword: String
    ) {
        if (password != storedPassword) throw RuntimeException() // throw custom exception
    }
}
