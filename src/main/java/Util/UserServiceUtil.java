package Util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

    public static  Boolean loggedIn = false;

    public static String hashedPassword(String password){

        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public static boolean checkPassword(String password,String hashedPassword){

        return BCrypt.checkpw(password,hashedPassword);
    }
}
