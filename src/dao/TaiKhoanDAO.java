package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.TaiKhoan;

public class TaiKhoanDAO {
    private Connection connection;

    public TaiKhoanDAO() {
        this.connection = new DatabaseManager().getConnection();
    }

    // Hàm kiểm tra đăng nhập
    public boolean checkLogin(String username, String password) {
        String query = "SELECT * FROM TaiKhoan WHERE username = ? AND password = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Trả về true nếu có kết quả, ngược lại là false
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
