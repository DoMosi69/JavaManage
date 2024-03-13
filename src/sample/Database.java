package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost/hotelmanagementdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            handleException(e);
        }
        return null;
    }

    public void closeResources(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        // Handle the exception appropriately, e.g., log it or display an error message
        e.printStackTrace();
    }


}
