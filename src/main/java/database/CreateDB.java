package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* This class creates the database file */

public class CreateDB {

    public static void main(String[] args) {
        connect();
    }

    private static void connect() {
        Connection conn = null;
        try {
            // Note: URL starts at Restaurant-Order-App
            String url = "jdbc:sqlite:src/main/resources/database/database.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connected");
        }   catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }   catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
