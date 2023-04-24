package ar.edu.itba.paw.interfacesServices;

import ar.edu.itba.paw.models.User;

public interface UserService {

    User createUser(String email, String name, String id, String password);
    User getUserByCuit(String cuit);
    User getUserById(int id);

}
