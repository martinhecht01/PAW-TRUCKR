package ar.edu.itba.paw.interfacesPersistence;

import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.ProposalRequest;
import ar.edu.itba.paw.models.Request;
import ar.edu.itba.paw.models.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RequestDao {

    Request create(int userid,
                   int availableWeight,
                   int availableVolume,
                   LocalDateTime departureDate,
                   LocalDateTime arrivalDate,
                   String origin,
                   String destination,
                   String type,
                   int price);

    ProposalRequest createProposal(int tripid, int userid, String description);

    List<ProposalRequest> getProposalsForRequestId(int tripid);

    Optional<ProposalRequest> getProposalById(int proposalId);
    List<Request> getAllActiveRequests(String origin, String destination, Integer minAvailableVolume, Integer minAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate,Integer maxAvailableVolume,Integer maxAvailableWeight, Integer pag);

    List<Request> getAllActiveRequestsByUserId(Integer userId);

    Integer getTotalPages(String origin, String destination, Integer minAvailableVolume,Integer maxAvailableVolume, Integer minAvailableWeight,Integer maxAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate);

    Optional<Request> getRequestById(int reqid);

    void acceptRequest(int proposalId);
}
