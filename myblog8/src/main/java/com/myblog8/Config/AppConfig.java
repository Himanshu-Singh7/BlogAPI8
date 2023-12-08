package com.myblog8.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

	@Bean
    public UserDetailsService userDetailsService(){

      UserDetails user = User.builder().username("Himanshu").password(passwordEncoder().encode("12345")).roles("USER").build();

      UserDetails admin = User.builder().username("Rahul").password(passwordEncoder().encode("abc")).roles("ADMIN").build();

      return new InMemoryUserDetailsManager(user,admin);
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
         return new BCryptPasswordEncoder();
    }
}
