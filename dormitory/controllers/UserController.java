package dormitory.controllers;

import dormitory.controllers.interfaces.IUserController;
import dormitory.models.User;
import dormitory.repositories.interfaces.IUserRepository;


public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String register(String name, String surname, String email, String password, String gender) {
        if (password.length() < 6) return "Error: Password must be at least 6 chars.";
        if (repo.getUserByEmail(email) != null) return "Error: Email already exists.";

        User user = new User(name, surname, email, password, gender);
        boolean created = repo.createUser(user);

        return created ? "User Registered Successfully!" : "DB Error during registration.";
    }

    @Override
    public User login(String email, String password) {
        return repo.login(email, password);
    }
}