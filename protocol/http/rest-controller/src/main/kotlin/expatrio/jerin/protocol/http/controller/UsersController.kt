package expatrio.jerin.protocol.http.controller

import expatrio.jerin.protocol.http.api.UsersApi
import expatrio.jerin.protocol.http.mappers.toApiModel
import expatrio.jerin.protocol.http.mappers.toUserAttributes
import expatrio.jerin.protocol.http.model.CreateCustomerRequest
import expatrio.jerin.protocol.http.model.GetAllUsersResponse
import expatrio.jerin.protocol.http.model.UpdateCustomerRequest
import expatrio.jerin.protocol.http.model.UserAttributes
import expatrio.jerin.protocol.http.model.UserRoles
import expatrio.jerin.user.management.UserManagementWorkflow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(
    private val userManagementWorkflow: UserManagementWorkflow
): UsersApi {
    override suspend fun getAllUsers(userRole: UserRoles): ResponseEntity<GetAllUsersResponse> =
        ResponseEntity.ok(
            userManagementWorkflow.getAllUsers(
                userRole = UserRoles.valueOf(userRole.name)
            ).toApiModel()
        )

    override suspend fun createCustomer(createCustomerRequest: CreateCustomerRequest): ResponseEntity<UserAttributes> =
        ResponseEntity.ok(
            userManagementWorkflow.createCustomer(
                userName = createCustomerRequest.userName,
                phoneNumber = createCustomerRequest.phoneNumber
            ).toUserAttributes()
        )

    override suspend fun updateCustomer(
        xUserId: String,
        updateCustomerRequest: UpdateCustomerRequest
    ): ResponseEntity<UserAttributes> =
        ResponseEntity.ok(
            userManagementWorkflow.updateCustomer(
                userId = xUserId,
                phoneNumber = updateCustomerRequest.phoneNumber
            ).toUserAttributes()
        )

    override suspend fun deleteCustomer(xUserId: String): ResponseEntity<Unit> =
        ResponseEntity.ok(
            userManagementWorkflow.deleteCustomer(
                userId = xUserId
            )
        )
}
