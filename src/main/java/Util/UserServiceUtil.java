package Util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

    public static  Boolean loggedIn = false;
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/ticket";
    public static  final String USER = "root";
    public static  final String PASSWORD = "";

    public static String hashedPassword(String password){

        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public static boolean checkPassword(String password,String hashedPassword){

        return BCrypt.checkpw(password,hashedPassword);
    }
}
