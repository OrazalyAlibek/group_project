package dormitory.controllers.interfaces;

public interface IRoomController {
    String createRoom (int number, int capacity, double price, int categoryId);
    String getAllRooms ();
    String bookRoom (int userId, int roomId);
    String getRoommates (int roomId);
    String getAvailableRoomsByPrice (double maxPrice);
    String getRoomsWithCategoryDetails();
}
