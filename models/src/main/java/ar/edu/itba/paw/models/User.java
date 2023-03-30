package ar.edu.itba.paw.models;

public class User {

    private final String email;
    private final String name;
    private final String id; //Cuit / cuil

    public User(final String email, final String name, final String id){
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
