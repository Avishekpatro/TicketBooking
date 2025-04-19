package org.selenium;

import Services.UserServices;
import Util.UserServiceUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    
    public static void main(String[] args) throws IOException, SQLException {

        System.out.println("Running Train booking System");

        Scanner sc = new Scanner(System.in);
        UserServices userService;
        int option =0,train_id = 0;
        userService = new UserServices();
        Train trainSelectedForBooking = new Train();

        while (option != 7){

            System.out.println("Choose Option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch bookings");
            System.out.println("4. Search Train");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");

            option = sc.nextInt();

            switch (option){

                case 1:
                System.out.println("Enter the username to signup");
                String nameToSignUp = sc.next();
                System.out.println("Enter the password to signup");
                String passwordToSignUp = sc.next();
                Users userToSignup = new Users(nameToSignUp, passwordToSignUp,
                        UserServiceUtil.hashedPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                userService.signup(userToSignup);

                break;

                case 2:
                    if (!UserServiceUtil.loggedIn) {
                        System.out.println("Enter the username to Login");
                        String nameToLogin = sc.next();
                        System.out.println("Enter the password to login");
                        String passwordToLogin = sc.next();
                        Users userLogin = new Users(nameToLogin, passwordToLogin,
                                UserServiceUtil.hashedPassword(passwordToLogin), new ArrayList<>(), UUID.randomUUID().toString());
                        userService = new UserServices(userLogin);
                        userService.loginUser(userLogin);
                        if (userLogin.getPassword() != null) {
                            if (UserServiceUtil.checkPassword(userLogin.getPassword(), userLogin.getHashPassword())) {
                                System.out.println("Login Successfull");
                                UserServiceUtil.loggedIn = true;
                            } else
                                System.out.println("Enter Correct Password");
                        } else
                            System.out.println("Username Not present. SignUP");
                    }
                    else
                        System.out.println("Already Logged IN");
                    break;

                case 3:
                    System.out.println("Fetch Booking");
                   List<String> bookings = userService.fetchBooking(trainSelectedForBooking);
                   for (String t : bookings)
                       System.out.println(t);
                    break;

                case 4:
                    System.out.println("Search Train");
                    System.out.println("Enter the Source station");
                    String source = sc.next();
                    trainSelectedForBooking.setSourceStation(source);
                    System.out.println("Enter the destination Station");
                    String destination = sc.next();
                    trainSelectedForBooking.setDestStation(destination);
                    HashMap<Integer,String> trains = userService.getTrains(source,destination);
                    for (Map.Entry<Integer, String> entry : trains.entrySet()){
                        System.out.println("TrainId: " + entry.getKey() + ", Train_name: " + entry.getValue());
                        }
                    System.out.println("Select a train by typing ID");
                    train_id = sc.nextInt();
                    if (trains.containsKey(train_id))
                        trainSelectedForBooking.setName(trains.get(train_id));
                    break;

                case 5:
                    System.out.println("Selected Train id is "+ train_id);
                    trainSelectedForBooking.setTrainId(String.valueOf(train_id));
                    Boolean booked = userService.bookTrainSeat(trainSelectedForBooking);
                    if(booked.equals(Boolean.TRUE)){
                        System.out.println("Booked! Enjoy your journey");
                    }else{
                        System.out.println("Can't book this seat");
                    }
                    break;
                default:
                    break;

            }
        }
    }
}