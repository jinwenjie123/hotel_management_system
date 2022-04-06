package ca.ubc.cs304.database;

import ca.ubc.cs304.controller.Hotel;
import ca.ubc.cs304.model.Company;
import ca.ubc.cs304.model.HotelBelongs;
import ca.ubc.cs304.model.Room;
import ca.ubc.cs304.model.WorkerWorks;
import ca.ubc.cs304.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	
	private Connection connection = null;
	
	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}
	
			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);
	
			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();	
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void databaseSetup() {
//		dropBranchTableIfExists();
//
//		try {
//			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)");
//			stmt.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//		}
//
//		BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
//		insertBranch(branch1);
//
//		BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
//		insertBranch(branch2);
	}
	
	private void dropBranchTableIfExists() {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select table_name from user_tables");
			
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase("branch")) {
					//stmt.execute("DROP TABLE branch");
					break;
				}
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public List<HotelBelongs> checkCompany(String companyName) {
		ArrayList<HotelBelongs> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM HOTEL_BELONGS, COMPANY WHERE HOTEL_BELONGS.COMPANYNAME = COMPANY.NAME and COMPANYNAME = " + "'" + companyName + "'";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				HotelBelongs model = new HotelBelongs(rs.getInt("id"),
						rs.getString("hotelName"),
						rs.getString("companyName"),
						rs.getDouble("revenue"),
						rs.getString("address"),
						rs.getDate("builtTime"),
						rs.getFloat("rating")
						);
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Company> showAllCompany() {
		ArrayList<Company> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM COMPANY";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Company model = new Company(rs.getString("name"),
						rs.getDouble("marketPrice"),
						rs.getDate("builtTime"),
						rs.getString("address"));
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Room> expensiveRoom(){
		ArrayList<Room> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT\n" +
					" rl.ROOMNUMBER,\n" +
					" rl.HOTEL_ID \n" +
					"FROM\n" +
					" (SELECT HOTEL_ID, MAX( PRICE ) AS maxPrice FROM ROOM_CONTAINS GROUP BY HOTEL_ID) temp,\n" +
					" ROOM_CONTAINS rl \n" +
					"WHERE\n" +
					" temp.HOTEL_ID = rl.HOTEL_ID \n" +
					" AND temp.maxPrice = rl.PRICE";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Room model = new Room(rs.getInt("ROOMNUMBER"),
						0,
						null,
						null,
						rs.getInt("HOTEL_ID"));
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Room> listRoom(int hotelId){
		ArrayList<Room> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM ROOM_CONTAINS WHERE HOTEL_ID = " + "'" + hotelId + "'";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Room model = new Room(rs.getInt("ROOMNUMBER"),
						rs.getInt("PRICE"),
						rs.getString("KIND"),
						rs.getString("STATE"),
						rs.getInt("HOTEL_ID"));
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<WorkerWorks> listWorker(int departmentID, int hotelID) {
		ArrayList<WorkerWorks> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM WORKER_WORKS WHERE DID = " + "'" + departmentID + "'";

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				WorkerWorks model = new WorkerWorks(rs.getInt("wid"),
						rs.getInt("did"),
						rs.getString("name"),
						rs.getDate("birthdate"),
						rs.getString("sex"),
						rs.getString("department"),
						rs.getDate("contractstarttime"));
				result.add(model);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String checkWorkerType(int workerID) {
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM FULLTIMEWORKER WHERE WID = " + "'" + workerID + "'";

			ResultSet rs = stmt.executeQuery(query);

			if(rs.next()) {
				return "Full Time worker";
			}
			rs.close();

			query = "SELECT * FROM PARTTIMEWORKER WHERE WID = " + "'" + workerID + "'";


			rs = stmt.executeQuery(query);

			if(rs.next()) {
				return "Part Time worker";
			}
			rs.close();

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Something wrong happens. Please try later.";
	}

	public boolean checkMembership(int customerID) {
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM MEMBERSHIP_APPLIES WHERE CID = " + "'" + customerID + "'";

			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addRoom(int roomNumber, int price, String kind, String state, int hotelId) {
		try {
			Statement stmt = connection.createStatement();
			String query = "INSERT INTO ROOM_CONTAINS VALUES" + " (" + "'" + roomNumber + "'" + "," + "'" + price + "'" + "," +
					"'" + kind + "'" + "," + "'" + state + "'" + "," + "'" + hotelId + "'" + ")";

			int rowCount = stmt.executeUpdate(query);
			if (rowCount >= 1) {
				connection.commit();
				stmt.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateRoom(int hotelID, int roomNumber, int price, String state, String kind){
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE ROOM_CONTAINS SET HOTEL_ID = ?, ROOMNUMBER = ?, PRICE = ?, STATE = ?, KIND = ? WHERE HOTEL_ID = ? AND ROOMNUMBER = ?");
			ps.setInt(1, hotelID);
			ps.setInt(2,roomNumber);
			ps.setInt(3, price);
			ps.setString(4, state);
			ps.setString(5,kind);
			ps.setInt(6,hotelID);
			ps.setInt(7,roomNumber);
			int rowCount = ps.executeUpdate();
			if (rowCount >= 1) {
				connection.commit();
				ps.close();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addWorker(int workerId, int dId, String name, String birthdayString, String sex, String department, String contract_start_time_string){
		try {
			Statement stmt = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement("INSERT INTO WORKER_WORKS VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, workerId);
			ps.setInt(2, dId);
			ps.setString(3, name);
			ps.setDate(4, java.sql.Date.valueOf(birthdayString));
			ps.setString(5, sex);
			ps.setString(6, department);
			ps.setDate(7, java.sql.Date.valueOf(contract_start_time_string));
			ps.executeUpdate();

			connection.commit();
			ps.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int numberOfAvailableRoom(int hotelID){
		int numRooms = -1;
		try {
			Statement stmt = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(ROOMNUMBER) AS total FROM ROOM_CONTAINS WHERE STATE = ? AND HOTEL_ID = ?");
			ps.setString(1, "Available");
			ps.setInt(2, hotelID);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				numRooms = rs.getInt("total");
			}

			rs.close();
			stmt.close();
			return numRooms;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numRooms;
	}

	public boolean deleteWorker(int workerID){
		try {
			Statement stmt = connection.createStatement();
			String query = "DELETE FROM WORKER_WORKS WHERE WID = " + "'" + workerID + "'";
			int rowCount = stmt.executeUpdate(query);
			if (rowCount >= 1) {
				connection.commit();
				stmt.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<BillPays> checkBill(int customerID) {
		ArrayList<BillPays> bills = new ArrayList<>();
		BillPays bill;
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM BILL_PAYS WHERE CID = " + "'" + customerID + "'";

			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				bill = new BillPays(rs.getInt("bID"),
						rs.getInt("cID"),
						rs.getDouble("price"),
						rs.getString("state"),
						rs.getDate("paymentDate"),
						rs.getString("paymentMethod"));
				bills.add(bill);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bills;
	}

	public List<Customer> checkAllCustomer(int hotelID) {
		ArrayList<Customer> customers = new ArrayList<>();
		Customer customer;
		try {
			Statement stmt = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER_STAY, CUSTOMER WHERE HOTEL_ID = ? AND CUSTOMER_STAY.DRIVING_LICENSE = CUSTOMER.DRIVING_LICENSE");

			ps.setInt(1, hotelID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				customer = new Customer(rs.getInt("cID"),
						rs.getInt("hotel_id"),
						rs.getString("address"),
						rs.getString("phone_number"),
						rs.getString("driving_license"),
						rs.getDate("checkin_time"),
						rs.getDate("checkout_time"),
						rs.getString("name"));
				customers.add(customer);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public boolean addCustomer(String drivingLicense, String name) {
		boolean result = false;
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMER VALUES (?, ?)");
			ps.setString(1, drivingLicense);
			ps.setString(2, name);

			ps.executeUpdate();
			connection.commit();
			ps.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean assignMembership(int membershipID, int customerID, String joinDate, float discount, long credit) {
		boolean result = false;
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO MEMBERSHIP_APPLIES VALUES (?, ?, ?, ?, ?)");
			ps.setInt(1, membershipID);
			ps.setInt(2, customerID);
			ps.setString(3, joinDate);
			ps.setFloat(4, discount);
			ps.setLong(5, credit);

			ps.executeUpdate();
			connection.commit();
			ps.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<HotelBelongs> showFullHotels() {
		ArrayList<HotelBelongs> hotels = new ArrayList<>();
		HotelBelongs hotel;
		try {
			Statement stmt = connection.createStatement();
//			String query = "SELECT DISTINCT * FROM HOTEL_BELONGS WHERE ID NOT IN ( SELECT HOTEL_ID FROM ROOM_CONTAINS WHERE ROOMNUMBER IN (( SELECT DISTINCT ROOMNUMBER FROM ROOM_CONTAINS ) MINUS ( SELECT ROOMNUMBER FROM ROOM_CONTAINS WHERE STATE = 'Occupied' )))";
			String query = "SELECT DISTINCT r1.HOTEL_ID, h1.HOTELNAME, h1.COMPANYNAME, h1.REVENUE, h1.ADDRESS, h1.BUILTTIME, h1.RATING FROM ROOM_CONTAINS r1, HOTEL_BELONGS h1 WHERE r1.HOTEL_ID = h1.ID AND NOT EXISTS (( SELECT * FROM ROOM_CONTAINS r2 WHERE r2.HOTEL_ID = r1.HOTEL_ID ) MINUS ( SELECT * FROM ROOM_CONTAINS r2 WHERE r2.HOTEL_ID = r1.HOTEL_ID AND r2.STATE = 'Occupied' ) )";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				hotel = new HotelBelongs(rs.getInt("HOTEL_ID"),
						rs.getString("HOTELNAME"),
						rs.getString("COMPANYNAME"),
						rs.getLong("REVENUE"),
						rs.getString("ADDRESS"),
						rs.getDate("BUILTTIME"),
						rs.getFloat("RATING"));
				hotels.add(hotel);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotels;
	}

	public List<HotelBelongs> checkHotel(int hotelID) {
		ArrayList<HotelBelongs> hotels = new ArrayList<>();
		HotelBelongs hotel;
		try {
			Statement stmt = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM HOTEL_BELONGS WHERE ID = ?");

			ps.setInt(1, hotelID);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				hotel = new HotelBelongs(rs.getInt("ID"),
						rs.getString("hotelName"),
						rs.getString("companyName"),
						rs.getDouble("revenue"),
						rs.getString("address"),
						rs.getDate("builtTime"),
						rs.getFloat("rating"));
				hotels.add(hotel);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotels;
	}

	public boolean addHotel(int hotelId, String hotelName, String companyName, double revenue, String address, String builtTime, double rating) {
		boolean result = false;
		try{
			PreparedStatement ps = connection.prepareStatement("INSERT INTO HOTEL_BELONGS VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, hotelId);
			ps.setString(2, hotelName);
			ps.setString(3, companyName);
			ps.setDouble(4, revenue);
			ps.setString(5, address);
			ps.setDate(6, java.sql.Date.valueOf(builtTime));
			ps.setDouble(7, rating);

			ps.executeUpdate();
			connection.commit();
			ps.close();
			result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
