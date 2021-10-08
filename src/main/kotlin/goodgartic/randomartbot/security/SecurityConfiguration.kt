package goodgartic.randomartbot.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Value("\${administration.password}")
    private val password: String,
    private val environment: Environment
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors()
                .and()
            .csrf()
                .disable()
            .formLogin()
                .and()
            .authorizeRequests()
                .anyRequest()
                .authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        // TODO: Maybe use password hash here some day
        auth.inMemoryAuthentication()
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            .withUser("Tim")
            .password(password)
            .roles("USER")
    }

    @Bean
    fun configurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        // There is no need to configure CORS in the production
        // as the web is running on the same domain and is served from the spring static resources
        override fun addCorsMappings(registry: CorsRegistry) {
            if (environment.acceptsProfiles(Profiles.of("development"))) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:8081")
                    .allowCredentials(true)
            }
        }
    }
}