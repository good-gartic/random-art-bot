package goodgartic.randomartbot.repositories

import goodgartic.randomartbot.entity.GarticArtLink
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GarticArtLinksRepository : CrudRepository<GarticArtLink, UUID>, PagingAndSortingRepository<GarticArtLink, UUID>