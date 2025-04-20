package LocalDB;

import Util.UserServiceUtil;
import org.selenium.Train;
import org.selenium.Users;

import java.sql.*;
import java.util.*;

public class Database {

    Connection connection;
    Statement statement;
    ResultSet resultSet;


   // Users users = new Users();

    public  void CreateConnection() {

        try {
            connection = DriverManager.getConnection(UserServiceUtil.URL,
                    UserServiceUtil.USER,UserServiceUtil.PASSWORD); // password is null Add your PWD
             statement = connection.createStatement();
            System.out.println("Connection extablished Successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean selectData(Users users) throws SQLException {

       //  resultSet = statement.executeQuery("SELECT name,hashedPassword FROM login");
        String query = "Select hashedPassword from login where name = '" + users.getName() + "'";
         resultSet = statement.executeQuery(query);
         if (resultSet.next()) {
             users.setHashPassword(resultSet.getString("hashedPassword"));
             return true;
         }else
             users.setPassword(null);
        return false;
    }

    public void UpdateLoginTable(Users users) throws SQLException {

        String query = "INSERT INTO login(userid, name, hashedPassword) VALUES(?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, users.getUserId());
        ps.setString(2,users.getName());
        ps.setString(3,users.getHashPassword());

        ps.addBatch();
        ps.executeBatch();

        System.out.println("Update executed Successfully!");
    }


    public HashMap<Integer,String> searchStations(String Source, String Destination){

       // ArrayList<String> trainList = new ArrayList<>();

        HashMap<Integer,String> trainlist = new HashMap<>();

        String query = "SELECT DISTINCT t.train_id, t.train_name " +
                "FROM Trains t " +
                "JOIN train_stations s1 ON t.train_id = s1.train_id " +
                "JOIN train_stations s2 ON t.train_id = s2.train_id " +
                "WHERE s1.station_name = ? " +
                "AND s2.station_name = ? " +
                "AND s1.stop_order < s2.stop_order";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,Source);
            ps.setString(2,Destination);

             resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int trainId = resultSet.getInt("train_id");
                String trainName = resultSet.getString("train_name");

                trainlist.put(trainId,trainName);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return trainlist;
    }


    public boolean bookTickets(Train train){

        String query = "UPDATE Trains " +
                "SET total_seats = total_seats - 1 " +
                "WHERE train_id = ? AND total_seats > 0";

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(train.getTrainId()));

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0){
               return true;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean bookTicket(Users users, Train train) {

        String query = "INSERT INTO bookings (user_name, train_id, source_station, destination_station, seat_number,train_name) " +
                "VALUES (?, ?, ?, ?, ?,?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, users.getName());
            ps.setInt(2, Integer.parseInt(train.getTrainId()));
            ps.setString(3, train.getSourceStation());
            ps.setString(4, train.getDestStation());
            ps.setString(5, String.valueOf(generateSeatNumber(train)));
            ps.setString(6, train.getName());

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("🎉 Ticket booked successfully for " + users.getName());
                return true;
            } else {
                System.out.println("❌ Booking failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<String> getUserBookings(Train train, Users users){

        String query = "SELECT * from Bookings "+
                "WHERE  user_name = ?";

        List<String> bookingList = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
          //  ps.setInt(1, Integer.parseInt(train.getTrainId()));
            ps.setString(1, users.getName());

            resultSet = ps.executeQuery();


            while (resultSet.next()){

                String seatNo = resultSet.getString("seat_number");
                String name = resultSet.getString("user_name");
                String trainName = resultSet.getString("train_name");
                String detail = "Train: " + trainName + ", Passenger: " + name + ", Seat No: " + seatNo;

                bookingList.add(detail);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookingList;

    }

    public int CheckSeatAvailability(Train train){

        String query = "SELECT total_seats from Trains " +
                "WHERE train_id = ?";

        int id =0;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(train.getTrainId()));

            resultSet = ps.executeQuery();

            if (resultSet.next()) {

                 id = resultSet.getInt("total_seats");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;

    }
    public int generateSeatNumber(Train train){
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random rand = new Random();
            int num = rand.nextInt(CheckSeatAvailability(train)) + 1;

            while (true) {
                if (!(uniqueNumbers.contains(num))) {
                    uniqueNumbers.add(num);
                    break;
                }else
                     num = rand.nextInt(CheckSeatAvailability(train)) + 1;

            }

            return num;
    }
}
