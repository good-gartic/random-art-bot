package goodgartic.randomartbot.configuration

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

// The class need to be final in order to safely dereference api in other properties

@Component
final class DiscordBeans(configuration: DiscordConfiguration) {

    // the @get:Bean is shorthand for applying @Bean annotation to the compiler-generated getter
    // https://kotlinlang.org/docs/annotations.html#java-annotations

    @get:Bean
    val api: JDA = JDABuilder.createDefault(configuration.token)
        .setActivity(Activity.watching("your art in #gartic-art"))
        .setStatus(OnlineStatus.ONLINE)
        .build()
        .awaitReady()

    @get:Bean
    val guild = api.getGuildById(configuration.guildId)
        ?: throw IllegalStateException("Cannot find the configured guild (${configuration.guildId})")
}