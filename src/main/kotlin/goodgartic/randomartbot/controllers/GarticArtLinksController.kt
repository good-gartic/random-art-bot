package goodgartic.randomartbot.controllers

import goodgartic.randomartbot.entity.GarticArtLink
import goodgartic.randomartbot.service.GarticArtLinksService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/links")
class GarticArtLinksController(private val service: GarticArtLinksService) {

    @GetMapping
    fun page(@RequestParam page: Int? = null): ResponseEntity<Page<GarticArtLink>> =
        ResponseEntity.ok(service.linksPage(page ?: 0))
}