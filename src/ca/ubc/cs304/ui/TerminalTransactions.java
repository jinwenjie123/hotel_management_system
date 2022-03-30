package ca.ubc.cs304.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

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
			System.out.println("If you have a table called Branch in your database (capitialization of the name does not matter), it will be dropped and a new Branch table will be created.\nIf you want to proceed, enter 1; if you want to quit, enter 2.");
			
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
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Show all company");
			System.out.println("2. Delete branch");
			System.out.println("3. Update branch name");
			System.out.println("4. Show hotels of a specific company");
			System.out.println("5. Show workers under a specific department");
			System.out.println("6. Check whether a worker is part time or full time");
			System.out.println("7. Check whether a customer is a membership");
			System.out.println("8. Check Room information");
			System.out.println("9. Add a new room");
			System.out.print("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					handleShowAllCompanyOption();
					break;
				case 2:  
					handleDeleteOption(); 
					break;
				case 3: 
					handleUpdateOption();
					break;
				case 4:
					System.out.println("Please enter the company's name:");
					try {
						String companyName = bufferedReader.readLine();
						delegate.checkCompany(companyName);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 5:
					System.out.println("Please enter the departmentID and hotelID");
					try {
						int departmentID = Integer.parseInt(bufferedReader.readLine());
						int hotelID = Integer.parseInt(bufferedReader.readLine());
						delegate.listWorker(departmentID, hotelID);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 6:
						System.out.println("Please enter the worker's ID");
						try {
							int workerID = Integer.parseInt(bufferedReader.readLine());
							System.out.println(delegate.checkWorkerType(workerID));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
				case 7:
						System.out.println("Please enter the customer's ID:");
						try {
							int customerID = Integer.parseInt(bufferedReader.readLine());
							System.out.println(delegate.checkMembership(customerID));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
				case 8:
						System.out.println("Please enter the hotel ID");
						try {
							int hotelID = Integer.parseInt(bufferedReader.readLine());
							delegate.listRoom(hotelID);
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case 9:
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
//		delegate.showAllCompany();
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
