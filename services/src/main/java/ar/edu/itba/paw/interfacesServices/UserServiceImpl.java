package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    String email;

    @Override
    public User createUser(String email, String password){
        this.email = email;
        return new User(email, password);
    }

    @Override
    public String toString(){
        return this.email;
    }
}
