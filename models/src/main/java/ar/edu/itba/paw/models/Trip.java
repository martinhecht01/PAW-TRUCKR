package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_trip_id_seq")
    @SequenceGenerator(sequenceName="trips_trip_id_seq", name = "trips_trip_id_seq", allocationSize = 1)
    @Column(name = "trip_id")
    private Integer tripId;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider;

    @ManyToOne
    @JoinColumn(name = "trucker_id")
    private User trucker;

    @Column(name = "licenseplate", length = 30)
    private String licensePlate;

    @Column(name = "weight")
    private Number weight;

    @Column(name = "volume")
    private Number volume;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

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
    private LocalDateTime confirmationDate;

    @ManyToOne
    @JoinColumn(name = "imageid")
    private Image image;

    @Transient
    private int proposalCount;
    // Constructors, getters, and setters


    public Trip(int tripId, User provider, User trucker, String licensePlate, Number weight, Number volume, LocalDateTime departureDate, LocalDateTime arrivalDate, String origin, String destination, String type,
                Number price, Boolean truckerConfirmation, Boolean providerConfirmation, LocalDateTime confirmationDate, int proposalCount) {
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
    }

    public Trip(User provider, User trucker, String licensePlate, Number weight, Number volume, LocalDateTime departureDate, LocalDateTime arrivalDate, String origin, String destination, String type,
                Number price, Boolean truckerConfirmation, Boolean providerConfirmation, LocalDateTime confirmationDate, int proposalCount) {
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
        this.truckerConfirmation = truckerConfirmation;
        this.providerConfirmation = providerConfirmation;
        this.confirmationDate = confirmationDate;
        this.proposalCount = proposalCount;
    }

    public Trip() {
        // Default constructor required by Hibernate
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

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
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

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
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

}
