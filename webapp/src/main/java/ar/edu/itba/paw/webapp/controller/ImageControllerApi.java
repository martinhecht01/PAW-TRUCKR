package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfacesServices.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.utils.CacheHelper;
import ar.edu.itba.paw.webapp.dto.ImageDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.constraints.annotations.RequireImage;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("images")
@Component
public class ImageControllerApi {

    private final ImageService is;

    @Autowired
    public ImageControllerApi(final ImageService is){
        this.is = is;
    }

    @Context
    private UriInfo uriInfo;

    //TODO resizing
    @GET
    @Path("/{id}")
    public Response getImage(@PathParam("id") int id, @Context javax.ws.rs.core.Request request ){
        Image image = is.getImage(id).orElseThrow(ImageNotFoundException::new);
        Response.ResponseBuilder response = Response.ok(image.getImage());
        CacheHelper.setUnconditionalCache(response);
        return response.build();
    }

    @POST
    public Response uploadImage(
            @RequireImage @FormDataParam("image") FormDataBodyPart image,
            @FormDataParam("image") byte[] imageBytes)
    {
        int id = is.uploadImage(imageBytes);
        Image img = is.getImage(id).orElseThrow(ImageNotFoundException::new);
        return Response.created(uriInfo.getBaseUriBuilder().path("/images/").path(String.valueOf(id)).build()).entity(img.getImage()).build();
    }


}
