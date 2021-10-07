package goodgartic.randomartbot.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "links")
data class Link(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "message_id", unique = true, nullable = false)
    val messageId: Long,

    @Column(name = "image_url", unique = true, nullable = false)
    val imageUrl: String,

    @Column(name = "is_approved", nullable = false)
    var approved: Boolean = false
)