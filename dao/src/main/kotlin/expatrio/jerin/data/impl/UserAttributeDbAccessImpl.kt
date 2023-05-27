package expatrio.jerin.data.impl

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.data.UserAttributeDbAccess

class UserAttributeDbAccessImpl(

) : UserAttributeDbAccess {
    override fun getAllUsersByRole(userRole: String): List<UserAttribute> {
        TODO("Not yet implemented")
    }
}
