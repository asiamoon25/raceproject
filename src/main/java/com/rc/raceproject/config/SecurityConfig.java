package com.rc.raceproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rc.raceproject.domain.ErrorResponse;
import com.rc.raceproject.domain.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // non-client 프로젝트에서는 disable 을 권장
        http
                .csrf((csrfConfig) ->
                    csrfConfig.disable()
                )
                .authorizeHttpRequests((authorizeRequests) ->
                    authorizeRequests
                            .requestMatchers("/","/member/**").permitAll()
                            .requestMatchers("/api/v2/fetch-horse-info").hasRole(Role.BATCH.name())
                            .requestMatchers("/member/join/proc").permitAll()
                            .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                    formLogin
                            .loginPage("/member/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .loginProcessingUrl("/member/login/proc")
                            .defaultSuccessUrl("/", true)
                )
                .logout((logoutConfig) ->
                    logoutConfig.logoutSuccessUrl("/")
                )
                .userDetailsService(customUserDetailService)
                .exceptionHandling((exceptionConfig) ->
                    exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint = ((request, response, authException) -> {
        ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring Security unauthorized...");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String json = new ObjectMapper().writeValueAsString(fail);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        System.out.println(authException.toString());
        writer.write(json);
        writer.flush();
    });

    private final AccessDeniedHandler accessDeniedHandler = ((request, response, accessDeniedException) -> {
       ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring Security unauthorized...");
       response.setStatus(HttpStatus.FORBIDDEN.value());
       String json = new ObjectMapper().writeValueAsString(fail);
       response.setContentType(MediaType.APPLICATION_JSON_VALUE);
       PrintWriter writer = response.getWriter();
       writer.write(json);
       writer.flush();
    });

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
