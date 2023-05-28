package expatrio.jerin.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("expatrio.jerin")
class ExpatrioProperties {
    val authentication = AuthenticationProperties()

    class AuthenticationProperties {
        var secretKey: String = "abcder"
        var expirationTimeMillis: Int = 1800000 // 30 mins
    }
}
