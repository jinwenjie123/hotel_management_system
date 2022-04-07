package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Hotel implements LoginWindowDelegate, TerminalTransactionsDelegate {

    private DatabaseConnectionHandler dbHandler;
    private LoginWindow loginWindow = null;

    public Hotel() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    @Override
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            TerminalTransactions transaction = new TerminalTransactions();
            transaction.setupDatabase(this);
            transaction.showMainMenu(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    @Override
    public void databaseSetup() {
        dbHandler.databaseSetup();
    }


    @Override
    public void checkRoom(int hotelID) {

    }

    @Override
    public boolean updateRoom(int hotelID, int roomNumber, int price, String state, String kind) {
        return dbHandler.updateRoom(hotelID, roomNumber, price, kind, state);
    }

    @Override
    public boolean deleteWorker(int workerID) {
        return dbHandler.deleteWorker(workerID);
    }

    @Override
    public List<WorkerWorks> listWorker(int departmentID, int hotelID) {
        ArrayList<WorkerWorks> workers = dbHandler.listWorker(departmentID, hotelID);
        if (workers.isEmpty()) {
            System.out.printf("Error! Wrong Department ID!");
            return workers;
        }
        for (WorkerWorks worker : workers) {
            System.out.println("Department id: " + worker.getdID());
            System.out.println("Worker's id: " + worker.getwID());
            System.out.println("Worker's name: " + worker.getName());
            System.out.println("Worker's birthday: " + worker.getBirthDate());
            System.out.println("Worker's gender: " + worker.getSex());
            System.out.println("Worker's contract start time: " + worker.getContractStartTime());
            System.out.println();
        }
        return workers;
    }

    @Override
    public void listRoom(int hotelID){
        ArrayList<Room> rooms = dbHandler.listRoom(hotelID);

        if (rooms.isEmpty()) {
            System.out.printf("Error! Wrong roomnumber!");
        }
        for (Room room : rooms) {
            System.out.println("Room number: " + room.getRoom_number() + " | "+ "Room's price: " + room.getPrice() + " | " + "Room's kind: " + room.getKind() + " | " + "Room's state: " + room.getState() + " | " + "Room belongs to hotel #: " + room.getHotel_id());
        }
    }

    @Override
    public String checkWorkerType(int workerID) {
        return dbHandler.checkWorkerType(workerID);
    }

    @Override
    public boolean checkMembership(int customerID) {
        return dbHandler.checkMembership(customerID);
    }

    @Override
    public boolean assignMembership(int membershipID, int customerID, String joinDate, float discount, long credit) {
        return dbHandler.assignMembership(membershipID, customerID, joinDate, discount, credit);
    }

    @Override
    public List<BillPays> checkBill(int customerID) {
        return dbHandler.checkBill(customerID);
    }

    @Override
    public boolean addCustomer(String drivingLicense, String name) {

        return dbHandler.addCustomer(drivingLicense, name);

    }

    @Override
    public int numberOfAvailableRoom(int hotelID){
        return dbHandler.numberOfAvailableRoom(hotelID);
    }

    @Override
    public List<Customer> checkAllCustomer(int hotelID) {
        List<Customer> result = dbHandler.checkAllCustomer(hotelID);
        return result;
    }


    @Override
    public boolean addHotel(int hotelId, String hotelName, String companyName, double revenue, String address, String builtTime, double rating) {
        return dbHandler.addHotel(hotelId, hotelName, companyName, revenue, address, builtTime, rating);
    }

    @Override
    public boolean addRoom(int roomNumber, int price, String kind, String state, int hotelId) {
        return dbHandler.addRoom(roomNumber, price, kind, state, hotelId);
    }

    @Override
    public boolean addWorker(int workerId, int dId, String name, String birthday, String sex, String department, String contract_start_time){
        return dbHandler.addWorker(workerId, dId, name, birthday, sex,department, contract_start_time);
    }


    @Override
    public List<HotelBelongs> checkHotel(int hotelID) {
        List<HotelBelongs> hotels = dbHandler.checkHotel(hotelID);

        for(HotelBelongs hotel : hotels) {
            System.out.println("Hotel ID: " + hotel.getId() + "\n" + " Hotel Name: " + hotel.getHotelName() + "\n" + " Company Name: " + hotel.getCompanyName() + "\n" + " Revenue: " + hotel.getRevenue() + "\n" + " Address: " + hotel.getAddress() + "\n" + " Built Date: " + hotel.getBuiltTime() + "\n" + " Rating: " + hotel.getRating() + "\n");
        }

        return hotels;
    }

    @Override
    public List<HotelBelongs> checkCompany(String companyName) {
        List<HotelBelongs> hotels = dbHandler.checkCompany(companyName);

        for(HotelBelongs hotel : hotels) {
            System.out.println("Hotel name: " + hotel.getHotelName() + " Address: " + hotel.getAddress() + " Build-time: " + hotel.getBuiltTime());
        }

        return hotels;
    }

    @Override
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    @Override
    public List<Company> showAllCompany() {
        return dbHandler.showAllCompany();
    }

    @Override
    public List<Room> expensiveRoom(){
        return dbHandler.expensiveRoom();
    }

    @Override
    public List<HotelBelongs> showFullHotels() {
        List<HotelBelongs> results = dbHandler.showFullHotels();
        return results;
    }

    /**
     * Main method called at launch time
     */
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.start();
    }
}
