package kr.mitgames.codingteacher.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import kr.mitgames.codingteacher.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;
import static kr.mitgames.codingteacher.auth.PREFIX.HEADER_NAME;
import static kr.mitgames.codingteacher.auth.PREFIX.TOKEN_PREFIX;
import static kr.mitgames.codingteacher.auth.PREFIX.SECRET;
import static kr.mitgames.codingteacher.auth.PREFIX.EXPIRATION_TIME;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_NAME);
        if(header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_NAME);
        Application.LOG.warning("[getAuthentication]");
        if(token != null) {
            Application.LOG.warning("[getAuthentication] token= "+token);
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                            .build()
                            .verify(token.replace(TOKEN_PREFIX, ""))
                            .getSubject();
            Application.LOG.warning("[getAuthentication] user= "+user);
            if(user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, emptyList());
            }
            return null;
        }
        return null;
    }
}
