package expatrio.jerin.common.exception

import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue

open class CommonException(message: String, cause: Throwable? = null) : RuntimeException(message) {
    init {
        cause?.let { initCause(cause) }
    }
}

class EntityNotFoundException(entityType: KClass<*>, entityValue: String) :
    CommonException("Unable to fetch $entityType for $entityValue")
