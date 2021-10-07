package goodgartic.randomartbot.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "links")
data class GarticArtLink(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "image_url", unique = true, nullable = false)
    val image: String,

    @Column(name = "message_id", unique = true, nullable = false)
    val messageId: Long? = null,

    @Column(name = "is_approved", nullable = false)
    var approved: Boolean = false
)