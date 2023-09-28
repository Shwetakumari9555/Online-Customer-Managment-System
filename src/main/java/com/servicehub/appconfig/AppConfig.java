package com.servicehub.appconfig;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {
	@Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {

		http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
		.cors(cors ->{
			cors.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration configuration= new CorsConfiguration();
					configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
					configuration.setAllowedMethods(Collections.singletonList("*"));
					configuration.setAllowCredentials(true);
					configuration.setAllowedHeaders(Collections.singletonList("*"));
					configuration.setExposedHeaders(Arrays.asList("Authorization"));
					return configuration;
				}
			});
		})
		.authorizeHttpRequests(auth ->{
			auth
				.requestMatchers(HttpMethod.POST,"/users").permitAll()
				.requestMatchers(HttpMethod.GET, "/user/logIn").hasRole("USER")
		        .requestMatchers(HttpMethod.POST, "/bookings/**").hasRole("USER")
		        .requestMatchers(HttpMethod.PUT, "/bookings/*/rooms").hasRole("USER")
		        .requestMatchers(HttpMethod.PUT, "/bookings/*/").hasRole("USER")
		        .requestMatchers(HttpMethod.GET, "/users/*/bookings").hasRole("USER")
		        .requestMatchers(HttpMethod.GET, "/*/recommended-properties").hasRole("USER")
		       
				.anyRequest().authenticated();
				})
			.csrf(csrf -> csrf.disable())
			.formLogin(Customizer.withDefaults())
			.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
			.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
			.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}


//		.authorizeHttpRequests(auth ->{
//			auth
//				.requestMatchers(HttpMethod.POST,"/customers").permitAll()
//				.requestMatchers(HttpMethod.GET, "/customers","/hello").hasRole("ADMIN")
//				.requestMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN","USER")
//				.anyRequest().authenticated();
//			
//				})
//			.csrf(csrf -> csrf.disable())
//			.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//			.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
//			.formLogin(Customizer.withDefaults())
//			.httpBasic(Customizer.withDefaults());
//		

	


