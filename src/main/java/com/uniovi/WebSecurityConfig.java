package com.uniovi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/css/**", "/img/**", "/script/**", "/", "/singup", "/login/**", "/admin/login/**",
						"/files/**","/verPoliticaPrivacidad","/resetPass")
				.permitAll().antMatchers("/files/**").permitAll()// hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
				.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
				// .antMatchers("/invitacion/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				// .antMatchers("/publicacion/crearPublicacion/**").hasAnyAuthority("ROLE_USER",
				// "ROLE_ADMIN")
				// .antMatchers("/publicacion/listPublicacionesPropias/**").hasAnyAuthority("ROLE_USER",
				// "ROLE_ADMIN")
				// .antMatchers("/publicacion/listPublicacionesAmigos/**").hasAnyAuthority("ROLE_USER",
				// "ROLE_ADMIN")
				// .antMatchers("/admin/listAllUsers/**").hasAuthority("ROLE_ADMIN")
				// .antMatchers("/fragments/adminNav/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/home").failureUrl("/login-error").and().logout().permitAll()
				.logoutSuccessUrl("/login");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
