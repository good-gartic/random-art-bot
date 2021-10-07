package goodgartic.randomartbot.repositories

import goodgartic.randomartbot.entity.Link
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LinksRepository : CrudRepository<Link, UUID>