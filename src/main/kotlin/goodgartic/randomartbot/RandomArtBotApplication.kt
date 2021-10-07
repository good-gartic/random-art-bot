package goodgartic.randomartbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = ["goodgartic.randomartbot.configuration"])
class RandomArtBotApplication

fun main(args: Array<String>) {
    runApplication<RandomArtBotApplication>(*args)
}
