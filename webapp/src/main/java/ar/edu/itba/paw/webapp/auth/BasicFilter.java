package ar.edu.itba.paw.webapp.auth;


import ar.edu.itba.paw.interfacesServices.UserService;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class BasicFilter extends OncePerRequestFilter {

    private static final int CUIT = 0;
    private static final int PASSWORD = 1;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Basic ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String[] credentials = extractAndDecodeHeader(header);

            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials[CUIT], credentials[PASSWORD])
            );

            userService.getUserByCuit(credentials[CUIT]).ifPresent(user -> response.setHeader("X-JWT", jwtTokenUtil.createToken(user, baseUrl(request))));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, failed);
            return;
        }

        chain.doFilter(request, response);
    }

    private String[] extractAndDecodeHeader(String header) {
        byte[] base64Token = header.split(" ")[1].trim().getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);

        } catch (IllegalArgumentException e) {
            //TODO exception
            throw new RuntimeException();
//            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            //TODO exception
            throw new RuntimeException();
//            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};

    }

    private String baseUrl(HttpServletRequest request){
        return  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

}
