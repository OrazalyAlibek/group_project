package dormitory;

import dormitory.controllers.RoomController;
import dormitory.controllers.UserController;
import dormitory.controllers.interfaces.IRoomController;
import dormitory.controllers.interfaces.IUserController;
import dormitory.data.PostgresDB;
import dormitory.data.interfaces.IDB;
import dormitory.factories.RepositoryFactory;
import dormitory.repositories.RoomRepository;
import dormitory.repositories.UserRepository;
import dormitory.repositories.interfaces.IRoomRepository;
import dormitory.repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args){
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        IDB db = PostgresDB.getInstance(url, username, password, dbName);

        IUserRepository userRepo = RepositoryFactory.createUserRepository(db);
        IRoomRepository roomRepo = RepositoryFactory.createRoomRepository(db);

        IUserController userController = new UserController(userRepo);
        IRoomController roomController = new RoomController(roomRepo, userRepo);

        MyApplication app = new MyApplication(userController, roomController);
        app.start();

        db.close();
    }
}

