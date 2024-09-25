package summarybuddy.server.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
	private final Key key;

	// H M A C 중에서 HS512 선택
	// 비대칭 알고리즘은 공개 키와 개인 키 관리해야함
	public JwtUtil() {
		key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}

	// JWT 생성 메서드
	// 사용자 정보를 활용할 경우를 위해 UserDetails 객체를 사용
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	// JWT 생성에 필요한 내부 메서드
	private String createToken(Map<String, Object> claims, String subject) {
		// 만료 시간을 1시간으로 설정 (밀리초 단위)
		long EXPIRATION_TIME = 1000 * 60 * 60;
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuer("summary buddy")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key)
				.compact();
	}

	// 클레임 추출 로직
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	// JWT 유효성 검사 메서드
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// 토큰 만료 여부 확인 메서드
	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
}
