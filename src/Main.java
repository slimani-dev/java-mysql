import com.mysql.jdbc.Driver;

import java.sql.*;

public class Main {


    public static void main(String[] args) {


        try {


            Statement stmt  = conn.createStatement();



            String sql = "SELECT * FROM test.users";

            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("id") + "\t" +
                        rs.getString("name")  + "\t" +
                        rs.getString("email"));
            }

            try{
                rs.close();
                stmt.close();
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }


}
