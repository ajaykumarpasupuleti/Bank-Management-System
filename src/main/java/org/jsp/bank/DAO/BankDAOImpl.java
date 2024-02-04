package org.jsp.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import org.jsp.bank.App;
import org.jsp.bank.model.Bank;

import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.xdevapi.Result;

//Implementation Class

public class BankDAOImpl implements BankDAO {
	Scanner scanner = new Scanner(System.in);
	
	String url = "jdbc:mysql://localhost:3306/teca52?user=root&password=12345";
	public void userRegistration(Bank bank) {
		
		String insertion = "insert into bank(FirstName, LastName, MobileNumber, EmailId, Password, Amount, Address, AccountNumber) values(?,?,?,?,?,?,?,?)";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(insertion);
			preparedStatement.setString(1, bank.getFirstName());
			preparedStatement.setString(2, bank.getLastName());
			preparedStatement.setString(3, bank.getMobileNumber());
			
			Random random = new Random(); //randomly generating 3 digit number
			int randomDigits = random.nextInt(1000);
			if(randomDigits<100) {
				randomDigits+=100;
			}
			String emailId = bank.getFirstName().toLowerCase()+randomDigits+"@teca52.com";
			preparedStatement.setString(4, emailId);
			
			preparedStatement.setString(5, bank.getPassword());
			preparedStatement.setDouble(6, bank.getAmount());
			preparedStatement.setString(7, bank.getAddress());
			
			long randomNumber = random.nextLong(100000000000l);//randomly generating 11 digit account number
			if(randomNumber<10000000000l) {
				randomNumber+=10000000000l;
			}
			String accountNumber = Long.toString(randomNumber);
			preparedStatement.setString(8, accountNumber);
			
			int result = preparedStatement.executeUpdate();
			if(result!=0) {
				try {
					Thread.sleep(1000);
					System.out.println("**********Account Created Successfully**********");
					Thread.sleep(2000);
					System.out.println("Your Email Id is :"+emailId);
					System.out.println("Your Account Number is :"+accountNumber);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Invalid Data");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void credit(String accountNumber) {
		String selection = "select * from bank where AccountNumber=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(selection);
			preparedStatement.setString(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				double amount;
				System.out.print("Enter the amount : ");
				while(true) {
					try {
						amount = scanner.nextDouble();
						while(!(amount>=0)) {
							System.out.print("Invalid Amount\n");
							System.out.print("Please Re-Enter the amount : ");
							amount = scanner.nextDouble();
						}
						break;
					}
					catch(InputMismatchException e) {
						System.out.print("Please Re-Enter the amount : ");
						scanner.next();
					}
				}
				double databaseAmount = resultSet.getDouble("Amount");
				
				double totalAmount = databaseAmount + amount;
				String updation = "update bank set Amount=? where AccountNumber=?";
				preparedStatement = connection.prepareStatement(updation);
				preparedStatement.setDouble(1, totalAmount);
				preparedStatement.setString(2, accountNumber);
				int result = preparedStatement.executeUpdate();
				if(result!=0) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("*****Amount Credited Successfully*****");
				}
				else {
					System.out.println("Server Issue");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void debit(String accountNumber, String password) {
		String selection = "select * from bank where AccountNumber=? and Password=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(selection);
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, password);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				double amount;
				System.out.print("Enter the amount : ");
				while(true) {
					try {
						amount = scanner.nextDouble();
						while(!(amount>=0)) {
							System.out.print("Invalid Amount\n");
							System.out.print("Please Re-Enter the amount : ");
							amount = scanner.nextDouble();
						}
						break;
					}
					catch(InputMismatchException e) {
						System.out.print("Please Re-Enter the amount : ");
						scanner.next();
					}
				}
				double databaseAmount = resultSet.getDouble("Amount");
				if(databaseAmount>=amount) {
					double remaingBalance = databaseAmount-amount;
					String updation = "update bank set Amount=? where AccountNumber=? and Password=?";
					preparedStatement = connection.prepareStatement(updation);
					preparedStatement.setDouble(1, remaingBalance);
					preparedStatement.setString(2, accountNumber);
					preparedStatement.setString(3, password);
					int result = preparedStatement.executeUpdate();
					if(result!=0) {
						try {
							Thread.sleep(1000);
							System.out.println(".....");
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("*****Amount Debited Successfully****");
						System.out.print("Do you want to check remaining balance\nYes or No : ");
						String check = scanner.next();
						if(check.equalsIgnoreCase("Yes")) {
							System.out.println("Remaining Balance is : "+remaingBalance);
						}
						else {
							System.out.println("Thank You! Visit Again 😊");
						}
					}
					else {
						System.out.println("Server 404");
					}
				}
				else {
					System.out.println("Insufficient Balance");
				}
			}
			else {
				System.out.println("No data found");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changingThePassword(String accountNumber,String password) {
		String selection = "select * from bank where AccountNumber=? and Password=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(selection);
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.print("Enter Your New Password : ");
				String newPassword = scanner.next();
				System.out.print("Confirm Your Password : ");
				String confirmPassword = scanner.next();
				
				while(!newPassword.equals(confirmPassword)) {
					System.out.print("Password not matched\n");
					System.out.println("Please Re-Confirm Your Password");
					confirmPassword = scanner.next();
				}
				String updation = "update bank set Password=? where AccountNumber=?";
				preparedStatement = connection.prepareStatement(updation);
				preparedStatement.setString(1, newPassword);
				preparedStatement.setString(2, accountNumber);
				int result = preparedStatement.executeUpdate();
				if(result!=0) {
					try {
						Thread.sleep(1000);
						System.out.println("****Password Updated Successfully!*****");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Password Updation Failed");
				}
			}
			else {
				System.out.println("No data found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void mobileToMobileTransaction(String mobileNumber) {
		String selection = "select * from bank where MobileNumber=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(selection);
			preparedStatement.setString(1, mobileNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.print("Enter Receivers Mobile Number : ");
				String receiverMobileNumber = scanner.next();
				
				selection = "select * from bank where MobileNumber=?";
				preparedStatement = connection.prepareStatement(selection);
				preparedStatement.setString(1, receiverMobileNumber);
				ResultSet resultSet2 = preparedStatement.executeQuery();
				if(resultSet2.next()) {
					double amount;
					System.out.print("Enter the amount : ");
					while(true) {
						try {
							amount = scanner.nextDouble();
							while(!(amount>=0)) {
								System.out.print("Invalid Amount\n");
								System.out.print("Please Re-Enter the amount : ");
								amount = scanner.nextDouble();
							}
							break;
						}
						catch(InputMismatchException e) {
							System.out.print("Please Re-Enter the amount : ");
							scanner.next();
						}
					}
					
					double senderAmount = resultSet.getDouble("Amount");
					if(amount<=senderAmount) {
						double receiverAmount = resultSet2.getDouble("Amount");
						double totalReceiverAmount = receiverAmount + amount;
						double remainingSenderAmount = senderAmount - amount;
						
						String updation1 = "update bank set Amount=? where MobileNumber=?";
						preparedStatement = connection.prepareStatement(updation1);
						preparedStatement.setDouble(1, remainingSenderAmount);
						preparedStatement.setString(2, mobileNumber);
						int result1 = preparedStatement.executeUpdate();
						
						String updation2 = "update bank set Amount=? where MobileNumber=?";
						preparedStatement = connection.prepareStatement(updation1);
						preparedStatement.setDouble(1, totalReceiverAmount);
						preparedStatement.setString(2, receiverMobileNumber);
						int result2 = preparedStatement.executeUpdate();
						
						if(result1!=0 && result2!=0) {
							try {
								Thread.sleep(2000);
								System.out.println("****Mobile To Mobile Transaction Successful*****");
								Thread.sleep(00);
								String rmn = receiverMobileNumber.substring(0,3)+"XXXX"+receiverMobileNumber.substring(7);
								System.out.println(amount+"Rs/- is transfered to mobile number "+rmn);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							System.out.println("Server 404");
						}
					}
					else {
						System.out.println("Insufficient Balance");
					}
				}
				else {
					System.out.println("Invalid Receivers Mobile Number");
				}
				
			}
			else {
				System.out.println("Invalid Mobile Number");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkBalance(String accountNumber,String password) {
		String selection = "select * from bank where AccountNumber=? and Password=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(selection);
			preparedStatement.setString(1, accountNumber);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				double amount = resultSet.getDouble("Amount");
				try {
					Thread.sleep(1000);
					System.out.println("Your Account Balance is : "+amount);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}