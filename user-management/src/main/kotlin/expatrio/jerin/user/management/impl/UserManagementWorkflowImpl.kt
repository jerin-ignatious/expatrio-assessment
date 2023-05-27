package expatrio.jerin.user.management.impl

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.protocol.http.model.UserRoles
import expatrio.jerin.user.management.UserManagementWorflow

class UserManagementWorkflowImpl(

) : UserManagementWorflow {
    override fun getAllUsers(userRole: UserRoles): List<UserAttribute> {
        TODO("Not yet implemented")
    }
}
