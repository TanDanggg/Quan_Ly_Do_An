package dao;

import dto.GiangVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiangVienDAO {
    private Connection connection;

    public GiangVienDAO() {
        // Khởi tạo kết nối đến CSDL ở đây
        // (Cần implement phương thức kết nối CSDL tùy theo loại CSDL bạn sử dụng)
    	this.connection = new DatabaseManager().getConnection();
    }

    // Hàm thêm giảng viên vào CSDL
    public boolean addGiangVien(GiangVien giangVien) {
        try {
            String query = "INSERT INTO GiangVien (NameGV, Level, Position, SDT, ID_Project) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, giangVien.getNameGV());
                preparedStatement.setString(2, giangVien.getLevel());
                preparedStatement.setString(3, giangVien.getPosition());
                preparedStatement.setInt(4, giangVien.getSdt());
                preparedStatement.setInt(5, giangVien.getIdProject());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm sửa thông tin giảng viên trong CSDL
    public boolean updateGiangVien(GiangVien giangVien) {
        try {
            String query = "UPDATE GiangVien SET NameGV = ?, Level = ?, Position = ?, SDT = ?, ID_Project = ? WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, giangVien.getNameGV());
                preparedStatement.setString(2, giangVien.getLevel());
                preparedStatement.setString(3, giangVien.getPosition());
                preparedStatement.setInt(4, giangVien.getSdt());
                preparedStatement.setInt(5, giangVien.getIdProject());
                preparedStatement.setInt(6, giangVien.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm xóa giảng viên khỏi CSDL
    public boolean deleteGiangVien(int id) {
        try {
            String query = "DELETE FROM GiangVien WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm lấy danh sách giảng viên từ CSDL
    public List<GiangVien> getAllGiangVien() {
        List<GiangVien> giangVienList = new ArrayList<>();
        try {
            String query = "SELECT * FROM GiangVien";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    GiangVien giangVien = new GiangVien(
                            resultSet.getInt("ID"),
                            resultSet.getString("NameGV"),
                            resultSet.getString("Level"),
                            resultSet.getString("Position"),
                            resultSet.getInt("SDT"),
                            resultSet.getInt("ID_Project")
                    );
                    giangVienList.add(giangVien);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giangVienList;
    }
}
