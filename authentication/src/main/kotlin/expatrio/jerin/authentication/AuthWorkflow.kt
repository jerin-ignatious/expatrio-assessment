package expatrio.jerin.authentication

import expatrio.jerin.common.models.UserRoles

interface AuthWorkflow {
    fun verifyJWT(token: String)

    fun login(
        phoneNumber: String,
        password: String,
        userRole: UserRoles
    ): String
}