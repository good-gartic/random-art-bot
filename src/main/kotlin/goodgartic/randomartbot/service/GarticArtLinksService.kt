package goodgartic.randomartbot.service

import goodgartic.randomartbot.configuration.DiscordConfiguration
import goodgartic.randomartbot.entity.GarticArtLink
import goodgartic.randomartbot.exceptions.EntityNotFoundException
import goodgartic.randomartbot.repositories.GarticArtLinksRepository
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.interactions.components.Button
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
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

        // Create the message embed
        val embed = createLinkEmbed(link, message)
        val action = repostChannel.sendMessageEmbeds(embed)

        // Add jump to the original message button component
        message?.let { action.setActionRow(Button.link(it.jumpUrl, "Jump to the original message")) }

        // Send the embed
        action.queue()

        // And finally delete sent link from the database to prevent duplicates
        repository.delete(link)
    }

    fun loadLinks(page: Int): Page<GarticArtLink> {
        val itemsPerPage = 32
        val sort = Sort.by(
                Sort.Order.asc("messageId"),
                Sort.Order.asc("image"),
                Sort.Order.asc("approved")
        )

        // Put the not-approved entries first
        return repository.findAll(PageRequest.of(page, itemsPerPage, sort))
    }

    fun approveAll(): Int {
        val entities = repository.findAllByApprovedIsFalse()
        val updated = entities.map {
            it.approved = true
            it
        }

        repository.saveAll(updated)

        return updated.size
    }

    fun approve(id: UUID): GarticArtLink {
        val item = repository.findByIdOrNull(id) ?: throw EntityNotFoundException()
        return repository.save(item.apply { approved = true })
    }

    fun delete(id: UUID): GarticArtLink {
        val item = repository.findByIdOrNull(id) ?: throw EntityNotFoundException()
        return item.also { repository.delete(it) }
    }

    private fun getRandomGarticArtLink(): GarticArtLink? {
        // Select a random row from 0 to N, where N is the number of all links in the database
        val selected = Random.nextLong(max(repository.countByApprovedIsTrue(), 1)).toInt()
        val pageable = PageRequest.of(selected, 1)

        // The returned page will contain exactly 1 element or be empty.
        // Empty page indicates, that the database contains no more links
        return repository.findAllByApprovedIsTrue(pageable).firstOrNull()
    }

    private fun fetchMessageById(id: Long): Message? {
        return try { artChannel.retrieveMessageById(id).complete() }
               catch (exception: Throwable) { null }
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
                    .setDescription(message.contentRaw)
                    .setAuthor(message.author.name, null, message.author.avatarUrl)
                    .setFooter("Original message posted at")
                    .setTimestamp(message.timeEdited ?: message.timeCreated)
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