package goodgartic.randomartbot.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class EntityNotFoundException : RuntimeException("Entity not found")