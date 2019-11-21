import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    private static final String TABLE_NAME = "users";
    private static final String[] COLUMNS = new String[]{"id", "name", "email"};
    private static final String[] SAVE_COLUMNS = new String[]{"name", "email"};
    private static DBConnection connection;

    private int id;
    private String name;
    private String email;
    private boolean deleted = false;

    User(String name, String email) {
        this.id = -1;
        this.name = name;
        this.email = email;
    }

    private User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    private static boolean connect() throws SQLException {
        connection = new DBConnection();
        return connection.conect();
    }

    public boolean save() throws SQLException {
        if (this.id != -1) {
            return this.update();
        }


        connect();
        String[] values = new String[]{this.name, this.email};
        int id = connection.insert(TABLE_NAME, SAVE_COLUMNS, values);
        if (id != -1) {
            this.id = id;
            this.deleted = false;
            return true;
        }
        return false;
    }

    public boolean update() throws SQLException {
        connect();
        String[] values = new String[]{this.name, this.email};
        int id = connection.update(TABLE_NAME, SAVE_COLUMNS, values, this.id);
        if (id != -1) {
            this.id = id;
            return true;
        }
        return false;
    }

    public boolean delete() {
        try {
            connect();
            try {
                connection.delete(TABLE_NAME, this.id);
                this.deleted = true;
            } catch (SQLException ignored) {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.deleted;
    }

    static User getUserById(int id) throws SQLException {
        connect();

        ArrayList<String[]> userList = connection.select(TABLE_NAME, COLUMNS, "id", "=", "" + id);
        String[] userInfo = userList.get(0);

        return new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2]);
    }

    static ArrayList<User> getUsers() throws SQLException {

        connect();

        ArrayList<String[]> userList = connection.select(TABLE_NAME, COLUMNS, "", "", "");

        ArrayList<User> users = new ArrayList<>();

        for (String[] userInfo : userList) {
            User user = new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2]);
            users.add(user);
        }

        return users;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String lastLine = this.deleted ? " (deleted!) \n" : "\n";
        return "id : " + this.id + " " +
                "name : " + this.name + " " +
                "email : " + this.email + lastLine;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        User.closeConnection();
    }

    private static void closeConnection() throws SQLException {
        connection.disconnect();
    }
}
