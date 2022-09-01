package com.gl.usersservice.app.config;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gl.usersservice.app.exception.CustomException;
import com.gl.usersservice.app.service.CustomerService;
import com.gl.usersservice.app.util.SecurityUtil;
import com.gl.usersservice.core.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gl.usersservice.app.exception.CustomException.ExceptionDefinition.CUSTOMER_NOT_FOUND_ERROR;


@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final CustomerService customerService;
    private final ExceptionHandlerExceptionResolver resolver;
    private SecurityUtil securityUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (!ObjectUtils.isEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if (ObjectUtils.isEmpty(jwt)) {
                throw new JWTVerificationException("Token cannot be empty");
            } else {
                try {
                    String email = securityUtil.validateTokenAndRetrieveSubject(jwt);
                    Customer customer = customerService.findByEmail(email).orElseThrow(() -> new CustomException(CUSTOMER_NOT_FOUND_ERROR));
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, customer.getPassword(), getDefaultAuthorities());
                    if (ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException | CustomException ex) {
                    resolver.resolveException(request, response, null, new RuntimeException(ex));
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> getDefaultAuthorities() {
        return Stream.of(new SimpleGrantedAuthority("USER")).collect(Collectors.toList());
    }


}
