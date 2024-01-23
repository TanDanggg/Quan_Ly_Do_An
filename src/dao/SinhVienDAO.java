package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.SinhVien;

public class SinhVienDAO {
    private Connection connection;

    public SinhVienDAO() {
        this.connection = new DatabaseManager().getConnection();
    }

 // Hàm lấy danh sách sinh viên từ cơ sở dữ liệu
    public List<SinhVien> getAllSinhVien() {
        List<SinhVien> sinhVienList = new ArrayList<>();
        String query = "SELECT * FROM SinhVien";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                Date dateOfBirth = resultSet.getDate("DateOfBirth");
                String sex = resultSet.getString("Sex");
                String address = resultSet.getString("Address");
                String email = resultSet.getString("Email");
                String className = resultSet.getString("Class");
                int projectID = resultSet.getInt("ID_Project");

                // Tạo đối tượng SinhVien và thêm vào danh sách
                SinhVien sinhVien = new SinhVien(ID, name, dateOfBirth, sex, address, email, className, projectID);
                sinhVienList.add(sinhVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sinhVienList;
    }


    // Hàm thêm sinh viên vào cơ sở dữ liệu
    public boolean addSinhVien(SinhVien sinhVien) {
        String query = "INSERT INTO SinhVien (Name, DateOfBirth, Sex, Address, Email, Class, ID_Project) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sinhVien.getName());
            preparedStatement.setDate(2, new java.sql.Date(sinhVien.getDateOfBirth().getTime()));
            preparedStatement.setString(3, sinhVien.getSex());
            preparedStatement.setString(4, sinhVien.getAddress());
            preparedStatement.setString(5, sinhVien.getEmail());
            preparedStatement.setString(6, sinhVien.getClassName());
            preparedStatement.setInt(7, sinhVien.getProjectID());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm cập nhật thông tin sinh viên trong cơ sở dữ liệu
    public boolean updateSinhVien(SinhVien sinhVien) {
        String query = "UPDATE SinhVien SET Name=?, DateOfBirth=?, Sex=?, Address=?, Email=?, Class=?, ID_Project=? " +
                "WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sinhVien.getName());
            preparedStatement.setDate(2, new java.sql.Date(sinhVien.getDateOfBirth().getTime()));
            preparedStatement.setString(3, sinhVien.getSex());
            preparedStatement.setString(4, sinhVien.getAddress());
            preparedStatement.setString(5, sinhVien.getEmail());
            preparedStatement.setString(6, sinhVien.getClassName());
            preparedStatement.setInt(7, sinhVien.getProjectID());
            preparedStatement.setInt(8, sinhVien.getID());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm xóa sinh viên khỏi cơ sở dữ liệu
    public boolean deleteSinhVien(int sinhVienID) {
        String query = "DELETE FROM SinhVien WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, sinhVienID);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Hàm tìm kiếm sinh viên theo tên
    public List<SinhVien> searchSinhVienByName(String nameKeyword) {
        List<SinhVien> sinhVienList = new ArrayList<>();
        String query = "SELECT * FROM SinhVien WHERE Name LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Sử dụng tham số để truyền vào giá trị tìm kiếm với dạng %nameKeyword%
            preparedStatement.setString(1, "%" + nameKeyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String name = resultSet.getString("Name");
                    Date dateOfBirth = resultSet.getDate("DateOfBirth");
                    String sex = resultSet.getString("Sex");
                    String address = resultSet.getString("Address");
                    String email = resultSet.getString("Email");
                    String className = resultSet.getString("Class");
                    int projectID = resultSet.getInt("ID_Project");

                    // Tạo đối tượng SinhVien và thêm vào danh sách
                    SinhVien sinhVien = new SinhVien(ID, name, dateOfBirth, sex, address, email, className, projectID);
                    sinhVienList.add(sinhVien);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sinhVienList;
    }

}
