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

/**
 *???
 * das heißt die daten kommen aus der datenbank die in application.properties definiert ist. ansonsten aus data.sql/schema.sql
 * was ist die data.sql/schema.sql???
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	//The source of data from a database, this can (and is) set in the application.properties
	// (otherwise the embedded h2 database with schema data.sql/schema.sql would be used)
	@Autowired
	DataSource dataSource;

	/**
	 * auth. = authentication???
	 * was heißt jdbc??? -> Java Database Connectivity = Datenbankverbindungsfähigkeit -> die Fähigkeit aus der Datenbank etwas rauszulesen.
	 * @param auth
	 * @throws Exception
	 */
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
		//disable the need to use csrf token for a successfull post request
		//TODO: re-enable csrf and add token to POST-requests
		http.csrf().disable();
		//redirect to formLogin page if not logged in
		http.authorizeRequests()
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/user").hasAnyRole("ADMIN","USER")
				.antMatchers("/**").hasAnyRole("ADMIN","USER")
				.and().formLogin();
	}
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
}
