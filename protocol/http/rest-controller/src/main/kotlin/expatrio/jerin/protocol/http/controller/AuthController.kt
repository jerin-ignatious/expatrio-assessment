package expatrio.jerin.protocol.http.controller

import expatrio.jerin.authentication.AuthWorkflow
import expatrio.jerin.common.models.UserRoles
import expatrio.jerin.protocol.http.api.AuthApi
import expatrio.jerin.protocol.http.mappers.toApiModel
import expatrio.jerin.protocol.http.model.AdminLoginRequest
import expatrio.jerin.protocol.http.model.AdminLoginResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authWorkflow: AuthWorkflow
): AuthApi {
    override suspend fun adminLogin(adminLoginRequest: AdminLoginRequest): ResponseEntity<AdminLoginResponse> =
        ResponseEntity.ok(
            authWorkflow.login(
                phoneNumber = adminLoginRequest.phoneNumber,
                password = adminLoginRequest.password,
                userRole = UserRoles.CUSTOMER
            ).toApiModel()
        )
}
