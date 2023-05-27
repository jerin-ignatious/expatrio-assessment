package expatrio.jerin.protocol.http.mappers

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.common.models.UserLoginResponse
import expatrio.jerin.protocol.http.model.AdminLoginResponse
import expatrio.jerin.protocol.http.model.GetAllUsersResponse
import expatrio.jerin.protocol.http.model.UserAttributes
import expatrio.jerin.protocol.http.model.UserRoles

fun List<UserAttribute>.toApiModel(): GetAllUsersResponse =
    GetAllUsersResponse(
        userAttributes = this.map { it.toUserAttributes() }
    )

fun UserAttribute.toUserAttributes(): UserAttributes =
    UserAttributes(
        userId = this.userId,
        userRole = UserRoles.valueOf(this.userRole.name),
        userName = this.userName,
        phoneNumber = this.phoneNumber
    )

fun UserLoginResponse.toApiModel(): AdminLoginResponse =
    AdminLoginResponse(
        token = this.token
    )
