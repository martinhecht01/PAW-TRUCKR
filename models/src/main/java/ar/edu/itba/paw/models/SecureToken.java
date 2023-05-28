package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "securetokens")
public class SecureToken {

    @Id
    @Column(name = "token")
    private Integer token;

    @Column(name = "expiredate")
    private LocalDateTime expireAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    // Constructors, getters, and setters

    public SecureToken(){
        // Default constructor required by Hibernate
    }
    public SecureToken(User user, Integer token, LocalDateTime expireAt) {
        this.user = user;
        this.token = token;
        this.expireAt = expireAt;
    }

    public Integer getToken() {
        return token;
    }

    public boolean isExpired(){
        	return LocalDateTime.now().isAfter(expireAt);
    }

    public void setToken(Integer token) {
        this.token = token;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
