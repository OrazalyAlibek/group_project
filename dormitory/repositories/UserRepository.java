package dormitory.repositories;

import dormitory.data.PostgresDB;
import dormitory.data.interfaces.IDB;
import dormitory.repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRepository implements IUserRepository {

    @Override
    public boolean createUser (User user) {
        Connection con = PostgresDB.getInstance().getConnection();
        String sql = "INSERT INTO users (name, surname, email, password, gender) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setString(5, user.getGender());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public User getUserByEmail (String email) {
        Connection con = PostgresDB.getInstance().getConnection();
        String sql = "SELECT * FROM users WHERE email = ?";
        try {

        }
    }
}
