package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.BillPays;
import ca.ubc.cs304.model.Customer;
import ca.ubc.cs304.model.HotelBelongs;
import ca.ubc.cs304.model.WorkerWorks;

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
	boolean addWorker(WorkerWorks workerWorks);
	boolean deleteWorker(int workerID);
	List<WorkerWorks> listWorker(int departmentID, int hotelID);
	void listRoom(int hotelID);


	String checkWorkerType(int workerID);

	boolean checkMembership(int customerID);
	boolean assignMembership(int customerID, Customer customer);

	BillPays checkBill(int customerID);
	boolean addCustomer(int customerID, Customer customer);
	Customer checkCustomer(int hotelID);

	boolean addHotel(int hotelID, HotelBelongs hotel);
	boolean addRoom(int roomNumber, int price, String kind, String state, int hotelId);
	HotelBelongs checkHotel(int hotelID);
	List<HotelBelongs> checkCompany(String companyName);

	
	void terminalTransactionsFinished();
}
