package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.DescriptionLinkDto;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Component
public class DescriptionControllerApi {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDescription(){
        return Response.ok(DescriptionLinkDto.fromDescriptionLink()).build();
    }

}
