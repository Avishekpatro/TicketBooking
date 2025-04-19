package Services;

import LocalDB.Database;
import Util.UserServiceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.selenium.Train;
import org.selenium.Users;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserServices {

    private Users users;
    private List<Users> usersList;
    private Database localDB = new Database();

    public ObjectMapper objectMapper =  new ObjectMapper();

    public UserServices(Users users) throws IOException {
        this.users = users;
    }

    public UserServices() throws IOException {


    }

    public void loginUser(Users users1){

//        Optional<Users> optionalUsers = usersList.stream().filter(user1 -> {return (users.getName().equals(user1.getName()) &&
//                UserServiceUtil.checkPassword(users.getPassword(), user1.getHashPassword()));} ).findFirst();

        try {
            this.users = users1;
            localDB.CreateConnection();
            localDB.selectData(users1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean signup(Users users1) throws SQLException {

        this.users = users1;
        localDB.CreateConnection();
        localDB.UpdateLoginTable(users1);
//        usersList.add(users1);
//        saveUserlistToFile();
        return true;
    }



    public List<String> fetchBooking(Train train){

        localDB.CreateConnection();
        return localDB.getUserBookings(train,users);

    }

    public HashMap<Integer,String> getTrains(String source, String destination) {

        TrainServices services = null;
        try {
            services = new TrainServices();
        } catch (IOException e) {
            return new HashMap<>();

        }
        return services.searchTrains(source, destination);

    }

    public Boolean bookTrainSeat(Train train) {

        localDB.CreateConnection();

        if (localDB.bookTickets(train)) {
          localDB.bookTicket(users,train);
                return true; // Booking successful
            } else {
                return false; // Seat is already booked
            }

    }

    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }
}
