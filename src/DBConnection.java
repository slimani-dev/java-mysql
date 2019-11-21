import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;

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

    public boolean disconnect() throws SQLException {
        if (conn != null)
            conn.close();

        return conn != null;
    }

    public boolean connected(){
        return this.conn != null;
    }

    public ArrayList<String[]> select(String tableName,String[] columns,String whereColumn,String whereOperator,String where) throws SQLException {

        Statement stmt  = conn.createStatement();

        String sql = "SELECT ";

        for (int i = 0; i < columns.length; i++) {
            sql += columns[i];
            if (columns.length > i+1){
                sql += ",";
            }
        }

        sql += " FROM " + tableName;

        if(!where.isEmpty()){
            sql += " where " + whereColumn + " " + whereOperator + " \'"  + where + "\'";
        }

        System.out.println(sql);

        ResultSet rs = stmt.executeQuery(sql);

        ArrayList<String[]> result = new ArrayList<>();

        while (rs.next()) {
            String[] resultRow = new String[columns.length];

            for (int i = 0; i < columns.length; i++) {
                resultRow[i] = rs.getString(columns[i]);
            }

            result.add(resultRow);
        }

        

        try{
            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public int insert(String tableName,String[] columns, String[] values) throws SQLException {

        String sql = "insert into " + tableName + "(";

        for (int i = 0; i < columns.length; i++) {
            sql += columns[i];
            if (columns.length > i+1){
                sql += ",";
            }
        }

        sql+= ") values(";

        for (int i = 0; i < values.length; i++) {
            sql += " ? ";
            if (values.length > i+1){
                sql += ",";
            }
        }

        sql+= ")";

        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < values.length; i++) {
            pstmt.setString(i + 1, values[i]);
        }

        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if(rs.next())
        {
            return rs.getInt(1);
        }

        return -1;
    }

    public int update(String tableName, String[] columns, String[] values, int id) throws SQLException {
        String sql = "update " + tableName + " set ";

        for (int i = 0; i < columns.length; i++) {
            sql += columns[i] + " = ? ";
            if (columns.length > i+1){
                sql += ",";
            }
        }

        sql+= " where id = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < values.length; i++) {
            pstmt.setString(i + 1, values[i]);
        }

        pstmt.setInt(values.length + 1, id);

        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if(rs.next())
        {
            return rs.getInt(1);
        }

        return -1;
    }

    public void delete(String tableName, int id) throws SQLException {
        String sql = "delete from " + tableName ;

        sql+= " where id = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, id);

        pstmt.executeUpdate();
    }
}
