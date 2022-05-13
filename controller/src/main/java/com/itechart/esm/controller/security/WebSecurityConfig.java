package com.itechart.esm.controller.security;

import com.itechart.esm.controller.security.jwt.AuthEntryPointJwt;
import com.itechart.esm.controller.security.jwt.AuthTokenFilter;
import com.itechart.esm.controller.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_GIFT_CERT_PAGE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_IN;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SIGN_UP;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final AuthTokenFilter authTokenFilter;

	@Autowired
	public WebSecurityConfig(AuthTokenFilter authTokenFilter) {
		this.authTokenFilter = authTokenFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and().csrf()
				.disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(URL_MAIN_GIFT_CERT_PAGE + URL_SIGN_UP)
				.permitAll()
				.anyRequest().authenticated();

		http.addFilterAfter(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
