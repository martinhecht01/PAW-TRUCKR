package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.exceptions.CustomInternalServerErrorException;
import ar.edu.itba.paw.interfacesServices.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.controller.utils.CacheHelper;
import ar.edu.itba.paw.webapp.controller.utils.ImageHelper;
import ar.edu.itba.paw.webapp.dto.ImageDto;
import ar.edu.itba.paw.webapp.form.constraints.annotations.RequireImage;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@Path("images")
@Component
public class ImageControllerApi {

    private final static int MAX_IMAGE_SIZE = 1024 * 1024 * 10;

    private final ImageService is;

    @Autowired
    public ImageControllerApi(final ImageService is){
        this.is = is;
    }

    @Context
    private UriInfo uriInfo;


    @GET
    @Path("/{id:\\d+}")
    @Produces("image/*")
    public Response getImage(
            @PathParam("id") int id,
            @DefaultValue("FULL") @Pattern(regexp = "FULL|PROFILE|SQUARE", message = "validation.ImageSize.Pattern") @QueryParam("size") String size,
            @Context javax.ws.rs.core.Request request ){
        Image image = is.getImage(id).orElseThrow(ImageNotFoundException::new);
        Response.ResponseBuilder response;
        try {
            response = Response.ok(ImageHelper.valueOf(size).resizeImage(image.getImage()));
        }catch(IOException e){
            throw new CustomInternalServerErrorException("exception.ResizeImage");
        }
        CacheHelper.setUnconditionalCache(response);
        return response.build();
    }

    @POST
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
//    @Produces("image/*")
    public Response uploadImage(
            @RequireImage @FormDataParam("image") FormDataBodyPart image,
            @Size(max = MAX_IMAGE_SIZE, message = "validation.Image.Size") @FormDataParam("image") byte[] imageBytes){
        int id = is.uploadImage(imageBytes);
        Image img = is.getImage(id).orElseThrow(ImageNotFoundException::new);
        return Response.created(uriInfo.getBaseUriBuilder()
                .path("/images/")
                .path(String.valueOf(id)).build())
                .entity(ImageDto.fromImage(uriInfo, img))
                .build();
    }


}
