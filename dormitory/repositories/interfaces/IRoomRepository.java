package dormitory.repositories.interfaces;

import dormitory.entities.Room;
import java.util.List;

public interface IRoomRepository {
    boolean addRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomById(int id);
}