package dormitory;

import dormitory.controllers.RoomController;
import dormitory.controllers.UserController;
import dormitory.controllers.interfaces.IRoomController;
import dormitory.controllers.interfaces.IUserController;
import dormitory.data.PostgresDB;
import dormitory.data.interfaces.IDB;
import dormitory.repositories.RoomRepository;
import dormitory.repositories.UserRepository;
import dormitory.repositories.interfaces.IRoomRepository;
import dormitory.repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {

        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "dorm_db");

        IUserRepository userRepo = new UserRepository(db);
        IRoomRepository roomRepo = new RoomRepository(db);

        IUserController userController = new UserController(userRepo);
        IRoomController roomController = new RoomController(roomRepo, userRepo);

        MyApplication app = new MyApplication(UserController, RoomController);

        app.start();

        db.close();
    }
}