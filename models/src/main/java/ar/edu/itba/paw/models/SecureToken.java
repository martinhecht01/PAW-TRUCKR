package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "securetokens")
public class SecureToken {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "expiredate")
    private LocalDateTime expireAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    // Constructors, getters, and setters


    public SecureToken(User user, String token, LocalDateTime expireAt) {
        this.user = user;
        this.token = token;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired(){
        	return LocalDateTime.now().isAfter(expireAt);
    }

    public void setToken(String token) {
        this.token = token;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
