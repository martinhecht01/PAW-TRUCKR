package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class Reset {
    private final int userId;

    private final String hash;

    private final LocalDateTime createDate;

    private final boolean completed;

    public Reset(int userId, String hash, LocalDateTime createDate, boolean completed) {
        this.userId = userId;
        this.hash = hash;
        this.createDate = createDate;
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public String getHash() {
        return hash;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public boolean isCompleted() {
        return completed;
    }
}
