package br.com.api.admin.security;

import br.com.api.admin.config.ApiErrorConfig;
import br.com.api.admin.entity.User;
import br.com.api.admin.utils.JwtUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import static br.com.api.admin.exception.ErrorResponse.ApiError;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserSpringSecurity) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);

        Object json = new ObjectMapper().writeValueAsString(new UserResponse(username, token));
        response.getWriter().append(json.toString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());

        Object json = new ObjectMapper().writeValueAsString(Arrays.asList(
                new ApiError(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                        ApiErrorConfig.apiErrorMessageSource().getMessage("error-user-10", null, Locale.getDefault()))));

        response.getWriter().append(json.toString());
    }

    @JsonAutoDetect(fieldVisibility = ANY)
    public static class UserResponse {

        private final String username;
        private final String token;

        public UserResponse(String username, String token) {
            this.username = username;
            this.token = token;
        }
    }

}
