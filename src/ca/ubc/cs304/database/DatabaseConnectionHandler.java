package ca.ubc.cs304.database;

import ca.ubc.cs304.controller.Hotel;
import ca.ubc.cs304.model.Company;
import ca.ubc.cs304.model.HotelBelongs;
import ca.ubc.cs304.model.WorkerWorks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
					stmt.execute("DROP TABLE branch");
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
		ArrayList<HotelBelongs> result = new ArrayList<HotelBelongs>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM HOTEL_BELONGS, COMPANY WHERE HOTEL_BELONGS.COMPANYNAME = COMPANY.NAME and COMPANYNAME = " + "'" + companyName + "'";
//			String query = "SELECT * FROM HOTEL_BELONGS";

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
		ArrayList<Company> result = new ArrayList<Company>();
//		try {
//			Statement stmt = connection.createStatement();
////			String query = "SELECT * FROM hotel_belongs, company WHERE hotel_belongs.companyName = company.name and companyName = " + "\'" + companyName + "\'";
//			String query = "SELECT * FROM \"company\"";
//
//			ResultSet rs = stmt.executeQuery(query);
//
//			while (rs.next()) {
//				HotelBelongs model = new HotelBelongs(rs.getInt("id"),
//						rs.getString("hotelName"),
//						rs.getString("companyName"),
//						rs.getDouble("revenue"),
//						rs.getString("address"),
//						rs.getDate("builtTime"),
//						rs.getFloat("rating")
//				);
//				result.add(model);
//			}
//			rs.close();
//			stmt.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return result;
	}

	public ArrayList<WorkerWorks> listWorker(int departmentID, int hotelID) {
		ArrayList<WorkerWorks> result = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM WORKER_WORKS WHERE DID = " + "'" + departmentID + "'";
//			String query = "SELECT * FROM HOTEL_BELONGS";

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
//			String query = "SELECT * FROM HOTEL_BELONGS";

			ResultSet rs = stmt.executeQuery(query);

			if(rs.next()) {
				return "Full Time worker";
			}
			rs.close();

			query = "SELECT * FROM PARTTIMEWORKER WHERE WID = " + "'" + workerID + "'";
//			String query = "SELECT * FROM HOTEL_BELONGS";

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
		return false;
	}
}
