package expatrio.jerin.data

import expatrio.jerin.common.models.UserCredentials

interface UserCredentialsDbAccess {
    fun fetchByUserId(userId: String): UserCredentials
}
