package at.massedterm.massedterm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	//The source of data from a database, this can (and is) set in the application.properties (otherwise the embedded h2 database with schema data.sql/schema.sql would be used)
	@Autowired
	DataSource dataSource;
	
	//Override auth. mechanism, in this case override with jdbc mechanism
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource);
				/*Create static users with the default db schema.
				.withDefaultSchema()
					.withUser(
							User.withUsername("user")
							.password("password")
							.roles("USER")
					)
					.withUser(
							User.withUsername("admin")
							.password("password")
							.roles("ADMIN")
					);*/
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/user").hasAnyRole("ADMIN","USER")
				.antMatchers("/").permitAll()
				.and().formLogin();
	}
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
}