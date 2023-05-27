package expatrio.jerin.protocol.http.controller

import expatrio.jerin.protocol.http.api.UsersApi
import expatrio.jerin.protocol.http.model.GetAllUsersResponse
import expatrio.jerin.protocol.http.model.UserRoles
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(

): UsersApi {
    override suspend fun getAllUsers(userRole: UserRoles): ResponseEntity<GetAllUsersResponse> {
        return super.getAllUsers(userRole)
    }
}
