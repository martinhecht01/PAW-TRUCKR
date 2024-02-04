package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.webapp.dto.ErrorDto;
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
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON);

        String message = messageSource.getMessage("exception.Unauthorized", null, LocaleContextHolder.getLocale());
        response.getWriter().write(String.format("{\n \"message\": \"%s\"\n}", message));


    }
}
