package goodgartic.randomartbot.controllers

import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@Secured("ROLE_USER")
@RequestMapping("/")
class HomepageController {

    // TODO: Map this 1:1 to produced vue site builds copied to /resources in CI pipeline

    @GetMapping
    fun index() = "index"

}