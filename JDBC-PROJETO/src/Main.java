import db.DB;
import db.DbException;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        // Example usage of DB class
        Connection conn = null;
        try {
            conn = DB.getConnection();
            System.out.println("foi");
            // Perform database operations here
        } catch (DbException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            DB.closeConnection();
        }
    }
}