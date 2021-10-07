package goodgartic.randomartbot.tasks

import goodgartic.randomartbot.service.GarticArtLinksService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PostRandomGarticArtTask(private val service: GarticArtLinksService) {

    @Scheduled(fixedRateString = "#{\${discord.repost-interval} * 1000}")
    fun run() {
        service.repostRandomGarticArtLink()
    }
}