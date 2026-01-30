package dormitory.factories;

import dormitory.data.interfaces.IDB;
import dormitory.repositories.RoomRepository;
import dormitory.repositories.UserRepository;
import dormitory.repositories.interfaces.IRoomRepository;
import dormitory.repositories.interfaces.IUserRepository;

public class RepositoryFactory {
    public static IUserRepository createUserRepository(IDB db) {
        return new UserRepository(db);
    }

    public static IRoomRepository createRoomRepository(IDB db) {
        return new RoomRepository(db);
    }
}