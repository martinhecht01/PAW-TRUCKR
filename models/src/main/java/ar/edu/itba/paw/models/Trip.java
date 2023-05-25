package ar.edu.itba.paw.models;

import java.sql.Timestamp;
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
    private Number weight;

    @Column(name = "volume")
    private Number volume;

    @Column(name = "departure_date")
    private Timestamp departureDate;

    @Column(name = "arrival_date")
    private Timestamp arrivalDate;

    @Column(name = "origin", length = 50)
    private String origin;

    @Column(name = "destination", length = 50)
    private String destination;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "price")
    private Number price;

    @Column(name = "trucker_confirmation")
    private Boolean truckerConfirmation;

    @Column(name = "provider_confirmation")
    private Boolean providerConfirmation;

    @Column(name = "confirmation_date")
    private Timestamp confirmationDate;

    @ManyToOne
    @JoinColumn(name = "imageid")
    private Image image;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private List<Proposal> proposals;

    @Transient
    private int proposalCount;
    // Constructors, getters, and setters

    //constructor completo
    public Trip(int tripId, User trucker, User provider, String licensePlate, Number weight, Number volume, Timestamp departureDate, Timestamp arrivalDate, String origin, String destination, String type,
                Number price, Boolean truckerConfirmation, Boolean providerConfirmation, Timestamp confirmationDate, Image image, int proposalCount) {
        this.tripId = tripId;
        this.provider = provider;
        this.trucker = trucker;
        this.licensePlate = licensePlate;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.truckerConfirmation = truckerConfirmation;
        this.providerConfirmation = providerConfirmation;
        this.confirmationDate = confirmationDate;
        this.proposalCount = proposalCount;
        this.image= image;
    }

    //constructor para el trucker
    public Trip(User trucker,
                User provider,
                String licensePlate,
                Number weight,
                Number volume,
                Timestamp departureDate,
                Timestamp arrivalDate,
                String origin,
                String destination,
                String type,
                Number price) {
        this.tripId = null;
        this.provider = provider;
        this.trucker = trucker;
        this.licensePlate = licensePlate;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
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
                Number weight,
                Number volume,
                Timestamp departureDate,
                Timestamp arrivalDate,
                String origin,
                String destination,
                String type,
                Number price) {
        this.tripId = null;
        this.provider = provider;
        this.trucker = trucker;
        this.licensePlate = null;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
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

    public void setTrucker(User trucker) {
        this.trucker = trucker;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Number getWeight() {
        return weight;
    }

    public void setWeight(Number weight) {
        this.weight = weight;
    }

    public Number getVolume() {
        return volume;
    }

    public void setVolume(Number volume) {
        this.volume = volume;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
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

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
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

    public Timestamp getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Timestamp confirmationDate) {
        this.confirmationDate = confirmationDate;
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
}
