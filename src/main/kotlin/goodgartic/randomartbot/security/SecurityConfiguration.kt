package goodgartic.randomartbot.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfiguration(@Value("\${administration.password}") private val password: String) : WebSecurityConfigurerAdapter() {

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
}