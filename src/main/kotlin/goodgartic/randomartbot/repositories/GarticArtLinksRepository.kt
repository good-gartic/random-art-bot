package goodgartic.randomartbot.repositories

import goodgartic.randomartbot.entity.GarticArtLink
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GarticArtLinksRepository : CrudRepository<GarticArtLink, UUID>, PagingAndSortingRepository<GarticArtLink, UUID> {

    fun countByApprovedIsTrue(): Long

    fun findAllByApprovedIsTrue(pageable: Pageable): Page<GarticArtLink>

    fun findAllByApprovedIsFalse(pageable: Pageable): Page<GarticArtLink>

    fun findAllByApprovedIsFalse(): List<GarticArtLink>

}