package ar.edu.itba.paw.models;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
    @Column(name = "userid")
    private Integer userId;

    @Column(name = "cuit", unique = true, length = 15)
    private String cuit;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "name", length = 80)
    private String name;

    @Column(name = "role", length = 15)
    private String role;

    @Column(name = "password", length = 80)
    private String password;

    @Column(name = "accountverified")
    private Boolean accountVerified;

    @ManyToOne
    @JoinColumn(name = "imageid")
    private Image image;

    @OneToMany(mappedBy = "user")
    private List<Proposal> proposals;

    @OneToMany(mappedBy = "trucker", fetch = FetchType.EAGER)
    private List<Trip> truckerTrips;

    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private List<Trip> providerTrips;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Review> reviews;

    @Nullable
    @OneToOne
    private Alert alert;

    // Constructors, getters, and setters

    public User() {
        // Default constructor required by Hibernate
    }

    public User(Integer userId,
                String email,
                String name,
                String cuit,
                String role,
                String password,
                Boolean accountVerified,
                Image image) {
        this.userId = userId;
        this.cuit = cuit;
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.accountVerified = accountVerified;
        this.image = image;
    }

    public User(String email,
                String name,
                String cuit,
                String role,
                String password,
                Boolean accountVerified,
                Image image) {
        this.userId = null;
        this.cuit = cuit;
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.accountVerified = accountVerified;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

// Getters and setters


    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(Boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<Trip> getTruckerTrips() {
        return truckerTrips;
    }

    public void setTruckerTrips(List<Trip> truckerTrips) {
        this.truckerTrips = truckerTrips;
    }

    public List<Trip> getProviderTrips() {
        return providerTrips;
    }

    public void setProviderTrips(List<Trip> providerTrips) {
        this.providerTrips = providerTrips;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getRating(){
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream().mapToDouble(Review::getRating).sum() / reviews.size();
    }
}
