package goodgartic.randomartbot.service

import com.fasterxml.jackson.databind.ObjectMapper
import goodgartic.randomartbot.entity.GarticArtLink
import goodgartic.randomartbot.repositories.GarticArtLinksRepository
import org.springframework.stereotype.Service

@Service
class ImportService(private val repository: GarticArtLinksRepository) {

    data class RawGarticArt(val image: String, val message: Long?)

    fun processJsonBatch(json: String) {
        val entries = extractGarticArtProperties(json)
        val entities = entries.map { GarticArtLink(image = it.image, messageId = it.message, approved = false) }

        repository.saveAll(entities)
    }

    private fun extractGarticArtProperties(json: String): List<RawGarticArt> {
        val reader = ObjectMapper()
        val source = reader.readTree(json) ?: throw IllegalArgumentException("Cannot parse the JSON tree")

        return source.get("messages").iterator()
            .asSequence()
            .map {
                val id = it.get("id")?.asLong()
                val image = it.get("attachments")?.get(0)?.get("url")?.asText() ?: return@map null

                RawGarticArt(
                    image = image,
                    message = id
                )
            }
            .filterNotNull()
            .toList()
    }
}