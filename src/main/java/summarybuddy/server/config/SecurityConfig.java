package summarybuddy.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import summarybuddy.server.common.filter.JsonUsernamePasswordAuthenticationFilter;
import summarybuddy.server.common.filter.JwtRequestFilter;
import summarybuddy.server.common.util.JwtUtil;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtUtil jwtUtil;
	private final JwtRequestFilter jwtRequestFilter;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				// 테스트 용 localhost:3000
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
					config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setAllowCredentials(true); // 자격 증명 허용
					return config;
				}))
				// Swagger
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth ->
						auth.requestMatchers("/swagger-ui/**", "/v3/**", "/error", "/api/member/join", "/api/member/check-username").permitAll()
								.anyRequest().authenticated()
				);

		// JWT 필터를 추가
		http
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http
				.addFilterBefore(
						jsonUsernamePasswordAuthenticationFilter(),
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
		JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter
				= new JsonUsernamePasswordAuthenticationFilter(jwtUtil, new ObjectMapper());
		jsonUsernamePasswordAuthenticationFilter
				.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return jsonUsernamePasswordAuthenticationFilter;
	}
}
