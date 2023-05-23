package ar.edu.itba.paw.models;

import javax.persistence.*;

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

    // Constructors, getters, and setters

    public User() {
        // Default constructor required by Hibernate
    }

    // Getters and setters

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
}
