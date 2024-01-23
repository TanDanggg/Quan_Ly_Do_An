package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ql_do_an_thuc_tap";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    // Mở kết nối đến cơ sở dữ liệu
    public DatabaseManager() {
        openConnection();
    }

    private void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Đóng kết nối đến cơ sở dữ liệu
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy đối tượng Connection để sử dụng trong các truy vấn
    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        // Sử dụng lớp DatabaseManager để mở và đóng kết nối
        DatabaseManager dbManager = new DatabaseManager();

        // Thực hiện các thao tác với cơ sở dữ liệu ở đây (truy vấn, cập nhật, ...)

        // Đóng kết nối sau khi hoàn thành
        dbManager.closeConnection();
    }
}
