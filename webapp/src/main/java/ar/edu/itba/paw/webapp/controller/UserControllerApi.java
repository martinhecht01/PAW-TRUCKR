package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.EditUserForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("users")
@Component
public class UserControllerApi {
    private final UserService us;
    private final ImageService is;
    private final TripServiceV2 ts;
    private final ReviewService revs;

    @Autowired
    public UserControllerApi(final UserService us, ImageService is, ReviewService revs, TripServiceV2 ts){
        this.us = us;
        this.is = is;
        this.revs = revs;
        this.ts = ts;
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    otra manera con un costom mimetype
//    @Produces("applictation/vnd.userlist.v1+json")
//    public Response listUsers(final int page){
//          final List<User> userList = us.getAll(page);
//          if (userList.isEmpty()){
//              return Response.noContent().build();
//          }
//          return Response.ok(userList).
//              .link("", "next")
//              .link("", "prev")
//              .link("", "first")
//              .link("", "last")
//              .build();
//    }


    @POST
    public Response createUser(@Valid UserForm form){
        final User user = us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword(), LocaleContextHolder.getLocale());
        return Response.created(null).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id){
        Optional<User> maybeUser = us.getUserByCuit(id);
        if(!maybeUser.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeUser.get()).build();
    }

//    @PUT
//    @Path("/{id}")
//    public

//    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
//    public ModelAndView editUser(@Valid @ModelAttribute("editUserForm") final EditUserForm form, final BindingResult errors){
//        if (errors.hasErrors()) {
//            return editUserView(form);
//        }
//        if(!form.getProfileImage().isEmpty()) {
//            int imgId = is.uploadImage(form.getProfileImage().getBytes());
//            us.updateProfilePicture(getCurrentUser().getUserId(), imgId);
//        }
//        us.updateProfileName(getCurrentUser().getUserId(), form.getName());
//
//
//        return new ModelAndView("redirect:/profile");
//    }



//    @RequestMapping(value = "/register", method = { RequestMethod.POST })
//    public ModelAndView create(@Valid @ModelAttribute("userForm") final UserForm form, final BindingResult errors) {
//        if (errors.hasErrors()) {
//            return register(false, "", form);
//        }
//
//        User user = us.createUser(form.getEmail(), form.getName(), form.getCuit(), form.getRole(), form.getPassword(),LocaleContextHolder.getLocale());
//        if(user == null){
//            LOGGER.info("User with CUIT {} already exists", form.getCuit());
//            errors.rejectValue("cuit", "alreadyExists");
//            return register(false, "", form);
//        }
//        LOGGER.info("User created with CUIT {} ", form.getCuit());
//        return new ModelAndView("redirect:/register?success=true&email=" + user.getEmail());
//    }
//

//    public void deleteUser(){}

}