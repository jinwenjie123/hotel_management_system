package ca.ubc.cs304.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;

	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}

	/**
	 * Sets up the database to have a branch table with two tuples so we can insert/update/delete from it.
	 * Refer to the databaseSetup.sql file to determine what tuples are going to be in the table.
	 */
	public void setupDatabase(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while(choice != 1 && choice != 2) {
			System.out.println("Welcome to Sunday Hotel Management System! If you want to proceed, enter 1; if you want to quit, enter 2.");

			choice = readInteger(false);

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						delegate.databaseSetup();
						break;
					case 2:
						handleQuitOption();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
						break;
				}
			}
		}
	}

	/**
	 * Displays simple text interface
	 */
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while (choice != 17) {
			System.out.println();
			System.out.println("|-------------------------------------------------------------- MENU ----------------------------------------------------------------------------|");
			System.out.println("| 1. Show all company                         | 2. Show hotels under a specific company            | 3. Add a hotel                              |");
			System.out.println("| 4. Find hotels with no available rooms      | 5. Find the most expensive room of each hotel      | 6. Check all available rooms in a hotel     |");
			System.out.println("| 7. Update the room status                   | 8. Add a new room                                  | 9. Check Room information                   |");
			System.out.println("| 10. Show workers under a specific department| 11. Check a worker's work type(part-time/full-time)| 12. Add a new worker                        |");
			System.out.println("| 13. Delete a worker                         | 14. Check all customers of a specific hotel        | 15. Check whether a customer is a membership|");
			System.out.println("| 16. Check a customers' bills                | 17. Add a customer                                 | 18. Assign a membership to a customer       |");
			System.out.println("| 19. Check a hotel's information             | 20. Quit                                           |                                             |");
			System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------------|");
			System.out.print("Please choose one of the above 20 options: ");
			System.out.print("Please choose one of the above 20 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
					case 1:
						handleShowAllCompanyOption();
						break;
					case 2:
						System.out.println("Please enter the company's name:");
						try {
							String companyName = bufferedReader.readLine();
							List<HotelBelongs> hotels = delegate.checkCompany(companyName);
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 10:
						System.out.println("Please enter the departmentID and hotelID");
						try {
							int departmentID = Integer.parseInt(bufferedReader.readLine());
							int hotelID = Integer.parseInt(bufferedReader.readLine());
							delegate.listWorker(departmentID, hotelID);
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 11:
						System.out.println("Please enter the worker's ID");
						try {
							int workerID = Integer.parseInt(bufferedReader.readLine());
							System.out.println(delegate.checkWorkerType(workerID));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 15:
						System.out.println("Please enter the customer's ID:");
						try {
							int customerID = Integer.parseInt(bufferedReader.readLine());
							System.out.println(delegate.checkMembership(customerID));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 9:
						System.out.println("Please enter the hotel ID");
						try {
							int hotelID = Integer.parseInt(bufferedReader.readLine());
							delegate.listRoom(hotelID);
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case 8:
						System.out.println("Please enter the RoomNumber, Price, Kind, State, and Hotel_ID in order");
						try {
							int roomNumber = Integer.parseInt(bufferedReader.readLine());
							int price = Integer.parseInt(bufferedReader.readLine());
							String kind = bufferedReader.readLine();
							String state = bufferedReader.readLine();
							int hotelId = Integer.parseInt(bufferedReader.readLine());
							if(delegate.addRoom(roomNumber, price, kind, state, hotelId)){
								System.out.println("Add new room successfully!");
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 16:
						System.out.println("Please enter the customer's ID:");
						try {
							int customerID = Integer.parseInt(bufferedReader.readLine());
							List<BillPays> bills = delegate.checkBill(customerID);
							if(!bills.isEmpty()) {
								for(BillPays bill : bills) {
									System.out.println(bill.getbID());
									System.out.println(bill.getcID());
									System.out.println(bill.getPrice());
									System.out.println(bill.getPaymentDate());
									System.out.println(bill.getPaymentMethod());
								}
							} else {
								System.out.println("The customer doesn't have any bills!");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 14:
						try {
							System.out.println("Please enter the hotel's ID:");
							int hotelID = Integer.parseInt(bufferedReader.readLine());
							List<Customer> customers = delegate.checkAllCustomer(hotelID);
							if(customers != null) {
								for (Customer customer : customers) {
									System.out.println("Name: " + customer.getName() + " CID: " + customer.getcID() + " Hotel_id: " + customer.getHotelID() + " address: " + customer.getAddress() + " phone: " + customer.getPhoneNumber() + " driving license: " + customer.getDrivingLicense() + " checkin_time: " + customer.getCheckinTime() + " checkout time: " + customer.getCheckoutTime() + "\n");
								}
							} else {
								System.out.println("This hotel currently has no customer! Poor hotel!");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case 12:
						try {
							System.out.println("Please enter a new worker's workerId, departmentId, name, birthdate, gender, department, contract start time");
							int workerId = Integer.parseInt(bufferedReader.readLine());
							int dId = Integer.parseInt(bufferedReader.readLine());
							String name = bufferedReader.readLine();
							String birthDayString = bufferedReader.readLine();
							String sex = bufferedReader.readLine();
							String department = bufferedReader.readLine();
							String contract_start_time_string = bufferedReader.readLine();
							if(delegate.addWorker(workerId, dId, name, birthDayString, sex,department, contract_start_time_string)){
								System.out.println("Add new worker successfully!");
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 7:
						try {
							System.out.println("Please enter the room's information.(including hotelID, roomNumber, price, state, and kind)");
							int hotelID = Integer.parseInt(bufferedReader.readLine());
							int roomNumber = Integer.parseInt(bufferedReader.readLine());
							int price = Integer.parseInt(bufferedReader.readLine());
							String kind = bufferedReader.readLine();
							String state = bufferedReader.readLine();
							if(delegate.updateRoom(hotelID, roomNumber, price, kind, state)){
								System.out.println("Updated room's information successfully!");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case 13:
						try {
							System.out.println("Please enter the worker's wID");
							int workerID = Integer.parseInt(bufferedReader.readLine());
							if(delegate.deleteWorker(workerID)){
								System.out.println("Delete the worker's information successfully!");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 17:
						try {
							System.out.println("Please enter the customer's driving license number:");
							String drivingLicense = bufferedReader.readLine();
							System.out.println("Please enter the customer's name:");
							String customerName = bufferedReader.readLine();
							boolean insertCustomer = delegate.addCustomer(drivingLicense, customerName);
							if(insertCustomer) {
								System.out.println("Add customer successfully!");
							} else {
								System.out.println("Add Failed! The customer already exists!");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 18:
						try {
							System.out.println("Please enter the membership ID:");
							int membershipID = Integer.parseInt(bufferedReader.readLine());
							System.out.println("Please enter the customer's ID:");
							int customerID = Integer.parseInt(bufferedReader.readLine());
							System.out.println("Please enter the join date:");
							String joinDate = bufferedReader.readLine();
							System.out.println("Please enter the membership discount:");
							float discount = Float.parseFloat(bufferedReader.readLine());
							System.out.println("Please enter the customer's credit:");
							long credit = Long.parseLong(bufferedReader.readLine());

							boolean assignMembership = delegate.assignMembership(membershipID, customerID, joinDate, discount, credit);
							if(assignMembership) {
								System.out.println("Membership assigned successfully!");
							} else {
								System.out.println("Assignment Failed!");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 3:
						try {
							System.out.println("Please enter the hotel's ID:");
							int hotelId = Integer.parseInt(bufferedReader.readLine());
							System.out.println("Please enter the hotel's name:");
							String hotelName = bufferedReader.readLine();
							System.out.println("Please enter the company's name:");
							String companyName = bufferedReader.readLine();
							System.out.println("Please enter the hotel's annual revenue:");
							double revenue = Double.parseDouble(bufferedReader.readLine());
							System.out.println("Please enter the hotel's address:");
							String address = bufferedReader.readLine();
							System.out.println("Please enter the hotel's build date:");
							String builtTime = bufferedReader.readLine();
							System.out.println("Please enter the hotel's rating:");
							float rating = Float.parseFloat(bufferedReader.readLine());

							boolean insertHotel = delegate.addHotel(hotelId, hotelName, companyName, revenue, address, builtTime, rating);
							if(insertHotel) {
								System.out.println("Add hotel successfully!");
							} else {
								System.out.println("Add Failed!");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 19:
						try {
							System.out.println("Please enter the hotel's ID:");
							int hotelID = Integer.parseInt(bufferedReader.readLine());
							List<HotelBelongs> hotels = delegate.checkHotel(hotelID);
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 6:
						try {
							System.out.println("Please enter the hotel ID to find the number of available rooms");
							int hotelID = Integer.parseInt(bufferedReader.readLine());
							int numRoom = delegate.numberOfAvailableRoom(hotelID);
							if(numRoom == -1){
								System.out.println("Wrong");
							}else{
								System.out.println("The total number of available room is:" + numRoom);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 5:
						System.out.println("The room numbers of the most expensive room in each hotel are: \n");
						List<Room> rooms = delegate.expensiveRoom();
						for (Room room : rooms) {
							System.out.println("room number: " + room.getRoom_number() + " in the hotel_id: " + room.getHotel_id() + "\n");
						}
						break;
					case 4:
						List<HotelBelongs> hotels = delegate.showFullHotels();
						if(!hotels.isEmpty()) {
							for(HotelBelongs hotel : hotels) {
								System.out.println(hotel.getId() + " | " + hotel.getHotelName() + " | " + hotel.getRevenue() + " | " + hotel.getBuiltTime() + " | " + hotel.getCompanyName() + " | " + hotel.getAddress() + " | " + hotel.getRating());
							}
						} else {
							System.out.println("All hotels have available rooms!");
						}
						break;
					case 20:
						delegate.terminalTransactionsFinished();
						break;
					default:
						System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
						break;
				}
			}
		}
	}

	private void handleDeleteOption() {
//		int branchId = INVALID_INPUT;
//		while (branchId == INVALID_INPUT) {
//			System.out.print("Please enter the branch ID you wish to delete: ");
//			branchId = readInteger(false);
//			if (branchId != INVALID_INPUT) {
//				delegate.deleteBranch(branchId);
//			}
//		}
	}

	private void handleShowAllCompanyOption() {
		List<Company> companies = delegate.showAllCompany();
		for (Company company : companies) {
			System.out.println(company.getName());
			System.out.println(company.getAddress());
			System.out.println(company.getBuiltTime());
			System.out.println(company.getMarketPrice());
		}
	}

	private void handleQuitOption() {
		System.out.println("Good Bye!");

		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}

		delegate.terminalTransactionsFinished();
	}

	private void handleUpdateOption() {
//		int id = INVALID_INPUT;
//		while (id == INVALID_INPUT) {
//			System.out.print("Please enter the branch ID you wish to update: ");
//			id = readInteger(false);
//		}
//
//		String name = null;
//		while (name == null || name.length() <= 0) {
//			System.out.print("Please enter the branch name you wish to update: ");
//			name = readLine().trim();
//		}
//
//		delegate.updateBranch(id, name);
	}

	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}

	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}