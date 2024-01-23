package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_trip_id_seq")
    @SequenceGenerator(sequenceName="trips_trip_id_seq", name = "trips_trip_id_seq", allocationSize = 1)
    @Column(name = "trip_id")
    private Integer tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private User provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trucker_id")
    private User trucker;

    @Column(name = "licenseplate", length = 30)
    private String licensePlate;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "departure_date")
    private Timestamp departureDate;

    @Column(name = "arrival_date")
    private Timestamp arrivalDate;

    @Column(name = "origin", length = 255)
    private String origin;

    @Column(name = "destination", length = 255)
    private String destination;

    @Column(name = "type", length = 255)
    private String type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "trucker_confirmation")
    private Boolean truckerConfirmation;

    @Column(name = "provider_confirmation")
    private Boolean providerConfirmation;

    @Column(name = "confirmation_date")
    private Timestamp confirmationDate;

    @ManyToOne
    @JoinColumn(name = "imageid")
    private Image image;

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER)
    private List<Proposal> proposals;

    @Transient
    private int proposalCount;

    @Transient
    private Proposal offer;

    @Formula("(SELECT COUNT(*) > 0 FROM reviews r JOIN users u ON r.userid = u.userid WHERE u.role = 'PROVIDER' AND r.tripid = trip_id)")
    private boolean truckerSubmittedHisReview;

    @Formula("(SELECT COUNT(*) > 0 FROM reviews r JOIN users u ON r.userid = u.userid WHERE u.role = 'TRUCKER' AND r.tripid = trip_id)")
    private boolean providerSubmittedHisReview;


    // Constructors, getters, and setters

    //constructor completo
    public Trip(
            int tripId,
            User trucker,
            User provider,
            String licensePlate,
            Integer weight,
            Integer volume,
            LocalDateTime departureDate,
            LocalDateTime arrivalDate,
            String origin,
            String destination,
            String type,
            Integer price,
            Boolean truckerConfirmation,
            Boolean providerConfirmation,
            LocalDateTime confirmationDate,
            Image image, int proposalCount,
            boolean truckerReview,
            boolean providerReview
    ) {
        this.tripId = tripId;
        this.provider = provider;
        this.trucker = trucker;
        this.licensePlate = licensePlate;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate == null ? null : Timestamp.valueOf(departureDate);
        this.arrivalDate = arrivalDate == null ? null : Timestamp.valueOf(arrivalDate);
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.truckerConfirmation = truckerConfirmation;
        this.providerConfirmation = providerConfirmation;
        this.confirmationDate = confirmationDate == null ? null : Timestamp.valueOf(confirmationDate);
        this.proposalCount = proposalCount;
        this.image= image;
        this.truckerSubmittedHisReview = truckerReview;
        this.providerSubmittedHisReview = providerReview;
    }

    //constructor para el trucker
    public Trip(User trucker,
                User provider,
                String licensePlate,
                Integer weight,
                Integer volume,
                LocalDateTime departureDate,
                LocalDateTime arrivalDate,
                String origin,
                String destination,
                String type,
                Integer price) {
        this.tripId = null;
        this.provider = provider;
        this.trucker = trucker;
        this.licensePlate = licensePlate;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate == null ? null : Timestamp.valueOf(departureDate);
        this.arrivalDate = arrivalDate == null ? null : Timestamp.valueOf(arrivalDate);
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.truckerConfirmation = false;
        this.providerConfirmation = false;
        this.confirmationDate = null;
        this.proposalCount = 0;
        this.image =null;
    }

    //constructor para el provider
    public Trip(User trucker,
                User provider,
                Integer weight,
                Integer volume,
                LocalDateTime departureDate,
                LocalDateTime arrivalDate,
                String origin,
                String destination,
                String type,
                Integer price) {
        this.tripId = null;
        this.provider = provider;
        this.trucker = trucker;
        this.licensePlate = null;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate == null ? null : Timestamp.valueOf(departureDate);
        this.arrivalDate = arrivalDate == null ? null : Timestamp.valueOf(arrivalDate);
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.truckerConfirmation = false;
        this.providerConfirmation = false;
        this.confirmationDate = null;
        this.proposalCount = 0;
        this.image =null;
    }

    public Trip() {
        // Default constructor required by Hibernate
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(tripId, trip.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId);
    }

    // Getters and setters

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public User getTrucker() {
        return trucker;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public LocalDateTime getDepartureDate() {
        if (departureDate == null) return null;
        return departureDate.toLocalDateTime();
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        if (departureDate == null) this.departureDate = null;
        else this.departureDate = Timestamp.valueOf(departureDate);
    }

    public LocalDateTime getArrivalDate() {
        if (arrivalDate == null) return null;
        return arrivalDate.toLocalDateTime();
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        if(arrivalDate == null) this.arrivalDate = null;
        else this.arrivalDate = Timestamp.valueOf(arrivalDate);
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getTruckerConfirmation() {
        return truckerConfirmation;
    }

    public void setTruckerConfirmation(Boolean truckerConfirmation) {
        this.truckerConfirmation = truckerConfirmation;
    }

    public Boolean getProviderConfirmation() {
        return providerConfirmation;
    }

    public void setProviderConfirmation(Boolean providerConfirmation) {
        this.providerConfirmation = providerConfirmation;
    }

    public LocalDateTime getConfirmationDate() {
        if(confirmationDate == null) return null;
        return confirmationDate.toLocalDateTime();
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        if(confirmationDate == null) this.confirmationDate = null;
        else this.confirmationDate = Timestamp.valueOf(confirmationDate);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Integer getProposalCount() {
        return proposalCount;
    }

    public void setProposalCount(Integer proposalCount) {
        this.proposalCount = proposalCount;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public void setProposalCount(int proposalCount) {
        this.proposalCount = proposalCount;
    }

    public Proposal getOffer() {
        return offer;
    }

    public void setOffer(Proposal offer) {
        this.offer = offer;
    }

    public String getDepartureDateString() {
        if (departureDate == null) return null;
        return departureDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getArrivalDateString() {
        if (arrivalDate == null) return null;
        return arrivalDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getConfirmationDateString() {
        if (confirmationDate == null) return null;
        return confirmationDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setTrucker(User trucker) {
        this.trucker = trucker;
    }

    public boolean getTruckerSubmittedHisReview() {
        return truckerSubmittedHisReview;
    }

    public void setTruckerSubmittedHisReview(boolean truckerSubmittedHisReview) {
        this.truckerSubmittedHisReview = truckerSubmittedHisReview;
    }

    public boolean getProviderSubmittedHisReview() {
        return providerSubmittedHisReview;
    }

    public void setProviderSubmittedHisReview(boolean providerSubmittedHisReview) {
        this.providerSubmittedHisReview = providerSubmittedHisReview;
    }
}
