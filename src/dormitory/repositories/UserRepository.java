package dormitory.repositories;

import dormitory.data.interfaces.IDB;
import dormitory.models.User;
import dormitory.repositories.interfaces.IUserRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createUser(User user) {
        Connection con = db.getConnection();
        String sql = "INSERT INTO users(name, surname, email, password, gender, role) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setString(5, user.getGender());
            st.setString(6, user.getRole());
            return st.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        Connection con = db.getConnection();
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public User getUserById(int id) {
        Connection con = db.getConnection();
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public User login(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) return user;
        return null;
    }

    @Override
    public boolean assignRoomToUser(int userId, int roomId) {
        Connection con = db.getConnection();
        String sql = "UPDATE users SET room_id = ? WHERE id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, roomId);
            st.setInt(2, userId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean removeUserFromRoom(int userId) {
        Connection con = db.getConnection();
        String sql = "UPDATE users SET room_id = NULL WHERE id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, userId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<User> getUsersByRoomId(int roomId) {
        Connection con = db.getConnection();
        String sql = "SELECT * FROM users WHERE room_id = ?";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, roomId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return users;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User u = new User(rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getString("password"), rs.getString("gender"), rs.getString("role"));
        u.setId(rs.getInt("id"));
        u.setRoomId(rs.getInt("room_id"));
        return u;
    }
}