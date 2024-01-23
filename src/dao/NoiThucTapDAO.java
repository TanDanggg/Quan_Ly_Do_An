package dao;

import dto.NoiThucTap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoiThucTapDAO {
    private Connection connection;

    public NoiThucTapDAO() {
    	this.connection = new DatabaseManager().getConnection();
    }

    // Hàm lấy danh sách nơi thực tập từ cơ sở dữ liệu
    public List<NoiThucTap> getAllNoiThucTap() {
        List<NoiThucTap> noiThucTapList = new ArrayList<>();
        String query = "SELECT * FROM NoiThucTap";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                int projectID = resultSet.getInt("ID_Project");
                String internshipAddress = resultSet.getString("InternshipAddress");
                String timeLine = resultSet.getString("TimeLine");
                String instructor = resultSet.getString("Instructor");

                // Tạo đối tượng NoiThucTap và thêm vào danh sách
                NoiThucTap noiThucTap = new NoiThucTap(ID, projectID, internshipAddress, timeLine, instructor);
                noiThucTapList.add(noiThucTap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return noiThucTapList;
    }

    // Hàm thêm nơi thực tập vào cơ sở dữ liệu
    public boolean addNoiThucTap(NoiThucTap noiThucTap) {
        String query = "INSERT INTO NoiThucTap (ID_Project, InternshipAddress, TimeLine, Instructor) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, noiThucTap.getID_Project());
            preparedStatement.setString(2, noiThucTap.getInternshipAddress());
            preparedStatement.setString(3, noiThucTap.getTimeLine());
            preparedStatement.setString(4, noiThucTap.getInstructor());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm cập nhật thông tin nơi thực tập trong cơ sở dữ liệu
    public boolean updateNoiThucTap(NoiThucTap noiThucTap) {
        String query = "UPDATE NoiThucTap SET ID_Project=?, InternshipAddress=?, TimeLine=?, Instructor=? " +
                "WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, noiThucTap.getID_Project());
            preparedStatement.setString(2, noiThucTap.getInternshipAddress());
            preparedStatement.setString(3, noiThucTap.getTimeLine());
            preparedStatement.setString(4, noiThucTap.getInstructor());
            preparedStatement.setInt(5, noiThucTap.getID());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm xóa nơi thực tập khỏi cơ sở dữ liệu
    public boolean deleteNoiThucTap(int noiThucTapID) {
        String query = "DELETE FROM NoiThucTap WHERE ID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, noiThucTapID);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
