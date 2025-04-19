package org.selenium;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private String name;
    private String password;
    private String userId;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;



    public Users(String name, String password, String hashedPassword, List<Ticket> ticketsBooked, String userId){
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printTickets(){
        for (int i=0; i<ticketsBooked.size();i++){
            System.out.println(ticketsBooked.get(i));
        }
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashPassword() {
        return hashedPassword;
    }

    public void setHashPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public List<Ticket> getTickets() {
        return ticketsBooked;
    }

    public void setTickets(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }
}
