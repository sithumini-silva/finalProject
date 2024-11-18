package lk.ijse.gdse71.dreamlandkids.util;

import lk.ijse.gdse71.dreamlandkids.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException {
        // Get connection from DBConnection
        Connection connection = DBConnection.getInstance().getConnection();

        // Prepare the SQL statement
        PreparedStatement pst = connection.prepareStatement(sql);

        // Set parameters for the prepared statement
        for (int i = 0; i < obj.length; i++) {
            pst.setObject(i + 1, obj[i]);
        }

        // Check if it's a SELECT query
        if (sql.trim().toLowerCase().startsWith("select")) {
            // Execute query and return ResultSet
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet; // This is fine for SELECT queries
        } else {
            // Execute update (INSERT, UPDATE, DELETE) and return boolean success
            int rowsAffected = pst.executeUpdate();
            boolean isSaved = rowsAffected > 0;
            return (T) Boolean.valueOf(isSaved); // Return boolean wrapped in Boolean
        }
    }
}
