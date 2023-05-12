/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author User
 */
public class JdbcHelper {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/test";
     private static final String DATABASE_USER_NAME = "root";
      private static final String DATABASE_PASSWORD = "";
      
      public static Connection getConnection() throws SQLException{
          return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
      }
       public static PreparedStatement preparedStatement(String sql, Object...args) throws SQLException{
          Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
          PreparedStatement pstmt = null;
          if (sql.trim().startsWith("(")){
          pstmt = connection.prepareCall(sql);
          } else {
              pstmt = connection.prepareStatement(sql);
          }
           for (int i = 0; i < args.length; i++) {
               pstmt.setObject(i+1, args[i]);
           }
          return pstmt;
      }     
      public static void executeUpdate(String sql, Object... args) throws SQLException {
        try {
            PreparedStatement stmt = preparedStatement(sql, args);
            try {
                stmt.executeUpdate();
            } finally {
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
      public static ResultSet executeQuery(String sql, Object... args) {
        PreparedStatement stmt;
        try {
            stmt = preparedStatement(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
