package goodgartic.randomartbot.tasks

import goodgartic.randomartbot.configuration.DiscordConfiguration
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PostRandomGarticArtTask(private val configuration: DiscordConfiguration, private val guild: Guild) {

    private val artChannel: TextChannel = guild.getTextChannelById(configuration.artChannelId)
        ?: throw IllegalStateException("Cannot find the configured art channel (${configuration.artChannelId})")

    private val repostChannel: TextChannel = guild.getTextChannelById(configuration.repostChannelId)
        ?: throw IllegalStateException("Cannot find the configured repost channel (${configuration.repostChannelId})")

    @Scheduled(fixedRateString = "#{\${discord.repost-interval} * 1000}")
    fun run() {
    }
}