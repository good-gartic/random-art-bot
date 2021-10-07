package goodgartic.randomartbot.tasks

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PostRandomGarticArtTask {

    @Scheduled(fixedRateString = "#{\${discord.repost-interval} * 1000}")
    fun run() {
        println("Posting random art...")
    }
}