package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DoAn;

public class DoAnDAO {
    private Connection connection;

    public DoAnDAO() {
        // Khởi tạo connection từ DatabaseManager
        this.connection = new DatabaseManager().getConnection();
    }

    // Thêm đồ án và trả về true nếu thành công, false nếu thất bại
    public boolean addDoAn(String name, String deadline, String instructor) {
        try {
            String query = "INSERT INTO project (NameProject, Deadline, Instructor) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, deadline);
                preparedStatement.setString(3, instructor);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được thêm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Sửa thông tin đồ án và trả về true nếu thành công, false nếu thất bại
    public boolean updateDoAn(int id, String name, String deadline, String instructor) {
        try {
            String query = "UPDATE project SET NameProject=?, Deadline=?, Instructor=? WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, deadline);
                preparedStatement.setString(3, instructor);
                preparedStatement.setInt(4, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa đồ án và trả về true nếu thành công, false nếu thất bại
    public boolean deleteDoAn(int id) {
        try {
            String query = "DELETE FROM project WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị xóa
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách toàn bộ đồ án
    public List<DoAn> getAllDoAn() {
        List<DoAn> doAnList = new ArrayList<>();
        try {
            String query = "SELECT * FROM project";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("NameProject");
                    String deadline = resultSet.getString("Deadline");
                    String instructor = resultSet.getString("Instructor");

                    DoAn doAn = new DoAn(id, name, deadline, instructor);
                    doAnList.add(doAn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doAnList;
    }
    
 // Hàm tìm kiếm đồ án theo từ khóa
    public List<DoAn> searchDoAn(String keyword) {
        List<DoAn> doAnList = new ArrayList<>();
        String query = "SELECT * FROM project WHERE NameProject LIKE ? OR Deadline LIKE ? OR Instructor LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Sử dụng tham số để truyền vào giá trị tìm kiếm với dạng %keyword%
            String likeKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, likeKeyword);
            preparedStatement.setString(2, likeKeyword);
            preparedStatement.setString(3, likeKeyword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("NameProject");
                    String deadline = resultSet.getString("Deadline");
                    String instructor = resultSet.getString("Instructor");

                    // Tạo đối tượng DoAn và thêm vào danh sách
                    DoAn doAn = new DoAn(id, name, deadline, instructor);
                    doAnList.add(doAn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doAnList;
    }


}
