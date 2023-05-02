package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.ProposalRequest;
import ar.edu.itba.paw.models.Request;
import ar.edu.itba.paw.models.Trip;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RequestService {
    Request createRequest(String cuit,
                          int availableWeight,
                          int availableVolume,
                          LocalDateTime departureDate,
                          LocalDateTime arrivalDate,
                          String origin,
                          String destination,
                          String type,
                          int price);

    List<Request> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate,Integer maxAvailableVolume,Integer maxAvailableWeight, Integer pag);

    Optional<Request> getRequestById(int requestid);

    void acceptRequest(int proposalId);
    List<ProposalRequest> getProposalsForRequestId(int requestId);

    ProposalRequest sendProposal(int requestId, int userid, String description) throws MessagingException;

    Integer getTotalPages(String origin, String destination, Integer minAvailableVolume,Integer maxAvailableVolume, Integer minAvailableWeight,Integer maxAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate);

    List<Request> getAllActiveRequestsByUserId(Integer userid);
}
