package kr.mitgames.codingteacher.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mitgames.codingteacher.Application;
import kr.mitgames.codingteacher.user.EndUser;
import kr.mitgames.codingteacher.user.EndUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;
import static kr.mitgames.codingteacher.auth.PREFIX.HEADER_NAME;
import static kr.mitgames.codingteacher.auth.PREFIX.TOKEN_PREFIX;
import static kr.mitgames.codingteacher.auth.PREFIX.SECRET;
import static kr.mitgames.codingteacher.auth.PREFIX.EXPIRATION_TIME;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authManager;

    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            byte[] string = new byte[1024];
            req.getInputStream().read(string);
            Application.LOG.warning("[mapped enduser] data "+new String(string));
            EndUserDTO creds = new ObjectMapper()
                    .readValue(new String(string), EndUserDTO.class);
            Application.LOG.warning("[mapped enduser] "+creds.getLoginId()+" "+creds.getPassword());
            return authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getLoginId(),
                            creds.getPassword(),
                            emptyList()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_NAME, TOKEN_PREFIX + token);
    }
}
