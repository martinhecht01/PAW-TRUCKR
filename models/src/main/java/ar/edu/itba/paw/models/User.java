package ar.edu.itba.paw.models;

public class User {

    private final String email;
    private final String name;
    private final String cuit; //Cuit / cuil
    private final String role;
    private final String password;
    private final int userId;


    public User(final int userId, final String email, final String name, final String cuit, final String role, String password){
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.cuit = cuit;
        this.role = role;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCuit() {
        return cuit;
    }

    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {return role;}
}
