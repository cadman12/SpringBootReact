package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.config.filter.JWTAuthorizationFilter;
import edu.pnu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authConfig;
	private final MemberRepository memberRepo;
	private final OAuth2SuccessHandler oauth2SuccessHandler;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable());
		http.cors(cors->cors.configurationSource(corsSource()));
		
		http.authorizeHttpRequests(auth->auth
			.requestMatchers("/api/**", "/user/**", "/h2-console/**").permitAll()
			.anyRequest().authenticated()
		);
		
		http.sessionManagement(ssmn->ssmn.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.formLogin(frm->frm.disable());
		http.httpBasic(basic->basic.disable());
		
		http.addFilter(new JWTAuthenticationFilter(authConfig.getAuthenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepo), AuthorizationFilter.class);
		
		http.headers(header->header.frameOptions(fo->fo.disable()));

		http.oauth2Login(oauth2->oauth2.successHandler(oauth2SuccessHandler));
		
		return http.build();
	}
    
    private CorsConfigurationSource corsSource() {
    	CorsConfiguration config = new CorsConfiguration();
    	config.addAllowedOriginPattern(CorsConfiguration.ALL);
    	config.addAllowedMethod(CorsConfiguration.ALL);
    	config.addAllowedHeader(CorsConfiguration.ALL);
    	config.addExposedHeader(HttpHeaders.AUTHORIZATION);
    	config.setAllowCredentials(true);	// 쿠키 전송 허용 

    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", config);
    	return source;
    }
}

