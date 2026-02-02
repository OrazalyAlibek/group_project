package dormitory.controllers;

import dormitory.controllers.interfaces.IRoomController;
import dormitory.models.Room;
import dormitory.models.User;
import dormitory.repositories.interfaces.IRoomRepository;
import dormitory.repositories.interfaces.IUserRepository;
import dormitory.utils.Validator;
import java.util.List;
import java.util.stream.Collectors;

public class RoomController implements IRoomController {
    private final IRoomRepository roomRepo;
    private final IUserRepository userRepo;

    public RoomController(IRoomRepository roomRepo, IUserRepository userRepo) {
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }

    @Override
    public String createRoom(int number, int capacity, double price, int categoryId) {
        if (!Validator.isPositive(capacity) || !Validator.isPositive(price)) {
            return "Error: Capacity and Price must be positive.";
        }
        Room room = new Room(number, capacity, price, categoryId);
        boolean created = roomRepo.addRoom(room);
        return created ? "Room Added!" : "Failed to add room.";
    }

    @Override
    public String getAllRooms() {
        List<Room> rooms = roomRepo.getAllRooms();
        if (rooms.isEmpty()) return "No rooms found.";
        StringBuilder sb = new StringBuilder();
        for (Room r : rooms) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String filterRoomsByCategory(String categoryName) {
        List<String> rooms = roomRepo.getRoomsByCategory(categoryName);
        if (rooms.isEmpty()) return "No rooms found for category: " + categoryName;
        return String.join("\n", rooms);
    }

    @Override
    public String bookRoom(int userId, int roomId) {
        User user = userRepo.getUserById(userId);
        if (user == null) return "Error: User not found.";

        if (user.getRoomId() > 0) {
            return "Booking Failed: You are already assigned to Room " + user.getRoomId() +
                    ". Please ask an Admin to remove you before switching.";
        }

        Room room = roomRepo.getRoomById(roomId);
        if (room == null) return "Error: Room not found.";

        List<User> occupants = userRepo.getUsersByRoomId(roomId);
        if (occupants.size() >= room.getCapacity()) return "Booking Failed: Room is FULL.";


        return userRepo.assignRoomToUser(userId, roomId) ? "Success! Booked Room " + room.getRoomNumber() : "DB Error.";
    }

    @Override
    public String evictUser(int userId) {
        User user = userRepo.getUserById(userId);
        if (user == null) return "User not found.";
        if (user.getRoomId() == 0) return "User is not in any room.";

        boolean success = userRepo.removeUserFromRoom(userId);
        return success ? "User " + user.getName() + " removed from room. They can now book a new one." : "DB Error.";
    }

    @Override
    public String getRoommates(int roomId) {
        List<User> users = userRepo.getUsersByRoomId(roomId);
        if (users.isEmpty()) return "Room is empty.";
        StringBuilder sb = new StringBuilder("Occupants:\n");
        for (User u : users) {
            sb.append("- ").append(u.getName()).append(" ").append(u.getSurname()).append("\n");
        }
        return sb.toString();
    }
    @Override
    public String getAvailableRoomsByPrice(double maxPrice) {
        List<Room> allRooms = roomRepo.getAllRooms();
        List<Room> filtered = allRooms.stream().filter(r -> r.getPrice() <= maxPrice).collect(Collectors.toList());
        if (filtered.isEmpty()) return "No rooms under " + maxPrice;
        StringBuilder sb = new StringBuilder();
        filtered.forEach(r -> sb.append(r.toString()).append("\n"));
        return sb.toString();
    }

    @Override
    public String getRoomsWithCategoryDetails() {
        List<String> details = roomRepo.getRoomsWithCategoryDetails();
        if (details.isEmpty()) return "No data.";
        return String.join("\n", details);
    }
}