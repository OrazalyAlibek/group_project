package dormitory.controllers;

import dormitory.controllers.interfaces.IRoomController;
import dormitory.models.Room;
import dormitory.models.User;
import dormitory.repositories.interfaces.IRoomRepository;
import dormitory.repositories.interfaces.IUserRepository;
import java.util.List;


public class RoomController implements IRoomController {
    private final IRoomRepository roomRepo;
    private final IUserRepository userRepo;

    public RoomController(IRoomRepository roomRepo, IUserRepository userRepo) {
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }

    @Override
    public String createRoom(int number, int capacity, double price) {
        Room room = new Room(number, capacity, price);
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
    public String bookRoom(int userId, int roomId) {
        Room room = roomRepo.getRoomById(roomId);
        if (room == null) return "Error: Room not found.";

        User user = userRepo.getUserById(userId);
        if (user == null) return "Error: User not found.";

        List<User> occupants = userRepo.getUsersByRoomId(roomId);
        if (occupants.size() >= room.getCapacity()) {
            return "Booking Failed: Room is FULL! (Capacity: " + room.getCapacity() + ")";
        }

        boolean success = userRepo.assignRoomToUser(userId, roomId);
        return success ? "Success! Booked Room " + room.getRoomNumber() : "DB Error.";
    }

    @Override
    public String getRoommates(int roomId) {
        List<User> users = userRepo.getUsersByRoomId(roomId);
        if (users.isEmpty()) return "Room is empty.";

        StringBuilder sb = new StringBuilder("Occupants in Room ID " + roomId + ":\n");
        for (User u : users) {
            sb.append("- ").append(u.getName()).append(" ").append(u.getSurname()).append("\n");
        }
        return sb.toString();
    }
}
