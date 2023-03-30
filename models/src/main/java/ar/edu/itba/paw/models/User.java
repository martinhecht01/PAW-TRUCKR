package ar.edu.itba.paw.models;

public class User {

    private final String email;

    private String password;

    public User(final String email, final String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password){
         this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
