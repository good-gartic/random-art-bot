package goodgartic.randomartbot.entity

import org.hibernate.Hibernate
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
) {
    // Methods optimized for Hibernate ORM performance

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        if (other !is GarticArtLink) return false

        return id == other.id
    }

    override fun hashCode(): Int = 0

    override fun toString(): String = this::class.simpleName + "(id = $id)"
}