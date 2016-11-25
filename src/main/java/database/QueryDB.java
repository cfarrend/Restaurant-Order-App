package database;

/* The purpose of this class is to allow direct queries into the database
 * Note: Needs to have a database to perform a query on otherwise will throw an exception
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class QueryDB {
    public static void main(String[] args) {

        String db = null;
        String url;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Ask for database name
        try {
            System.out.println("Database Name: ");
            db = br.readLine();
        }   catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Attempt connection to the database
        Connection conn = null;
        try {
            url = "jdbc:sqlite:src/main/resources/database/" + db + ".db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to Database " + db);
        }   catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        }

        System.out.println("In this current version of the application, all queries must be inputted manually (SQLite). " +
                "Type 'exit' to exit from program");

        while (true) {
            try {
                System.out.println("Enter in SQL statement");

                String sql = br.readLine();
                String command = getFirstWord(sql);

                Statement stmt = conn.createStatement();

                switch (command) {
                    case "alter":
                    case "delete":
                    case "update":
                    case "create":
                    case "drop":
                    case "insert":
                        stmt.execute(sql);
                        System.out.println("Query was successful");
                        break;
                    case "select":
                        ResultSet rs = stmt.executeQuery(sql);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                System.out.print(rs.getString(i) + " ");
                            }
                            System.out.println();
                        }
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Not a valid query");
                        break;
                }

            } catch (IOException e) {
                System.out.println("I/O error: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("SQL error: " + e.getMessage());
            }
        }

    }

    private static String getFirstWord(String text) {
        if (text.indexOf(' ') > -1) {
            return text.substring(0, text.indexOf(' ')).toLowerCase();
        } else {
            return text.toLowerCase();
        }
    }
}
