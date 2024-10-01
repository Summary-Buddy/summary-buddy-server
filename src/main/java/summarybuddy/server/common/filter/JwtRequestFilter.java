package summarybuddy.server.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import summarybuddy.server.common.util.JwtUtil;
import summarybuddy.server.member.repository.domain.CustomUserDetails;
import summarybuddy.server.member.service.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
// 요청이 중복으로 처리되지 않도록 OncePerRequestFilter 를 사용
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    // OncePerRequestFilter 를 위해 정의해야 하는 부분
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (checkPassUri(request)) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // JWT 토큰 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // "Bearer " 제거하는 부분
            username = jwtUtil.extractUsername(jwt); // JWT 에서 username 을 추출하는 부분
        }

        // JWT 가 유효하고 SecurityContext 에 Authentication 이 설정되어 있지 않은 경우
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // JWT 의 유효성을 검사
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken); // 인증 정보 설정
            }
        }

        chain.doFilter(request, response); // 필터 체인 계속 진행
    }

    private Boolean checkPassUri(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/swagger-ui/**")
                || request.getRequestURI().startsWith("/api/member/join")
                || request.getRequestURI().startsWith("/api/member/check-username")
                || request.getRequestURI().startsWith("/api/login");
    }
}
