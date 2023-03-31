package com.web.pi3s.SpringWeb.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity

@Configuration
public class WebSecurityConfig{
    
   
     
     
     
    @Bean
     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    // // http

    // // .httpBasic()
    // // .and()
    // // .authorizeHttpRequests()
    // // .requestMatchers(HttpMethod.GET, "/**").permitAll()
    // //  .requestMatchers(HttpMethod.GET, "/api/usuario/index**").permitAll().
    // //  requestMatchers(HttpMethod.GET, "/api/usuario/login**").permitAll()
    // //  .and().csrf().disable();


     
     return http.httpBasic().and().authorizeHttpRequests(authorizeconfig -> {
          
            authorizeconfig.requestMatchers("/logar").permitAll();
            authorizeconfig.requestMatchers("/listarTodos").permitAll();
            authorizeconfig.anyRequest().authenticated();

         
            

     })
     
     .csrf().disable()
     .build();
// .authenticated().and()

       
    


    }


       
        @Bean
         PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder() {
                
            };

    
}

}

