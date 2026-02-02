package dormitory.repositories.interfaces;

import dormitory.models.Room;
import java.util.List;

public interface IRoomRepository {
    boolean addRoom(Room room);
    List<Room> getAllRooms();
    List<String> getRoomsByCategory(String categoryName);
    Room getRoomById(int id);
    List<String> getRoomsWithCategoryDetails();
}