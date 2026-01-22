package dormitory.controllers.interfaces;

import  dormitory.models.User;

public interface IUserController {
    String register(String name, String surname, String email, String password, String gender);
    User login (String email, String password);
}
