package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DoAn;
import dto.ThongKe;

public class ThongKeDAO {
    private Connection connection;

    public ThongKeDAO() {
        this.connection = new DatabaseManager().getConnection();
    }

    // Hàm thêm dữ liệu thống kê mới
    public boolean addThongKe(ThongKe thongKe) {
        String query = "INSERT INTO ThongKe (ID_Project, Instructor, PointGV, PointHD, PointHDC, TongDiem, PointTB) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, thongKe.getID_Project());
            preparedStatement.setString(2, thongKe.getInstructor());
            preparedStatement.setDouble(3, thongKe.getPointGV());
            preparedStatement.setDouble(4, thongKe.getPointHD());
            preparedStatement.setDouble(5, thongKe.getPointHDC());
            preparedStatement.setDouble(6, thongKe.getTongDiem());
            preparedStatement.setDouble(7, thongKe.getPointTB());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm cập nhật thông tin thống kê
    public boolean updateThongKe(ThongKe thongKe) {
        String query = "UPDATE ThongKe SET ID_Project=?, Instructor=?, PointGV=?, PointHD=?, " +
                       "PointHDC=?, TongDiem=?, PointTB=? WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, thongKe.getID_Project());
            preparedStatement.setString(2, thongKe.getInstructor());
            preparedStatement.setDouble(3, thongKe.getPointGV());
            preparedStatement.setDouble(4, thongKe.getPointHD());
            preparedStatement.setDouble(5, thongKe.getPointHDC());
            preparedStatement.setDouble(6, thongKe.getTongDiem());
            preparedStatement.setDouble(7, thongKe.getPointTB());
            preparedStatement.setInt(8, thongKe.getID());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm xóa thông tin thống kê
    public boolean deleteThongKe(int thongKeID) {
        String query = "DELETE FROM ThongKe WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, thongKeID);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm lấy danh sách thông kê từ cơ sở dữ liệu
    public List<ThongKe> getAllThongKe() {
        List<ThongKe> thongKeList = new ArrayList<>();
        String query = "SELECT * FROM ThongKe";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                int ID_Project = resultSet.getInt("ID_Project");
                String instructor = resultSet.getString("Instructor");
                float pointGV = resultSet.getFloat("PointGV");
                float pointHD = resultSet.getFloat("PointHD");
                float pointHDC = resultSet.getFloat("PointHDC");
                float tongDiem = resultSet.getFloat("TongDiem");
                float pointTB = resultSet.getFloat("PointTB");

                // Tạo đối tượng ThongKe và thêm vào danh sách
                ThongKe thongKe = new ThongKe(ID, ID_Project, instructor, pointGV, pointHD, pointHDC, tongDiem, pointTB);
                thongKeList.add(thongKe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return thongKeList;
    }
}
