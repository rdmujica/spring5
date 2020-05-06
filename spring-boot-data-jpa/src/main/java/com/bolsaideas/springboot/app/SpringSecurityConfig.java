package com.bolsaideas.springboot.app;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsaideas.springboot.app.auth.handler.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//para las anotaciones
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	@Qualifier("jpaUserDetailService")
	UserDetailsService userDetailsService;

	/* Usuarios en memoria
	 * @Autowired public void configurerGlobal(AuthenticationManagerBuilder builder)
	 * throws Exception { PasswordEncoder encoder = this.passwordEncoder; //
	 * UserBuilder users = User.builder().passwordEncoder(password -> //
	 * encoder.encode(password)); UserBuilder users =
	 * User.builder().passwordEncoder(encoder::encode);
	 * builder.inMemoryAuthentication().withUser(users.username("admin").password(
	 * "12345").roles("ADMIN", "USER"))
	 * .withUser(users.username("rafael").password("123456").roles("USER")); }
	 */
	
	/* JDBC
	 * @Autowired public void configurerGlobal(AuthenticationManagerBuilder builder)
	 * throws Exception { builder .jdbcAuthentication() .dataSource(dataSource)
	 * .passwordEncoder(passwordEncoder)
	 * .usersByUsernameQuery("select username,password,enabled from users where username=?"
	 * )
	 * .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?"
	 * ); }
	 */
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder
		.userDetailsService(userDetailsService);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar","/h2-console/**").permitAll()
				//.antMatchers("/ver/**").hasAnyRole("USER")
				//.antMatchers("/uploads/**").hasAnyRole("USER")
				//.antMatchers("/form/**").hasAnyRole("ADMIN")
				//.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				//.antMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().successHandler(loginSuccessHandler).loginPage("/login").permitAll()
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403")
				.and().csrf().ignoringAntMatchers("/h2-console/**")
				.and().headers().frameOptions().sameOrigin();
	}
	
	

}