package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesPersistence.RequestDao;
import ar.edu.itba.paw.interfacesPersistence.UserDao;
import ar.edu.itba.paw.interfacesServices.MailService;
import ar.edu.itba.paw.interfacesServices.RequestService;
import ar.edu.itba.paw.models.Request;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Request> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate, Integer pag){
        return requestDao.getAllActiveRequests(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate,pag);
    }

    @Override
    public Optional<Request> getRequestById(int tripid){
        return requestDao.getRequestById(tripid);
    }

    @Override
    public Request acceptRequest(int requestId, String email, String name, String cuit ){
        User user = userDao.getUserByCuit(cuit).get();
        int acceptUserId = user.getUserId();

        Request req = requestDao.getRequestById(requestId).get();
        Request acceptedRequest = requestDao.acceptRequest(req, acceptUserId);
        User tripOwner = userDao.getUserById(acceptedRequest.getUserId());
//        ms.sendEmail(tripOwner, user, acceptedRequest);
        return acceptedRequest;
    }

    @Override
    public Integer getTotalPages(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate) {
        return requestDao.getTotalPages(origin, destination,minAvailableVolume, minAvailableWeight, minPrice, maxPrice, sortOrder, departureDate, arrivalDate);
    }

    @Override
    public List<Request> getAllActiveRequestsByUserId(Integer userid){
        return requestDao.getAllActiveRequestsByUserId(userid);
    }


}
