package com.spring.social.security;
import com.spring.social.security.oauth2.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class JwtSocialAuthSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			"/",
			"/**/documentation/**",
			"/versions/1/auth/**",
			"/oauth2/**",
			"/h2-console/**",
			"/**/v2/api-docs",           // swagger
			"/**/webjars/**",            // swagger-ui webjars
			"/**/swagger-resources/**",  // swagger-ui resources
			"/**/configuration/**",      // swagger configuration
			"/*.html",
			"/favicon.ico",
			"/**/*.html",
			"/**/*.css",
			"/**/*.js"
	};


	 UserDetailsServiceImpl userDetailsService;

	 CustomOAuth2UserService customOAuth2UserService;

	CustomOidcUserService customOidcUserService;

	 OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	 OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	 HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	 JwtRequestFilter jwtRequestFilter;

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
		http
				.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated()
				.and()
				.oauth2Login()
				.authorizationEndpoint()
				.baseUri("/oauth2/authorize")
				.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
				.and()
				.redirectionEndpoint()
				.baseUri("/oauth2/callback/*")
				.and()
				.userInfoEndpoint()
				.userService(customOAuth2UserService)
				.oidcUserService(customOidcUserService)
				.and()
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler);

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

}