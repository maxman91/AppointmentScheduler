package databases;
import javafx.collections.ObservableList;
import model.user;
import utilities.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static model.user.addUser;
import static model.user.getAllUsers;

/**The usersQuery selects all users from the database.*/
public abstract class usersQuery {

    /**The selectAllUsers method creates a user object of every row of data from the users table in the database.*/
    public static void selectAllUsers() throws SQLException {

        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userPassword = rs.getString("Password");
            String userName = rs.getString("User_Name");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            addUser(new user(userId, userName, userPassword));

        }
    }


}
