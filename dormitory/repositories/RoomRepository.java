package dormitory.repositories;

import dormitory.data.PostgresDB;
import dormitory.entities.Room;
import dormitory.repositories.interfaces.IRoomRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {

    @Override
    public boolean addRoom(Room room) {
        Connection con = PostgresDB.getInstance().getConnection();
        String sql = "INSERT INTO rooms(room_number, capacity, price_per_month) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, room.getRoomNumber());
            st.setInt(2, room.getCapacity());
            st.setDouble(3, room.getPrice());
            return st.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<Room> getAllRooms() {
        Connection con = PostgresDB.getInstance().getConnection();
        String sql = "SELECT * FROM rooms";
        List<Room> rooms = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Room r = new Room(rs.getInt("room_number"), rs.getInt("capacity"), rs.getDouble("price_per_month"));
                r.setId(rs.getInt("id"));
                rooms.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return rooms;
    }

    @Override
    public Room getRoomById(int id) {
        Connection con = PostgresDB.getInstance().getConnection();
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Room r = new Room(rs.getInt("room_number"), rs.getInt("capacity"), rs.getDouble("price_per_month"));
                r.setId(rs.getInt("id"));
                return r;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}