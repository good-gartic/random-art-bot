package goodgartic.randomartbot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "discord")
data class DiscordConfiguration(
    val token: String,
    val guildId: Long,
    val artChannelId: Long,
    val repostChannelId: Long,
    val repostInterval: Long,
    val developerId: Long
)