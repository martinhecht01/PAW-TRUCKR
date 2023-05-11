package ar.edu.itba.paw.models;

import java.security.Timestamp;
import java.time.LocalDateTime;

public class SecureToken {

    private final int userId;

    private final String token;
    private final LocalDateTime expireAt;



    public SecureToken(int userId, String hash, LocalDateTime expireAt) {
        this.userId = userId;
        this.token = hash;
        this.expireAt = expireAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired(){
        	return LocalDateTime.now().isAfter(expireAt);
    }
}
