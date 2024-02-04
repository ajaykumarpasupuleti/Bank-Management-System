package org.jsp.bank;

import java.util.Scanner;

import org.jsp.bank.DAO.BankCustomerDesk;
import org.jsp.bank.DAO.BankDAO;
import org.jsp.bank.model.Bank;

/**
 * Hello world!
 *
 */
public class App 
{	
    public static void main(String[] args )
    {
    	BankDAO bankDAO = BankCustomerDesk.customerHelpDesk();
    	
    	System.out.println("Enter "
    			+ "\n1.For Registration "
    			+ "\n2.For Credit \n3.For Debit "
    			+ "\n4.For Changing The Password "
    			+ "\n5.For Mobile To Mobile Transaction "
    			+ "\n6.For Check Balance ");
    	Scanner scanner = new Scanner(System.in);
    	int response = scanner.nextInt();
    	
    	String firstName,lastName, mobileNumber, password, address, accountNumber;
    	double amount;
    	switch (response) {
		case 1:
			//Firstly we have stored the values in the bank class object
			//So we have chosen the constructor with argument
			//Because it is mandatory to give all the details of the user
			
			System.out.print("Enter Your First Name : ");
			firstName = scanner.next();
			System.out.print("Enter Your Last Name : ");
			lastName = scanner.next();
			System.out.print("Enter Your Mobile Number : ");
			mobileNumber = scanner.next();
			while(!isValidMobileNumber(mobileNumber)) {
				System.out.print("Your mobile number should consists of 10 digits only\n");
				System.out.print("Please Re-Enter Your Mobile Number : ");
				mobileNumber = scanner.next();
			}
						
			System.out.print("Enter Your Password : ");
			password = scanner.next();
			while(!isValidPassword(password)) {
				System.out.print("Your password should consists of 5 digits only\n");
				System.out.print("Please Re-Enter Your Password : ");
				password = scanner.next();
			}
			System.out.print("Enter Your Amount : ");
			amount = scanner.nextDouble();
			while(!isValidAmount(amount)) {
				System.out.print("Amount should be positive\n");
				System.out.print("Please Re-Enter Your Amount : ");
				amount = scanner.nextDouble();
			}
			System.out.print("Enter Your Address : ");
			address = scanner.next();
			
			Bank bank = new Bank(0, firstName, lastName, mobileNumber, "0", password, amount, address, "0");
			bankDAO.userRegistration(bank);
			break;
			
		case 2:
			System.out.print("Enter Your Account Number : ");
			accountNumber = scanner.next();
			while(!isValidAccountNumber(accountNumber)) {
				System.out.print("Invalid Account Number\n");
				System.out.print("Please Re-Enter Your 11 digits Account Number : ");
				accountNumber = scanner.next();
			}
			bankDAO.credit(accountNumber);
			break;
			
		case 3:
			System.out.print("Enter Your Account Number : ");
			accountNumber = scanner.next();
			while(!isValidAccountNumber(accountNumber)) {
				System.out.print("Invalid Account Number\n");
				System.out.print("Please Re-Enter Your 11 digits Account Number : ");
				accountNumber = scanner.next();
			}
			
			System.out.print("Enter Your Password : ");
			password = scanner.next();
			while(!isValidPassword(password)) {
				System.out.print("Invalid Password\n");
				System.out.print("Please Re-Enter Your 5 digits Password : ");
				password = scanner.next();
			}
			bankDAO.debit(accountNumber, password);
			break;
			
		case 4:
			System.out.print("Enter Your Account Number : ");
			accountNumber = scanner.next();
			while(!isValidAccountNumber(accountNumber)) {
				System.out.print("Invalid Account Number\n");
				System.out.print("Please Re-Enter Your 11 digits Account Number : ");
				accountNumber = scanner.next();
			}
			
			System.out.print("Enter Your Password : ");
			password = scanner.next();
			while(!isValidPassword(password)) {
				System.out.print("Invalid Password\n");
				System.out.print("Please Re-Enter Your 5 digits Password : ");
				password = scanner.next();
			}
			bankDAO.changingThePassword(accountNumber, password);
			break;
		case 5:
			System.out.print("Enter Your Mobile Number : ");
			mobileNumber = scanner.next();
			while(!isValidMobileNumber(mobileNumber)) {
				System.out.print("Your mobile number should consists of 10 digits only\n");
				System.out.print("Please Re-Enter Your Mobile Number : ");
				mobileNumber = scanner.next();
			}
			bankDAO.mobileToMobileTransaction(mobileNumber);
			break;
		case 6:
			System.out.print("Enter Your Account Number : ");
			accountNumber = scanner.next();
			while(!isValidAccountNumber(accountNumber)) {
				System.out.print("Invalid Account Number\n");
				System.out.print("Please Re-Enter Your 11 digits Account Number : ");
				accountNumber = scanner.next();
			}
			
			System.out.print("Enter Your Password : ");
			password = scanner.next();
			while(!isValidPassword(password)) {
				System.out.print("Invalid Password\n");
				System.out.print("Please Re-Enter Your 5 digits Password : ");
				password = scanner.next();
			}
			bankDAO.checkBalance(accountNumber, password);
			break;

		default:
			break;
		}
    }
    public static boolean isValidName(String name) {
    	for(int i=0;i<name.length();i++) {
    		char ch = name.charAt(i);
			if(!Character.isAlphabetic(ch)) {
				return false;
			}
    	}
    	return true;
    }
    
    public static boolean isValidMobileNumber(String mobileNumber) {
		if(mobileNumber.length()!=10) {
			return false;
		}
		for(int i=0;i<mobileNumber.length();i++) {
			char m = mobileNumber.charAt(i);
			if(!Character.isDigit(m)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isValidPassword(String password) {
		if(password.length()!=5) {
			return false;
		}
		for(int i=0;i<password.length();i++) {
			char ch = password.charAt(i);
			if(!Character.isDigit(ch)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isValidAmount(double amount) {
		return amount>0;
	}
	
	public static boolean isValidAccountNumber(String accountNumber) {
		if(accountNumber.length()!=11) {
			return false;
		}
		for(int i=0;i<accountNumber.length();i++) {
			char m = accountNumber.charAt(i);
			if(!Character.isDigit(m)) {
				return false;
			}
		}
		return true;
	}
}