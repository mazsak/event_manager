package com.example.event_manager.configuration;

import com.example.event_manager.service.UserService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(
            "/user/login/**",
            "/user/register",
            "/events/all",
            "/events/details/**",
            "/predefineds/all")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
        })
        .failureHandler(
            (httpServletRequest, httpServletResponse, e) ->
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN))
        .loginPage("/user/login")
        .defaultSuccessUrl("/events/all")
        .failureUrl("/user/login/error")
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutSuccessUrl("/user/login/logout")
        .permitAll()
        .and()
        .csrf()
        .disable();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService((UserDetailsService) userService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
