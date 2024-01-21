package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.webapp.controller.utils.CacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("images")
@Component
public class ImageControllerApi {

    private final ImageService is;

    @Autowired
    public ImageControllerApi(final ImageService is){
        this.is = is;
    }


    //TODO resizing
    @GET
    @Path("/{id}")
    public Response getImage(@PathParam("id") int id, @Context javax.ws.rs.core.Request request ){
        byte[] image = is.getImage(id);
        Response.ResponseBuilder response = Response.ok(image);
        CacheHelper.setUnconditionalCache(response);
        return response.build();
    }


}
