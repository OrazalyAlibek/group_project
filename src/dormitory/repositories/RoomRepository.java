package dormitory.repositories;

import dormitory.data.interfaces.IDB;
import dormitory.models.Room;
import dormitory.repositories.interfaces.IRoomRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean addRoom(Room room) {
        Connection con = db.getConnection();
        String sql = "INSERT INTO rooms(room_number, capacity, price_per_month, category_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, room.getRoomNumber());
            st.setInt(2, room.getCapacity());
            st.setDouble(3, room.getPrice());
            st.setInt(4, room.getCategoryId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<Room> getAllRooms() {
        Connection con = db.getConnection();
        String sql = "SELECT * FROM rooms";
        List<Room> rooms = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Room r = new Room(rs.getInt("room_number"), rs.getInt("capacity"), rs.getDouble("price_per_month"), rs.getInt("category_id"));
                r.setId(rs.getInt("id"));
                rooms.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return rooms;
    }

    @Override
    public Room getRoomById(int id) {
        Connection con = db.getConnection();
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Room r = new Room(rs.getInt("room_number"), rs.getInt("capacity"), rs.getDouble("price_per_month"), rs.getInt("category_id"));
                r.setId(rs.getInt("id"));
                return r;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<String> getRoomsWithCategoryDetails() {
        Connection con = db.getConnection();
        String sql = "SELECT r.room_number, r.price_per_month, c.name FROM rooms r LEFT JOIN categories c ON r.category_id = c.id";
        List<String> result = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                result.add("Room " + rs.getInt("room_number") + " [" + rs.getString("name") + "] Price: " + rs.getDouble("price_per_month"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return result;
    }
}