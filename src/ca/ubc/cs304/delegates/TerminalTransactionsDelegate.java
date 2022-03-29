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
	public void databaseSetup();

	public void checkRoom(int hotelID);
	public boolean updateRoom(int hotelID, int roomNumber, int price, String state, String kind);
	public boolean addWorker(WorkerWorks workerWorks);
	public boolean deleteWorker(int workerID);
	public void listWorker(int departmentID, int hotelID);
	public void checkWorkerType(int workerID);

	public boolean checkMembership(int customerID);
	public boolean assignMembership(int customerID, Customer customer);

	public BillPays checkBill(int customerID);
	public boolean addCustomer(int customerID, Customer customer);
	public Customer checkCustomer(int hotelID);

	public boolean addHotel(int hotelID, HotelBelongs hotel);
	public HotelBelongs checkHotel(int hotelID);
	public List<HotelBelongs> checkCompany(String companyName);

	
	public void terminalTransactionsFinished();
}
