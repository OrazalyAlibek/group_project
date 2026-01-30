package dormitory.controllers;

import dormitory.controllers.interfaces.IUserController;
import dormitory.models.User;
import dormitory.repositories.interfaces.IUserRepository;
import dormitory.utils.Validator;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String register(String name, String surname, String email, String password, String gender) {
        if (!Validator.isValidEmail(email)) return "Error: Invalid email format.";
        if (!Validator.isValidPassword(password)) return "Error: Password too short (min 6).";
        if (repo.getUserByEmail(email) != null) return "Error: Email already exists.";

        User user = new User(name, surname, email, password, gender, "USER");
        boolean created = repo.createUser(user);

        return created ? "User Registered Successfully!" : "DB Error.";
    }

    @Override
    public User login(String email, String password) {
        return repo.login(email, password);
    }
}