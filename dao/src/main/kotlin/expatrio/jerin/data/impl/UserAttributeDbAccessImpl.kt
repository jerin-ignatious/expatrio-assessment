package expatrio.jerin.data.impl

import expatrio.jerin.common.exception.EntityNotFoundException
import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.common.models.UserRoles
import expatrio.jerin.data.UserAttributeDbAccess
import expatrio.jerin.generated.dao.jooq.Tables
import expatrio.jerin.mapper.toDomainModel
import expatrio.jerin.mapper.toRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Component

@Component
class UserAttributeDbAccessImpl(
    private val ctx: DSLContext
) : UserAttributeDbAccess {
    override fun getAllUsersByRole(userRole: String): List<UserAttribute> {
        val userAttributeRecords = ctx.selectFrom(Tables.USER_ATTRIBUTE)
            .where(Tables.USER_ATTRIBUTE.USER_ROLE.eq(userRole))

        return userAttributeRecords.map { it.toDomainModel() }
    }

    override fun fetchByPhoneNumber(phoneNumber: String): UserAttribute {
        val userAttributeRecord = ctx.selectFrom(Tables.USER_ATTRIBUTE)
            .where(Tables.USER_ATTRIBUTE.PHONE_NUMBER.eq(phoneNumber))
            .fetchOne()
            ?: throw EntityNotFoundException(
                entityType = expatrio.jerin.generated.dao.jooq.tables.UserAttribute::class,
                entityValue = phoneNumber
            )

        return userAttributeRecord.toDomainModel()
    }

    override fun createCustomer(userAttribute: UserAttribute): UserAttribute {
        val userAttributeRecord = ctx.insertInto(Tables.USER_ATTRIBUTE)
            .set(userAttribute.toRecord())
            .returning()
            .fetchOne()

        return userAttributeRecord?.toDomainModel()!!
    }

    override fun updateCustomer(userId: String, phoneNumber: String): UserAttribute {
        val userAttributeRecord = ctx.update(Tables.USER_ATTRIBUTE)
            .set(Tables.USER_ATTRIBUTE.PHONE_NUMBER,phoneNumber)
            .where(Tables.USER_ATTRIBUTE.USER_ID.eq(userId))
            .and(Tables.USER_ATTRIBUTE.USER_ROLE.eq(UserRoles.CUSTOMER.name))
            .returning()
            .fetchOne()
            ?: throw EntityNotFoundException(
                entityType = expatrio.jerin.generated.dao.jooq.tables.UserAttribute::class,
                entityValue = userId
            )

        return userAttributeRecord.toDomainModel()
    }

    override fun deleteCustomer(userId: String) {
        ctx.deleteFrom(Tables.USER_ATTRIBUTE)
            .where(Tables.USER_ATTRIBUTE.USER_ID.eq(userId))
            .and(Tables.USER_ATTRIBUTE.USER_ROLE.eq(UserRoles.CUSTOMER.name))
            .execute()
    }
}
