package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    public void authConfig(AuthenticationManagerBuilder auth,
                           PasswordEncoder passwordEncoder,
                           UserAuthService userAuthService) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mem_user")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .and()
                .withUser("mem_guest")
                .password(passwordEncoder.encode("password"))
                .roles("GUEST");

        auth.userDetailsService(userAuthService);
    }

    @Configuration
    public static class UiWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/**/*.css", "/**/*.js").permitAll()
                    .antMatchers("/user").hasAnyRole("ADMIN", "SUPER_ADMIN")
                    .antMatchers("/product").hasAnyRole("ADMIN", "SUPER_ADMIN")
                    .antMatchers("/user/**").hasRole("SUPER_ADMIN")
                    .antMatchers("/product/**").hasRole("SUPER_ADMIN")
                    .antMatchers("/access_denied").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login_processing")
                    .defaultSuccessUrl("/")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .logoutUrl("/logout")
                    .permitAll()
                    .and().csrf().disable().headers()
                    .frameOptions().sameOrigin()
                    .httpStrictTransportSecurity().disable()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access_denied");
        }
    }
}
