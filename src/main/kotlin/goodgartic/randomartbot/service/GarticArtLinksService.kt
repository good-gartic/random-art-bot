package goodgartic.randomartbot.service

import goodgartic.randomartbot.configuration.DiscordConfiguration
import goodgartic.randomartbot.repositories.GarticArtLinksRepository
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import org.springframework.stereotype.Service

@Service
class GarticArtLinksService(
    private val guild: Guild,
    private val configuration: DiscordConfiguration,
    private val repository: GarticArtLinksRepository
) {
    private val artChannel: TextChannel = guild.getTextChannelById(configuration.artChannelId)
        ?: throw IllegalStateException("Cannot find the configured art channel (${configuration.artChannelId})")

    private val repostChannel: TextChannel = guild.getTextChannelById(configuration.repostChannelId)
        ?: throw IllegalStateException("Cannot find the configured repost channel (${configuration.repostChannelId})")

    fun repostRandomGarticArtLink() {
       // TODO
    }
}