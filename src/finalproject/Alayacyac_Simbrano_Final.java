package finalproject;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Alayacyac_Simbrano_Final {
	public static int IDNumFinal = 255;// Final ID Number
	public static JFrame frame = new JFrame();//Frame
	public static double Balance = 3000;
	
	public enum TransactionType {//Special Class that can be casted
		DEPOSIT,
		WITHDRAW
	}
	
	/*
	 * IOException is used for catching errors in FileWriter, BufferedWriter and such
	 * 
	 */
	
	public static void main(String[] args) throws IOException{
		int maxcount = 1;
		boolean IdCheck = true, PinCheck = true;
		int ConstantPincode = 14369;//Initializations
		
	try {
			String Popup = "Welcome, User! You are Accessing to an Automated Machine Teller System.";
			JOptionPane.showMessageDialog(frame, Popup , "WELCOME" , JOptionPane.INFORMATION_MESSAGE);
			String IdNum = JOptionPane.showInputDialog("Input ID Number: ");//ID of the user
			int IdNumConv = Integer.parseInt(IdNum);
			
		while(IdNumConv != IDNumFinal) {//Loop til the logical expression will occur
			 JOptionPane.showMessageDialog(frame, "This is not your ID Number!" , "ERROR" , JOptionPane.ERROR_MESSAGE);
             IdNum = JOptionPane.showInputDialog("Input ID number: ");
             IdNumConv = Integer.parseInt(IdNum);
             
             if(maxcount < 3) {//Limiting to 3 times of loop
                 maxcount++;
             }else {
                 JOptionPane.showConfirmDialog(frame, "Access Denied!" , "BLOCKED!" , JOptionPane.CANCEL_OPTION , JOptionPane.ERROR_MESSAGE);
                 IdCheck = false;
                 System.exit(0);
             } 
		}
		
		if(IdCheck != false) {//If the ID is correct 
			JOptionPane.showMessageDialog(frame, "Welcome user! ID: " + IDNumFinal);
		}
			String Input = JOptionPane.showInputDialog("May I know your pincode?");//Pincode of the user
			int pincode = Integer.parseInt(Input);
	
        while(pincode != ConstantPincode) { //Loop til it gets the loggical expression
        	 JOptionPane.showMessageDialog(frame, "This is not your pincode! Please try again."
                     , "BankSaMoro" , JOptionPane.WARNING_MESSAGE);
             Input = JOptionPane.showInputDialog(frame, "Enter your pincode: " , "PINCODE" , JOptionPane.QUESTION_MESSAGE);
             pincode = Integer.parseInt(Input);
             if(maxcount < 3) {//Limiting til 3 times
                 maxcount++;
             }else {
                 JOptionPane.showConfirmDialog(frame, "Access Denied!" , "BLOCKED!" , JOptionPane.CANCEL_OPTION , JOptionPane.ERROR_MESSAGE);
                 PinCheck = false;
                 break;
             }
         }
        
        if(PinCheck != false) {
        	JOptionPane.showMessageDialog(frame, "Welcome to BankSaMoro, ID: " + IDNumFinal + "!", "WELCOME!" , JOptionPane.INFORMATION_MESSAGE);
            options();
         }
			
	}catch (Exception err) {//Catching "Cancel" and "X" Buttons
	     int Confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?" , "EXIT"
                 , JOptionPane.YES_NO_OPTION , JOptionPane.WARNING_MESSAGE);
         if(Confirm == JOptionPane.YES_OPTION) {
             JOptionPane.showMessageDialog(frame, "Thank you, Come back again!");
             System.exit(0);
         }else {
             main(args);
         }
		 }
	}

	public static void options() throws IOException {
		try {
			
			String[] option = new String [] {"Withdraw" , "Deposit" , "Check Balance" , "Check Transactions" , "Exit" };
			String choose = (String) JOptionPane.showInputDialog(frame, "Choose Option: " , "BANKSAMORO" 
					, JOptionPane.PLAIN_MESSAGE , null , option , option[0]);
			//Casting object to string causing input dialog to become dropdown
			
			switch (choose) {
			
			case "Withdraw":
				WithDraw();
				break;
				
			case "Deposit":
				Deposit();
				break;
				
			case "Check Balance":
				CheckBal();
				break;
				
			case "Check Transactions":
				CheckTran();
				break;
				
			case "Exit":
				   int Confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?" , "EXIT"
                           , JOptionPane.YES_NO_OPTION , JOptionPane.WARNING_MESSAGE);
                   if(Confirm == JOptionPane.YES_OPTION) {
                       JOptionPane.showMessageDialog(frame, "Thank you, Come back again!");
                       System.exit(0);
                   }else {
                       options();
                   }
				}
			
		}catch (Exception err) {//if the user wants to cancel or clicked x button amidst options method
			 int Confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?" , "EXIT"
	                    , JOptionPane.YES_NO_OPTION , JOptionPane.WARNING_MESSAGE);
	            if(Confirm == JOptionPane.YES_OPTION) {
	                JOptionPane.showMessageDialog(frame, "Thank you, Come back again!");
	                System.exit(0);
	            }else {
	                options();
	            }
			}
		
		}
	
		public static void WithDraw() throws IOException {
		try {
			boolean checkLoop = true;
			int maxInput = 1;//Initializations
			LocalDate date = LocalDate.now();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");//Format
			String formattedDate = date.format(dateTimeFormatter);//date
			DecimalFormat limit = new DecimalFormat("₱ ###,###.##");
			String input = JOptionPane.showInputDialog("Enter money to be withdrawn: ");
			double withdraw = Double.parseDouble(input);//Input of the user
			
			while(checkLoop) {//Loop if it is true then stops answering "If MaxInput = 2" reaching the limit of withdrawing.
                if(Balance >= withdraw) {
                    Balance -= withdraw;
                    JOptionPane.showConfirmDialog(frame, "Please collect your money!" , "BANKSAMORO"
                            , JOptionPane.OK_OPTION , JOptionPane.INFORMATION_MESSAGE);
                    int message = JOptionPane.showConfirmDialog(frame, "Do you want to withdraw again?"
                            , "BANKSAMORO", JOptionPane.YES_NO_OPTION , JOptionPane.INFORMATION_MESSAGE);
                    if(message != JOptionPane.YES_OPTION) {
                    	 saveTransaction(IDNumFinal, TransactionType.WITHDRAW, limit.format(withdraw), formattedDate);//Saves the withdraw action of the user
                         options();
                         
                    } else {
                    	 saveTransaction(IDNumFinal, TransactionType.WITHDRAW, limit.format(withdraw), formattedDate);
                    	 input = JOptionPane.showInputDialog("Enter money to be withdrawn: ");
             			 withdraw = Double.parseDouble(input);//Input of the user
                    }
                }else {
                    JOptionPane.showConfirmDialog(frame, "Insufficient Balance" , "BANKSAMORO"
                            , JOptionPane.CANCEL_OPTION , JOptionPane.ERROR_MESSAGE);
                    options();
                    
                }if(maxInput < 2) {
                	maxInput++;
                }else {
                	JOptionPane.showConfirmDialog(frame, "You reached the limit of Withdrawing" , "BANKSAMORO"
                            , JOptionPane.CANCEL_OPTION , JOptionPane.ERROR_MESSAGE);
                	checkLoop = false;
                	break;
                }
			}
			options();
			
		}catch (Exception err) {//if the user wants to cancel or clicked x button amidst WithDraw method
				int Confirm = JOptionPane.showConfirmDialog(frame, "Do you want to exit?" , "ERROR OCCURED"
	                    , JOptionPane.YES_NO_OPTION , JOptionPane.ERROR_MESSAGE);
	            if(Confirm == JOptionPane.YES_OPTION) {
	                JOptionPane.showMessageDialog(frame, "Thank you, Come back again!");
	                System.exit(0);
	            }else {
	                options();// goes back to select option
	            }
				}	
		}

		public static void Deposit() throws IOException {
		try {
				//Same procedures
				boolean checkLoop = true;
				int maxInput = 1;
				LocalDate date = LocalDate.now();
	            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	            String formattedDate = date.format(dateTimeFormatter);
	            DecimalFormat limit = new DecimalFormat("₱ ###,###.##");
	            String input = JOptionPane.showInputDialog("Enter money to be deposited: ");
	            double deposit = Double.parseDouble(input);
	            
	    		while(checkLoop) {
	                if(deposit <= 500000) {
	                	Balance += deposit;
	                    JOptionPane.showConfirmDialog(frame, "Your money has been successfully deposited!" , "BANKSAMORO"
	                            , JOptionPane.OK_OPTION , JOptionPane.INFORMATION_MESSAGE);
	                    int message = JOptionPane.showConfirmDialog(frame, "Do you want to deposit again?"
	                            , "BANKSAMORO", JOptionPane.YES_NO_OPTION , JOptionPane.INFORMATION_MESSAGE);
	                    if(message != JOptionPane.YES_OPTION) {
	                    	 saveTransaction(IDNumFinal, TransactionType.DEPOSIT, limit.format(deposit), formattedDate);//Saves the deposit action of the user
	                         options();
	                         
	                    } else {
	                    	 saveTransaction(IDNumFinal, TransactionType.DEPOSIT, limit.format(deposit), formattedDate);
	                    	 input = JOptionPane.showInputDialog("Enter money to deposit: ");
	             			 deposit = Double.parseDouble(input);
	                    }
	                }else {
	                    JOptionPane.showConfirmDialog(frame, "₱500,000 is the limit for you to deposit" , "BANKSAMORO"
	                            , JOptionPane.CANCEL_OPTION , JOptionPane.ERROR_MESSAGE);
	                    options();
	                    
	                }if(maxInput < 2) {
	                	maxInput++;
	                }else {
	                	JOptionPane.showConfirmDialog(frame, "You reached the limit to Deposit!" , "BANKSAMORO"
	                            , JOptionPane.CANCEL_OPTION , JOptionPane.ERROR_MESSAGE);
	                	checkLoop = false;
	                	break;
	                }
				}
				options();// goes back to select option

		}catch (Exception err) {//if the user wants to cancel or clicked x button amidst deposit method
				int Confirm = JOptionPane.showConfirmDialog(frame, "Do you want to exit?" , "ERROR OCCURED"
	                    , JOptionPane.YES_NO_OPTION , JOptionPane.ERROR_MESSAGE);
	            if(Confirm == JOptionPane.YES_OPTION) {
	                JOptionPane.showMessageDialog(frame, "Thank you, Come back again!");
	                System.exit(0);
	            }else {
	               options();// goes back to select option
	            }
			}
		}
		
		public static void CheckBal() throws IOException {
				DecimalFormat limit = new DecimalFormat("₱ ###,###.##");//Pattern to produce
				JOptionPane.showMessageDialog(frame, "Your Balance is  " + limit.format(Balance) 
				, "BankSaMoro" , JOptionPane.INFORMATION_MESSAGE);
				options();	
		}
		
		public static void saveTransaction(int ID, TransactionType type, String amount, String date) throws IOException {
			//Saving user's Withdraw and Deposit actions
			BufferedWriter bw = new BufferedWriter(new FileWriter("Transactions_of_User.txt", true));
			bw.append(IDNumFinal +  " , " + type.toString() + " , " + amount + " , " + date + "\n");// Format input
			bw.close();// Close the bufferedwriter
		}
		
		public static void CheckTran() throws IOException {
			
				BufferedReader br = new BufferedReader(new FileReader("Transactions_of_User.txt"));// Reads the txt file
				String[] column = {"ID", "Transaction Type" , "Amount" , "Date"};
				JTable table = new JTable();// columns in the table
				table.setPreferredSize(new Dimension(500,500));//new dimension of the table
				DefaultTableModel model = (DefaultTableModel) table.getModel();
	            model.setColumnIdentifiers(column);
	            Object [] tableLines = br.lines().toArray();// makes the input in the txt file to array
	            
	        for(int i = 0; i < tableLines.length; i++) {
	        	 String line = tableLines[i].toString().trim();
	             String[] dataRow = line.split(" , ");
	             model.addRow(dataRow);// adds row until all inputs are displayed
	        }
	     
	        JScrollPane scroll = new JScrollPane();//Scroll like structure
            scroll.add(table);
            JOptionPane.showMessageDialog(frame, new JScrollPane(table) , "BANKSAMORO" , JOptionPane.PLAIN_MESSAGE);
            // displays all students
            br.close();
            options();// goes back to select option
				  	
			}
		}
	
