package dormitory.controllers.interfaces;

public interface IRoomController {
    String createRoom (int number, int capacity, double price);
    String getAllRooms ();
    String bookRoom (int userId, int roomId);
    String getRoommates (int roomId);
    String getAvailableRoomByPrice (double maxPrice);
    String getRoomsWithCategoryDetails();
}
