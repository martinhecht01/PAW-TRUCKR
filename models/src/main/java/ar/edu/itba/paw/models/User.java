package ar.edu.itba.paw.models;

public class User {

    private final String email;
    private final String name;
    private final String userId; //Cuit / cuil

    public User(final String email, final String name, final String userId){
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
}
