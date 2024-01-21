package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.webapp.controller.utils.CacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("images")
@Controller
public class ImageControllerApi {

    private final ImageService is;

    @Autowired
    public ImageControllerApi(final ImageService is){
        this.is = is;
    }

    @GET
    @Path("/{id}")
    public Response getImage(@PathParam("id") int id, @Context javax.ws.rs.core.Request request ){

        EntityTag etag = new EntityTag(Integer.toString(id));
        Response.ResponseBuilder response = CacheHelper.getConditionalCacheResponse(request, etag);

        if (response == null){
            byte[] image = is.getImage(id);
            return Response.ok(image).tag(etag).build();
        }

        return response.build();
    }


}
