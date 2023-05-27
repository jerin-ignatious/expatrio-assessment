package expatrio.jerin.mapper

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.common.models.UserCredentials
import expatrio.jerin.common.models.UserRoles
import expatrio.jerin.generated.dao.jooq.tables.records.UserAttributeRecord
import expatrio.jerin.generated.dao.jooq.tables.records.UserCredentialsRecord

fun UserAttributeRecord.toDomainModel(): UserAttribute =
    UserAttribute(
        userId = this.userId,
        userRole = UserRoles.valueOf(this.userRole),
        userName = this.userName,
        phoneNumber = this.phoneNumber
    )

fun UserAttribute.toRecord(): UserAttributeRecord =
    UserAttributeRecord().apply {
        userId = this@toRecord.userId
        userRole = this@toRecord.userRole.name
        userName = this@toRecord.userName
        phoneNumber = this@toRecord.phoneNumber
    }

fun UserCredentialsRecord.toDomainModel(): UserCredentials =
    UserCredentials(
        password = this.password
    )