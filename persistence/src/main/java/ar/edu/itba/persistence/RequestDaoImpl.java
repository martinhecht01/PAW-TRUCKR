package ar.edu.itba.persistence;

import ar.edu.itba.paw.interfacesPersistence.RequestDao;
import ar.edu.itba.paw.models.Proposal;
import ar.edu.itba.paw.models.ProposalRequest;
import ar.edu.itba.paw.models.Request;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class RequestDaoImpl implements RequestDao {

    private final static RowMapper<Request> ROW_MAPPER = (rs, rowNum) -> {
        LocalDateTime departure = rs.getTimestamp("mindeparturedate").toLocalDateTime();
        LocalDateTime arrival = rs.getTimestamp("maxarrivaldate").toLocalDateTime();
        return new Request(
                rs.getInt("requestid"),
                rs.getInt("userid"),
                rs.getInt("requestedweight"),
                rs.getInt("requestedvolume"),
                departure,
                arrival,
                rs.getString("origin"),
                rs.getString("destination"),
                rs.getString("type"),
                rs.getInt("maxprice"),
                rs.getInt("acceptuserid")
        );
    };

    private final static RowMapper<Pair<Request, Integer>> ACTIVE_REQUEST_COUNT_MAPPER = (rs, rowNum) -> new Pair<>(ROW_MAPPER.mapRow(rs, rowNum), rs.getInt("proposalcount"));


    private final static RowMapper<ProposalRequest> PROPOSALREQUEST_ROW_MAPPER = (rs, rowNum) -> new ProposalRequest(
            rs.getInt("proposalid"),
            rs.getInt("requestid"),
            rs.getInt("userid"),
            rs.getString("description"),
            rs.getString("name")
    );


    private static Integer ITEMS_PER_PAGE = 10;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private SimpleJdbcInsert jdbcProposalRequestInsert;
    @Autowired
    public RequestDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "  userid SERIAL PRIMARY KEY,\n" +
                "  cuit VARCHAR(255) UNIQUE NOT NULL,\n" +
                "  email VARCHAR(255) NOT NULL,\n" +
                "  name VARCHAR(255) NOT NULL\n" +
                ");");
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS requests (\n" +
                        "  requestid SERIAL PRIMARY KEY,\n" +
                        "  userid INT NOT NULL REFERENCES users(userid),\n" +
                        "  requestedweight INT,\n" +
                        "  requestedvolume INT,\n" +
                        "  mindeparturedate TIMESTAMP,\n" +
                        "  maxarrivaldate TIMESTAMP,\n" +
                        "  origin VARCHAR(255),\n" +
                        "  destination VARCHAR(255),\n" +
                        "  type VARCHAR(255),\n" +
                        "  maxprice INT,\n" +
                        "  acceptuserid INT REFERENCES users(userid)\n" +
                        ");"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS proposalrequests (\n" +
                        "  proposalid SERIAL PRIMARY KEY,\n" +
                        "  requestid INT NOT NULL REFERENCES requests(requestid)," +
                        "  userid INT NOT NULL REFERENCES users(userid),\n" +
                        "  description VARCHAR(300)\n" +
                        ");"
        );
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("requests").usingGeneratedKeyColumns("requestid");
        this.jdbcProposalRequestInsert = new SimpleJdbcInsert(ds).withTableName("proposalrequests").usingGeneratedKeyColumns("proposalid");
    }

    @Override
    public Request create(final int userid,
                          final int availableWeight,
                          final int availableVolume,
                          final LocalDateTime departureDate,
                          final LocalDateTime arrivalDate,
                          final String origin,
                          final String destination,
                          final String type,
                          final int price)
    {

        HashMap<String, Object> data = new HashMap<>();

        //put all the data in the hashmap casting to string
        data.put("userid", userid);
        data.put("requestedweight", availableWeight);
        data.put("requestedvolume", availableVolume);
        data.put("mindeparturedate", Timestamp.valueOf(departureDate));
        data.put("maxarrivaldate", Timestamp.valueOf(arrivalDate));
        data.put("origin", origin);
        data.put("destination", destination);
        data.put("type", type);
        data.put("maxprice", price);

        int requestId = jdbcInsert.executeAndReturnKey(data).intValue();
        return new Request(requestId, userid, availableWeight, availableVolume, departureDate, arrivalDate, origin, destination, type,price,-1);
    }
    @Override
    public ProposalRequest createProposal(int requestid, int userid, String description){
        HashMap<String, Object> data = new HashMap<>();
        data.put("requestid", requestid);
        data.put("userid", userid);
        data.put("description", description);
        int key = jdbcProposalRequestInsert.executeAndReturnKey(data).intValue();
        return new ProposalRequest(key, requestid, userid, description,"");
    }

    @Override
    public List<ProposalRequest> getProposalsForRequestId(int requestid){
        String query = "SELECT * FROM proposalrequests NATURAL JOIN users WHERE requestid =  ?";
        return jdbcTemplate.query(query, PROPOSALREQUEST_ROW_MAPPER, requestid);
    }

    @Override
    public Optional<ProposalRequest> getProposalById(int proposalId){
        String query = "SELECT * FROM proposalrequests NATURAL JOIN users WHERE proposalid = ?";
        List<ProposalRequest> proposals = jdbcTemplate.query(query, PROPOSALREQUEST_ROW_MAPPER, proposalId);
        return proposals.isEmpty() ? Optional.empty() : Optional.of(proposals.get(0));
    }
    @Override
    public List<Request> getAllActiveRequests(String origin, String destination, Integer availableVolume, Integer availableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate,Integer maxAvailableVolume, Integer maxAvailableWeight, Integer pag) {
        if (pag < 1)
            pag = 1;
        Integer offset = (pag - 1) * 10;
        String query = "SELECT * FROM requests WHERE acceptuserid IS NULL AND mindeparturedate >= now()";
        List<Object> params = new ArrayList<>();


        if (origin != null && !origin.equals("")) {
            query = query + " AND origin = ?";
            params.add(origin);
        }

        if (destination != null && !destination.equals("")) {
            query = query + " AND destination = ?";
            params.add(destination);
        }

        if (availableVolume != null) {
            query = query + " AND requestedvolume >= ?";
            params.add(availableVolume);
        }

        if (availableWeight != null) {
            query = query + " AND requestedweight >= ?";
            params.add(availableWeight);
        }

        if (minPrice != null) {
            query = query + " AND maxprice >= ?";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            query = query + " AND maxprice <= ?";
            params.add(maxPrice);
        }

        if (departureDate != null && !departureDate.equals("")) {
            query = query + " AND DATE(mindeparturedate) = CAST(? AS DATE)";
            params.add("'" + departureDate + "'");
        }

        if (arrivalDate != null && !arrivalDate.equals("")) {
            query = query + " AND DATE(maxarrivaldate) = CAST(? AS DATE)";
            params.add("'" + arrivalDate + "'");
        }

        if (maxAvailableVolume != null) {
            query = query + " AND requestedvolume <= ?";
            params.add(maxAvailableVolume);
        }
        if (maxAvailableWeight != null) {
            query = query + " AND requestedweight <= ?";
            params.add(maxAvailableWeight);
        }

        if (sortOrder != null && !sortOrder.isEmpty()) {
            //sort order asc and desc
            if (sortOrder.equals("departureDate ASC")) {
                query = query + " ORDER BY mindeparturedate ASC";
            } else if (sortOrder.equals("departureDate DESC")) {
                query = query + " ORDER BY mindeparturedate DESC";
            } else if (sortOrder.equals("arrivalDate ASC")) {
                query = query + " ORDER BY maxarrivaldate ASC";
            } else if (sortOrder.equals("arrivalDate DESC")) {
                query = query + " ORDER BY maxarrivaldate DESC";
            } else if (sortOrder.equals("price ASC")) {
                query = query + " ORDER BY maxprice ASC";
            } else if (sortOrder.equals("maxprice DESC")) {
                query = query + " ORDER BY maxprice DESC";
            }
        }
        query = query + " LIMIT ? OFFSET ?";
        params.add(ITEMS_PER_PAGE);
        params.add(offset);

        return jdbcTemplate.query(query, params.toArray(), ROW_MAPPER);
        }


    @Override
    public List<Request> getAllActiveRequestsByUserId(Integer userId) {
        String query = "SELECT * FROM requests WHERE userid = ? AND acceptuserid IS NULL";
        return jdbcTemplate.query(query, ROW_MAPPER, userId);
    }

    @Override
    public Integer getTotalPages(String origin, String destination, Integer availableVolume, Integer maxAvailableVolume, Integer availableWeight,Integer maxAvailableWeight, Integer minPrice, Integer maxPrice, String sortOrder, String departureDate, String arrivalDate) {
        String query = "SELECT COUNT(*) FROM requests WHERE acceptuserid IS NULL AND mindeparturedate >= now()";
        List<Object> params = new ArrayList<>();
        if (origin != null && !origin.equals("")) {
            query = query + " AND origin = ?";
            params.add(origin);
        }

        if (destination != null && !destination.equals("")) {
            query = query + " AND destination = ?";
            params.add(destination);
        }

        if (availableVolume != null) {
            query = query + " AND requestedvolume >= ?";
            params.add(availableVolume);
        }

        if (availableWeight != null) {
            query = query + " AND requestedweight >= ?";
            params.add(availableWeight);
        }

        if (minPrice != null) {
            query = query + " AND maxprice >= ?";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            query = query + " AND maxprice <= ?";
            params.add(maxPrice);
        }

        if (departureDate != null && !departureDate.equals("")) {
            query = query + " AND DATE(mindeparturedate) = CAST(? AS DATE)";
            params.add("'" + departureDate + "'");
        }

        if (arrivalDate != null && !arrivalDate.equals("")) {
            query = query + " AND DATE(maxarrivaldate) = CAST(? AS DATE)";
            params.add("'" + arrivalDate + "'");
        }

        if (maxAvailableVolume != null) {
            query = query + " AND requestedvolume <= ?";
            params.add(maxAvailableVolume);
        }
        if (maxAvailableWeight != null) {
            query = query + " AND requestedweight <= ?";
            params.add(maxAvailableWeight);
        }

        if (sortOrder != null && !sortOrder.isEmpty()) {
            //sort order asc and desc
            if (sortOrder.equals("departureDate ASC")) {
                query = query + " ORDER BY mindeparturedate ASC";
            } else if (sortOrder.equals("departureDate DESC")) {
                query = query + " ORDER BY mindeparturedate DESC";
            } else if (sortOrder.equals("arrivalDate ASC")) {
                query = query + " ORDER BY maxarrivaldate ASC";
            } else if (sortOrder.equals("arrivalDate DESC")) {
                query = query + " ORDER BY maxarrivaldate DESC";
            } else if (sortOrder.equals("price ASC")) {
                query = query + " ORDER BY maxprice ASC";
            } else if (sortOrder.equals("maxprice DESC")) {
                query = query + " ORDER BY maxprice DESC";
            }
        }
        return (int) Math.ceil((double) jdbcTemplate.queryForObject(query, params.toArray(), Integer.class) /ITEMS_PER_PAGE);
    }
    @Override
    public Optional<Request> getRequestById(int reqid){
        List<Request> requests= jdbcTemplate.query("SELECT * FROM requests WHERE requestid = ?", ROW_MAPPER, reqid);
        return requests.isEmpty() ? Optional.empty() : Optional.of(requests.get(0));
    }

    @Override
    public void acceptRequest(int proposalId){
        ProposalRequest proposal = getProposalById(proposalId).get();
        System.out.println("PROPOSAL DESCRIPTION = " + proposal.getDescription());
        jdbcTemplate.update("UPDATE requests SET acceptuserid = ? WHERE requestid = ?", proposal.getUserid() , proposal.getRequestid());
    }

    @Override
    public List<Pair<Request, Integer>> getAllActiveRequestsAndProposalCount(int userid) {
        String query = "SELECT requests.*, COUNT(proposalrequests.proposalid) AS proposalcount FROM requests LEFT JOIN proposalrequests ON requests.requestid = proposalrequests.requestid WHERE requests.userid = ? AND acceptuserid IS NULL GROUP BY requests.requestid";
        return jdbcTemplate.query(query, ACTIVE_REQUEST_COUNT_MAPPER, userid);
    }

    @Override
    public List<Request> getAllAcceptedRequestsByUserId(int userId) {
        String query = "SELECT * FROM requests WHERE userid = ? AND acceptuserid IS NOT NULL";
        return jdbcTemplate.query(query, ROW_MAPPER, userId);
    }

    @Override
    public Optional<Request> getRequestByIdAndUserId(int reqid, int userid){
        return getRequestById(reqid).filter(request -> request.getUserId() == userid);
    }

    @Override
    public List<Request> getAllRequestsInProgressByAcceptUserId(Integer acceptuserid){
        String query = "SELECT * FROM requests WHERE acceptuserid = ? AND confirmation_date IS NULL";
        return jdbcTemplate.query(query, ROW_MAPPER, acceptuserid);
    }
}
