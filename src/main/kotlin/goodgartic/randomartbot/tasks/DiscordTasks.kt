package goodgartic.randomartbot.tasks

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DiscordTasks {
    @Scheduled(fixedRateString = "#{\${discord.repost-interval} * 1000}")
    fun postRandomGarticArt() {
        println("Posting random art...")
    }
}