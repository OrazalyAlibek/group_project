package dormitory.repositories.interfaces;

public interface IUserRepository {
    boolean createUser (User user);
    User findByEmail (String email);
    User login (String email, String password);
}
