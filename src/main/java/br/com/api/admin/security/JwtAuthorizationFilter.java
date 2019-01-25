package br.com.api.admin.security;

import br.com.api.admin.config.ApiErrorConfig;
import br.com.api.admin.exception.BusinessException;
import br.com.api.admin.exception.ErrorResponse;
import br.com.api.admin.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            Object json = new ObjectMapper().writeValueAsString(Arrays.asList(
                    new ErrorResponse.ApiError(String.valueOf(HttpStatus.FORBIDDEN.value()),
                            ApiErrorConfig.apiErrorMessageSource().getMessage("generic-3", null, Locale.getDefault()))));

            response.getWriter().append(json.toString());
        }
        //chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtUtil.isTokenValido(token)) {
            String username = jwtUtil.getUsername(token);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }

}
