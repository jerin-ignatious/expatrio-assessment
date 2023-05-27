package expatrio.jerin.data.impl

import expatrio.jerin.common.models.UserAttribute
import expatrio.jerin.data.UserAttributeDbAccess
import expatrio.jerin.generated.dao.jooq.Tables
import expatrio.jerin.generated.dao.jooq.tables.records.UserAttributeRecord
import expatrio.jerin.mapper.toDomainModel
import expatrio.jerin.mapper.toRecord
import org.jooq.DSLContext

class UserAttributeDbAccessImpl(
    private val ctx: DSLContext
) : UserAttributeDbAccess {
    override fun getAllUsersByRole(userRole: String): List<UserAttribute> {
        val userAttributeRecords = ctx.selectFrom(Tables.USER_ATTRIBUTE)
            .where(Tables.USER_ATTRIBUTE.USER_ROLE.eq(userRole))

        return userAttributeRecords.map { it.toDomainModel() }
    }

    override fun createCustomer(userAttribute: UserAttribute): UserAttribute {
        val userAttributeRecord = ctx.insertInto(Tables.USER_ATTRIBUTE)
            .set(userAttribute.toRecord())
            .returning()
            .fetchOne()

        return userAttributeRecord?.toDomainModel()!!
    }
}
