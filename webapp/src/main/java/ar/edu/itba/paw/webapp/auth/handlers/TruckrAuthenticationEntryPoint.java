package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.webapp.exceptions.AuthErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


public class TruckrAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Autowired
    private MessageSource messageSource;

    public TruckrAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=realmName must be specified");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON);
        String message;

        try {
            message = messageSource.getMessage(e.getMessage(), null, request.getLocale());
        } catch (Exception ex) {
            response.getWriter().write(String.format("{\n \"message\": \"%s\"\n}", e.getMessage()));
            return;
        }
        response.getWriter().write(String.format("{\n \"message\": \"%s\"\n}", message));

    }
}
