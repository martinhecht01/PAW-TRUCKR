package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfacesServices.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    String email;

    @Override
    public User createUser(String email, String name, String id){
        this.email = email;
        return new User(email, name, id);
    }

    @Override
    public String toString(){
        return this.email;
    }
}
