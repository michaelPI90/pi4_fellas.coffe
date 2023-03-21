package com.web.pi3s.SpringWeb.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity

@Configuration
public class WebSecurityConfig{
    
   
     
     
     
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http

    .httpBasic()
    .and()
    .authorizeHttpRequests()
    .requestMatchers(HttpMethod.GET, "/api/usuario/listarTodos**").hasAnyRole("ADMIN", "ESTOQUISTA")
     .requestMatchers(HttpMethod.POST, "/api/usuario/salvar**").hasRole("ADMIN")
     .requestMatchers(HttpMethod.GET, "/api/usuario/**").permitAll().and().csrf().disable();


     
     
     
// .authenticated().and()
// .csrf().disable();
        return http.build();
    


    }


       
        @Bean
         PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder() {
                
            };

    
}

}

