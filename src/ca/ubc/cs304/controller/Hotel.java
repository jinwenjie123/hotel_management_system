package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BillPays;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.HotelBelongs;
import ca.ubc.cs304.model.WorkerWorks;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;

import java.util.ArrayList;
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
        return false;
    }

    @Override
    public boolean addWorker(WorkerWorks workerWorks) {
        return false;
    }

    @Override
    public boolean deleteWorker(int workerID) {
        return false;
    }

    @Override
    public List<WorkerWorks> listWorker(int departmentID, int hotelID) {
        ArrayList<WorkerWorks> workers = dbHandler.listWorker(departmentID, hotelID);
        for (WorkerWorks worker : workers) {
            System.out.println(worker.getName());
            System.out.println(worker.getBirthDate());
            System.out.println(worker.getSex());
            System.out.println(worker.getContractStartTime());
        }
        return workers;
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
    public boolean assignMembership(int customerID, Customer customer) {
        return false;
    }

    @Override
    public List<BillPays> checkBill(int customerID) {
        return dbHandler.checkBill(customerID);
    }

    @Override
    public boolean addCustomer(int customerID, Customer customer) {
        return false;
    }

    @Override
    public List<Customer> checkAllCustomer(int hotelID) {
        List<Customer> result = dbHandler.checkAllCustomer(hotelID);
        return result;
    }

    @Override
    public boolean addHotel(int hotelID, HotelBelongs hotel) {
        return false;
    }

    @Override
    public HotelBelongs checkHotel(int hotelID) {
        return null;
    }

    @Override
    public List<HotelBelongs> checkCompany(String companyName) {
        List<HotelBelongs> hotels = dbHandler.checkCompany(companyName);

        for(HotelBelongs hotel : hotels) {
            System.out.println(hotel.getAddress());
            System.out.println(hotel.getHotelName());
            System.out.println(hotel.getBuiltTime());
        }

        return hotels;
    }

    @Override
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    /**
     * Main method called at launch time
     */
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.start();
    }
}
