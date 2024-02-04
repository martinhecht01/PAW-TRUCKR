package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.controller.utils.CacheHelper;
import ar.edu.itba.paw.webapp.dto.DescriptionLinkDto;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("")
@Component
public class DescriptionControllerApi {

    @Context
    private UriInfo uriInfo;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDescription(@Context Request request){
//        EntityTag eTag = new EntityTag(String.valueOf(DescriptionLinkDto.fromDescriptionLink(uriInfo).hashCode()));
//        Response.ResponseBuilder response = CacheHelper.getConditionalCacheResponse(request, eTag);
//        if (response == null){
            return Response.ok(DescriptionLinkDto.fromDescriptionLink(uriInfo)).build();
//        }
//        return response.build();
    }

}
