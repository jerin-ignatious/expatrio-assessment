package expatrio.jerin.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import java.time.ZoneId
import java.util.*

@SpringBootApplication(
    exclude = [SecurityAutoConfiguration::class, ManagementWebSecurityAutoConfiguration::class],
    scanBasePackages = ["expatrio.jerin"]
)
class ExpatrioApp {
    init {
        TimeZone.setDefault(TIME_ZONE_UTC)
    }

    companion object {
        val TIME_ZONE_UTC: TimeZone = TimeZone.getTimeZone(ZoneId.of("UTC"))

        @JvmStatic
        fun main(args: Array<String>) {
            val app = SpringApplication(ExpatrioApp::class.java)
            app.webApplicationType = WebApplicationType.REACTIVE
            app.run(*args)
        }
    }
}
