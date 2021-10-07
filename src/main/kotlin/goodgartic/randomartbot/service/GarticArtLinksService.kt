package goodgartic.randomartbot.service

import goodgartic.randomartbot.configuration.DiscordConfiguration
import goodgartic.randomartbot.entity.GarticArtLink
import goodgartic.randomartbot.repositories.GarticArtLinksRepository
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.interactions.components.Button
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.random.Random
import kotlin.math.max

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
        val link = getRandomGarticArtLink() ?: return reportEmptyDatabase()
        val message = link.messageId?.let { fetchMessageById(it) }

        // TODO: Edit the sent embed
        val embed = createLinkEmbed(link, message)
        val action = repostChannel.sendMessageEmbeds(embed)

        message?.let {
            action.setActionRow(Button.link(it.jumpUrl, "Jump to the original message"))
        }

        action.queue()
    }

    private fun getRandomGarticArtLink(): GarticArtLink? {
        // Select a random row from 0 to N, where N is the number of all links in the database
        val selected = Random.nextLong(max(repository.count(), 1)).toInt()
        val pageable = PageRequest.of(selected, 1)

        // The returned page will contain exactly 1 element or be empty.
        // Empty page indicates, that the database contains no more links
        return repository.findAll(pageable).firstOrNull()
    }

    private fun fetchMessageById(id: Long): Message? {
        return artChannel.retrieveMessageById(id).complete()
    }

    private fun createLinkEmbed(link: GarticArtLink, message: Message?): MessageEmbed {
        val builder = EmbedBuilder()
            .setTitle("Random gartic art")
            .setImage(link.image)
            .setColor(0x0a5efb)
            .setTimestamp(Instant.now())

        // TODO: Maybe add other interesting information?
        message?.let {
            builder
                .setAuthor(message.author.name, null, message.author.avatarUrl)
                .setFooter("Posted at ${message.timeCreated}")
                .addField("Message link", it.jumpUrl, false)
        }

        return builder.build()
    }

    private fun reportEmptyDatabase() {
        val embed = EmbedBuilder()
            .setTitle("Oh no!")
            .setDescription("The gartic art links database returned no results!\nThis is probably a bug.")
            .setColor(0xed4245)
            .setTimestamp(Instant.now())
            .build()

        repostChannel
            .sendMessage("Hey, ho <@${configuration.developerId}>!")
            .setEmbeds(embed)
            .queue()
    }
}