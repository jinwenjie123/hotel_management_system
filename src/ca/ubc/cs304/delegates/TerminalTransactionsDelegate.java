package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.*;

import java.util.Date;
import java.util.List;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
	void databaseSetup();

	void checkRoom(int hotelID);
	boolean updateRoom(int hotelID, int roomNumber, int price, String state, String kind);
	boolean addWorker(int workerId, int dId, String name, String birthday, String sex, String department, String contract_start_time);
	boolean deleteWorker(int workerID);
	List<WorkerWorks> listWorker(int departmentID, int hotelID);
	void listRoom(int hotelID);


	String checkWorkerType(int workerID);

	boolean checkMembership(int customerID);
	boolean assignMembership(int membershipID, int customerID, String joinDate, float discount, long credit);

	List<BillPays> checkBill(int customerID);
	boolean addCustomer(String drivingLicense, String name);
	List<Customer> checkAllCustomer(int hotelID);

	boolean addHotel(int hotelID, String hotelName, String companyName, double revenue, String address, String builtTime, double rating);
	boolean addRoom(int roomNumber, int price, String kind, String state, int hotelId);
	List<HotelBelongs> checkHotel(int hotelID);
	List<HotelBelongs> checkCompany(String companyName);

	List<Company> showAllCompany();
	List<Room> expensiveRoom();
	List<HotelBelongs> showFullHotels();

	void terminalTransactionsFinished();
	int numberOfAvailableRoom(int hotelID);
}
