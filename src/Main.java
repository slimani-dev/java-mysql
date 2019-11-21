import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws SQLException {


        ArrayList<User> userArrayList = new ArrayList<>();

        //User mohamed = User.getUserById(1);
        //System.out.println(mohamed);

        /*User hiba = new User("hiba","hiba@utc-dz.com");
        hiba.save();
        System.out.println(hiba);*/

        /*User chimaa = User.getUserById(2);
        System.out.println(chimaa);

        chimaa.setName("chimaa");
        chimaa.save();

        chimaa = User.getUserById(2);
        System.out.println(chimaa);*/



        User user4 = User.getUserById(1);
        user4.delete();
        System.out.println(user4);

        userArrayList = User.getUsers();
        System.out.println(userArrayList);

        user4.save();
        System.out.println(user4);

        userArrayList = User.getUsers();
        System.out.println(userArrayList);

    }


}
