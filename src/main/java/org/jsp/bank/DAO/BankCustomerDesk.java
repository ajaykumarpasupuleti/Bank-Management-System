package org.jsp.bank.DAO;


//HelperClass
public class BankCustomerDesk {
	//helperMethod
	public static BankDAO customerHelpDesk() {
		
		BankDAO bankDAO = new BankDAOImpl();
		
		return bankDAO;
	}
}