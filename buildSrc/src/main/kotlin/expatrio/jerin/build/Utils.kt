package expatrio.jerin.build

fun String.toPackageNameSafeString() = String(this.mapNotNull {
    if (!((65..90).contains(it.toInt()) || (97..122).contains(it.toInt()))) {
        null
    } else {
        it
    }
}.toCharArray())
