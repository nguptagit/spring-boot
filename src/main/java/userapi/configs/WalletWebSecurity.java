package userapi.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import userapi.security.filter.JWTAuthenticationFilter;
import userapi.security.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class WalletWebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	  @Bean
		public PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}
	  
	  @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, "/register").permitAll()
		.antMatchers(HttpMethod.GET, "/confirm").permitAll()
		.antMatchers(HttpMethod.POST, "/password/forgot").permitAll()
		.antMatchers(HttpMethod.POST, "/password/update").permitAll()
		.antMatchers(HttpMethod.GET, "/password/verifyToken").permitAll()
		.antMatchers(HttpMethod.POST,"/login").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JWTAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
	}
	  
	  @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }

}
