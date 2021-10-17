package goodgartic.randomartbot.listeners

import goodgartic.randomartbot.service.GarticArtLinksService
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
@Suppress("LeakingThis")
class PostNowCommandListener(api: JDA, private val service: GarticArtLinksService): ListenerAdapter() {

    private val signature = "gg!random-art"

    init {
        api.addEventListener(this)
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

        // Only handle messages exactly matching the specified signature
        if (event.message.contentRaw.trim() == signature) {
            service.repostRandomGarticArtLink()
            event.message.addReaction("\uD83D\uDC4D").queue()
        }
    }
}
