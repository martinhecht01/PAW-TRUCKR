package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.interfacesServices.ImageService;
import ar.edu.itba.paw.interfacesServices.ReviewService;
import ar.edu.itba.paw.interfacesServices.TripServiceV2;
import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.interfacesServices.exceptions.TripOrRequestNotFoundException;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.webapp.controller.utils.PaginationHelper;
import ar.edu.itba.paw.webapp.dto.ProposalDto;
import ar.edu.itba.paw.webapp.dto.TripDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.AcceptForm;
import ar.edu.itba.paw.webapp.form.EditUserForm;
import ar.edu.itba.paw.webapp.form.TripForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.function.CurryingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("offers")
@Component
public class OffersControllerApi {
    private final String PAGE_SIZE = "12";
    private final String PAGE = "1";

    private final UserService us;
    private final TripServiceV2 ts;


    @Context
    private UriInfo uriInfo;

    @Autowired
    public OffersControllerApi(final UserService us, TripServiceV2 ts){
        this.us = us;
        this.ts = ts;
    }

    @POST
//    @Consumes("application/vnd.proposal.v1+json");
//    @Produces("application/vnd.proposal.v1+json");
    public Response createOffer(@Valid AcceptForm form,@QueryParam ("tripId") int tripId){
        User user = us.getUserById(1).get();//TODO: get user from session
        final Proposal proposal = ts.createProposal(tripId,user.getUserId(), form.getDescription(), form.getPrice(), LocaleContextHolder.getLocale());
        return Response.created(uriInfo.getBaseUriBuilder().path("/offers/").path(String.valueOf(proposal.getProposalId())).build()).entity(ProposalDto.fromProposal(uriInfo,proposal)).build();
    }
    @GET
    @Path("/{id}")
    @Produces("application/vnd.proposal.v1+json")
    public Response getOffer(@PathParam("id") int id){
        final Proposal proposal = ts.getProposalById(id).get();
        return Response.ok(ProposalDto.fromProposal(uriInfo,proposal)).build();
    }
    private <T,R> Function<T,R> currifyUriInfo(CurryingFunction<UriInfo, T,R> fun) {
        return fun.curry(fun,uriInfo);
    }
    @GET
    @Produces("application/vnd.proposalList.v1+json")
    public Response getOffers(@QueryParam("tripId") int tripId, @QueryParam("page") @DefaultValue(PAGE) int page, @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize){
        final List<Proposal> proposalList = ts.getAllProposalsForTripId(tripId); // TODO: make this pageable
        if(proposalList.isEmpty()){
            return Response.noContent().build();
        }

        final List<ProposalDto> proposalDtos = proposalList.stream().map(currifyUriInfo(ProposalDto::fromProposal)).collect(Collectors.toList());

        int maxPages = 1; //TODO: pagination trips
        Response.ResponseBuilder toReturn = Response.ok(new GenericEntity<List<ProposalDto>>(proposalDtos){});
        PaginationHelper.getLinks(toReturn,uriInfo,page,maxPages);

        return toReturn.build();
    }




}
