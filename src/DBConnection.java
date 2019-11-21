import com.mysql.jdbc.Driver;

import java.sql.*;

public class DBConnection {

    private Connection conn = null;
    // db parameters
    private String url = "jdbc:mysql://localhost:3306/test";
    private String user = "root";
    private String password = "Mohamed01";

    public boolean conect() throws SQLException {
        DriverManager.registerDriver(new Driver());
        conn = DriverManager.getConnection(url, user, password);

        return conn != null;
    }

    public boolean insert(String sql, String[] values) throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < values.length; i++) {
            pstmt.setString(i + 1, values[i]);
        }

        return  pstmt.executeUpdate() > 0;
    }

    public ResultSet select(String sql) throws SQLException {
        Statement stmt  = conn.createStatement();

        ResultSet rs    = stmt.executeQuery(sql);

        

        try{
            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return ;
    }

}
