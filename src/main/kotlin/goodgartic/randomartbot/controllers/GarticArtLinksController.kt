package goodgartic.randomartbot.controllers

import goodgartic.randomartbot.entity.GarticArtLink
import goodgartic.randomartbot.service.GarticArtLinksService
import goodgartic.randomartbot.service.ImportService
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/links")
class GarticArtLinksController(
    private val service: GarticArtLinksService,
    private val importService: ImportService
) {
    @GetMapping
    fun page(@RequestParam page: Int? = null): ResponseEntity<Page<GarticArtLink>> =
        ResponseEntity.ok(service.linksPage(page ?: 0))

    @PostMapping("/import")
    fun import(@RequestParam file: MultipartFile): ResponseEntity<*> {
        // For now, only JSON files are supported
        if (file.contentType != MediaType.APPLICATION_JSON_VALUE) {
            return ResponseEntity.badRequest().build<Any>()
        }

        return try {
            val json = String(file.bytes)
            val items = importService.processJsonExport(json)

            ResponseEntity.ok(
                mapOf(
                    "success" to true,
                    "message" to "Imported $items art links"
                )
            )
        }
        catch (exception: Exception) {
            ResponseEntity.unprocessableEntity()
                .body(
                    mapOf(
                        "success" to false,
                        "message" to exception.message
                    )
                )
        }
    }

    @PostMapping("/approve/{id}")
    fun approve(@PathVariable id: UUID): ResponseEntity<*> = ResponseEntity.ok(service.approve(id))

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<*> = ResponseEntity.ok(service.delete(id))
}