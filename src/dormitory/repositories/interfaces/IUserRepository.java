package dormitory.repositories.interfaces;

import dormitory.models.User;
import java.util.List;

public interface IUserRepository {
    boolean createUser(User user);
    User getUserByEmail(String email);
    User getUserById(int id);
    User login(String email, String password);
    boolean assignRoomToUser(int userId, int roomId);
    boolean removeUserFromRoom(int userId);
    List<User> getUsersByRoomId(int roomId);
}
