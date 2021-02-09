package ch.supsi.webapp;

import ch.supsi.webapp.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/filter/**").permitAll()
			.antMatchers("/img/**").permitAll()
			.antMatchers("/styles.css").permitAll()
			.antMatchers("/script.js").permitAll()
			.antMatchers("/search.js").permitAll()
			.antMatchers("/item/new").authenticated()
			.antMatchers("/item/*/delete").hasRole("ADMIN")
			.antMatchers("/item/*/edit").authenticated()
			.antMatchers("/groups").authenticated()
			.antMatchers("/item/**").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/fonts/**").permitAll()
			.antMatchers("/login", "/register").permitAll()
			.antMatchers("/items/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			//.successHandler()
			.loginPage("/login")
			.failureUrl("/login?error")
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/");
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailService);
		authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

		auth.authenticationProvider(authProvider);
	}

}
