package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.RequestDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.RequestService;
import ar.edu.itba.paw.models.*;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class RequestServiceImpl implements RequestService {

    private final RequestDao requestDao;
    private final UserDao userDao;
    private final MailService ms;

    @Autowired
    public RequestServiceImpl(RequestDao requestDao, UserDao userDao, MailService ms) {
        this.requestDao = requestDao;
        this.userDao = userDao;
        this.ms = ms;
    }

    @Override
    public Request createRequest(String cuit,
                                 int availableWeight,
                                 int availableVolume,
                                 LocalDateTime departureDate,
                                 LocalDateTime arrivalDate,
                                 String origin,
                                 String destination,
                                 String type,
                                 int price)
    {
        User user = userDao.getUserByCuit(cuit).get();
        int userId = user.getUserId();
        return requestDao.create(userId, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type, price);
    }

    @Override
    public List<Request> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate,Integer maxAvailableVolume,Integer maxAvailableWeight, Integer pag){
        return requestDao.getAllActiveRequests(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate,maxAvailableVolume,maxAvailableWeight,pag);
    }

    @Override
    public Optional<Request> getRequestById(int requestId){
        return requestDao.getRequestById(requestId);
    }
    
    @Override
    public Optional<Request> getRequestByIdAndUserId(int requestId, int userid){
        return requestDao.getRequestByIdAndUserId(requestId, userid);
    }

    @Override
    public void acceptRequest(int proposalid ){
        requestDao.acceptRequest(proposalid);
        ProposalRequest proposal = requestDao.getProposalById(proposalid).get();
        Request request = requestDao.getRequestById(proposal.getRequestid()).get();

        ;
        User requestOwner = userDao.getUserById(request.getUserId()).get();
        User proposed = userDao.getUserById(proposal.getUserid()).get();
        try{ms.sendRequestEmail(requestOwner,request);}
        catch(MessagingException e){
            throw new RuntimeException();
        }
        try{ms.sendRequestEmail(proposed,request);}
        catch(MessagingException e){
            throw new RuntimeException();
        }
    }
    
    @Override
    public ProposalRequest sendProposal(int requestId, int userid, String description){
        ProposalRequest prop = requestDao.createProposal(requestId, userid, description);
        Request request = requestDao.getRequestById(requestId).get();
        try{
            ms.sendProposalRequestEmail(userDao.getUserById(request.getUserId()).get(),prop);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

    @Override
    public Integer getTotalPages(String origin, String destination, Integer minAvailableVolume,Integer maxAvailableVolume, Integer minAvailableWeight,Integer maxAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate) {
        return requestDao.getTotalPages(origin, destination,minAvailableVolume,maxAvailableVolume, minAvailableWeight,maxAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate);
    }
    @Override
    public List<ProposalRequest> getProposalsForRequestId(int requestId){
        return requestDao.getProposalsForRequestId(requestId);
    }
    @Override
    public List<Request> getAllActiveRequestsByUserId(Integer userid){
        return requestDao.getAllActiveRequestsByUserId(userid);
    }

    @Override
    public List<Pair<Request, Integer>> getAllActiveRequestsAndProposalCount(int userId) {
        return requestDao.getAllActiveRequestsAndProposalCount(userId);
    }

    @Override
    public List<Request> getAllAcceptedRequestsByUserId(int userId) {
        return requestDao.getAllAcceptedRequestsByUserId(userId);
    }


}
