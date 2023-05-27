package expatrio.jerin.data.impl

import expatrio.jerin.common.exception.EntityNotFoundException
import expatrio.jerin.common.models.UserCredentials
import expatrio.jerin.data.UserCredentialsDbAccess
import expatrio.jerin.generated.dao.jooq.Tables
import expatrio.jerin.generated.dao.jooq.tables.UserAttribute
import expatrio.jerin.mapper.toDomainModel
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class UserCredentialsDbAccessImpl(
    private val ctx: DSLContext
) : UserCredentialsDbAccess {
    override fun fetchByUserId(userId: String): UserCredentials {
        val userAttributeRowId = ctx.select(Tables.USER_ATTRIBUTE.ID)
            .from(Tables.USER_ATTRIBUTE)
            .where(Tables.USER_ATTRIBUTE.USER_ID.eq(userId))
            .fetchOne()
            ?.get(Tables.USER_ATTRIBUTE.ID)
            ?: throw EntityNotFoundException(entityType = UserAttribute::class, entityValue = userId)

        val userCredentialsRecord = ctx.selectFrom(Tables.USER_CREDENTIALS)
            .where(Tables.USER_CREDENTIALS.USER_ID.eq(userAttributeRowId))
            .fetchOne()
            ?: throw EntityNotFoundException(
                entityType = expatrio.jerin.generated.dao.jooq.tables.UserCredentials::class,
                entityValue = userId
            )

        return userCredentialsRecord.toDomainModel()
    }
}
