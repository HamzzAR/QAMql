import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class UpdateAccountWin {
	JLabel accnol,namel,addressl,acctypel,genderl,cbalancel,cball,moneyl;
	JTextField accnof,namef,addressf,acctypef,genderf,newbalf;
	JButton searchb,saveb;
	JFrame f;
	DB db;
	boolean found;
	String operation;
	int currentBalance;
	
	//constructor is overloaded as this one is being used for a different purpose from the Bank class
	public UpdateAccountWin(DB d){
		db = d;
		namel = new JLabel("Name",SwingConstants.CENTER);
		addressl = new JLabel("Address",SwingConstants.CENTER);
		acctypel = new JLabel("Account Type",SwingConstants.CENTER);
		genderl = new JLabel("Gender",SwingConstants.CENTER);
	}

	public UpdateAccountWin(DB d, String oper) {
		db = d;
		operation = oper;
		f = new JFrame();
		f.setLayout(new GridLayout(10,3)); //set the layout for the Window to grid layout 10 by 3
		
		//Initialise the labels
		accnol = new JLabel("Account No",SwingConstants.CENTER);
		namel = new JLabel("Name",SwingConstants.CENTER);
		addressl = new JLabel("Address",SwingConstants.CENTER);
		acctypel = new JLabel("Account Type",SwingConstants.CENTER);
		genderl = new JLabel("Gender",SwingConstants.CENTER);
		cbalancel = new JLabel("Current Balance",SwingConstants.CENTER);
		cball = new JLabel("");
		moneyl = new JLabel("",SwingConstants.CENTER);
		
		//if user has clicked the deposit button 
		//then set the window title accordingly etc.
		if(operation.equals("deposit")){
			f.setTitle("Deposit Money into Account");
			moneyl.setText("Deposit Money");
		}else{
			f.setTitle("Withdraw Money from Account");
			moneyl.setText("Withdraw Money");
		}
		
		//Initialise the Text fields
		accnof = new JTextField();
		namef = new JTextField();
		addressf = new JTextField();
		acctypef = new JTextField();
		genderf = new JTextField();
		newbalf = new JTextField();
		
		searchb = new JButton("Search");
		saveb = new JButton("Save");
		
		//if the search button is clicked
		//search for the account and extract details for that account
		searchb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(searchAccount(accnof.getText())){
					JOptionPane.showMessageDialog(f, "Account Found");
				}else{
					JOptionPane.showMessageDialog(f, "Error! Account not found","Error",JOptionPane.ERROR_MESSAGE);
				}
				cball.setText(Integer.toString(getCurrentBalance(accnof.getText(),false))); //set the current balance label with the current balance from user account
			}
		});
		
		//if the save button is clicked
		//call the updateAccount to save data into 
		//either withdraw table or deposit table
		saveb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccount();
				cball.setText(Integer.toString(getCurrentBalance(accnof.getText(),false)));
			}
		});
		
		//add the components to the window
		f.add(accnol); f.add(accnof); f.add(searchb);
		f.add(namel); f.add(namef); f.add(new JLabel(""));
		f.add(addressl); f.add(addressf); f.add(new JLabel(""));
		f.add(acctypel); f.add(acctypef); f.add(new JLabel(""));
		f.add(genderl); f.add(genderf); f.add(new JLabel(""));
		f.add(new JLabel("")); f.add(new JLabel("")); f.add(new JLabel(""));
		f.add(new JLabel("")); f.add(cbalancel); f.add(cball);
		f.add(new JLabel("")); f.add(moneyl); f.add(newbalf);
		f.add(new JLabel("")); f.add(new JLabel("")); f.add(new JLabel(""));
		f.add(new JLabel("")); f.add(saveb);
		
		f.setLocation(300, 320);
		f.setSize(600, 400);
		f.setVisible(true);
		
	}
	
	public void updateAccount(){
		Statement st = db.getStatement();
		
		if(searchAccount(accnof.getText())){  //search for the user account before doing any calculations
			try {
				int isInserted = 0;
				if(operation.equals("deposit")){
					//insert date into deposit table
					isInserted = st.executeUpdate("INSERT INTO deposit VALUES ('"+accnof.getText()+"', '"+Integer.parseInt(newbalf.getText())+"', now())");
					JOptionPane.showMessageDialog(f, "Successful Transaction!!");
				}else{
					if(getCurrentBalance(accnof.getText(),false) >= Integer.parseInt(newbalf.getText())){ //only allow user to withdraw if he has enough money in his account
						//insert date into withdraw table
						isInserted = st.executeUpdate("INSERT INTO withdraw VALUES ('"+accnof.getText()+"', '"+Integer.parseInt(newbalf.getText())+"', now())");
						JOptionPane.showMessageDialog(f, "Successful Transaction!!");
					}else{
						JOptionPane.showMessageDialog(f, "You DON'T have enough money in your account","Alert",JOptionPane.WARNING_MESSAGE);
					}					
				}
				
				//if data inserted then show an appropriate message to the user
				if(isInserted < 0 && operation.equals("deposit")){
					JOptionPane.showMessageDialog(f, "Unsuccessful Deposit Transaction!!","Error",JOptionPane.ERROR_MESSAGE);
				}else if(isInserted < 0 && operation.equals("withdraw")){
					JOptionPane.showMessageDialog(f, "Unsuccessful Withdraw Transaction!!","Error",JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//get the current balance for the aNo account
	public int getCurrentBalance(String aNo, boolean thiss){
		currentBalance = 0;
		if(thiss){
		    try {
		    	ResultSet rs;
		    	String sql = "SELECT * FROM bank WHERE acno = ?";
				PreparedStatement upd = db.getConnectObj().prepareStatement(sql);
			    upd.setString(1, aNo);
				rs = upd.executeQuery();
				if(rs.next()){           //check if the account exists before getting the balance
					getBalance(aNo);  // get the balance
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(searchAccount(aNo)){
				 getBalance(aNo);
			}
		}
	    
	    return currentBalance;
	}
	
	public void getBalance(String aNo){
		ResultSet rs,rs2;
		int depositM = 0;
		int withdrawM = 0;
		try {
	    	String sql = "SELECT SUM(amount) FROM deposit WHERE acno = ?"; //get the sum of all the amount records in the db for aNo account
			String sql2 = "SELECT SUM(amount) FROM withdraw WHERE acno = ?"; // same here from withdraw tabel
	    	PreparedStatement upd = db.getConnectObj().prepareStatement(sql);
	    	PreparedStatement upd2 = db.getConnectObj().prepareStatement(sql2);
			upd.setString(1, aNo);
			upd2.setString(1, aNo);
			rs = upd.executeQuery();
			rs2 = upd2.executeQuery();
			if(rs.next() && rs2.next()){ //if both tables return something
				depositM = rs.getInt(1);
				withdrawM = rs2.getInt(1);
				currentBalance = depositM - withdrawM;
			
			}else if(rs.next()){  // if deposit table returns something
				depositM = rs.getInt(1);
				currentBalance = depositM;
			}else{
				JOptionPane.showMessageDialog(f, "Account has 0 withdraws","Alert",JOptionPane.WARNING_MESSAGE);
				System.out.println("in else "+currentBalance);
			}
			
		}
	    catch (Exception e) {
			JOptionPane.showMessageDialog(f, "Exception! Something went wrong","Alert",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			System.out.println("in Exception "+currentBalance);
		}
	}
	
	//search for aNo account
	public boolean searchAccount(String aNo) {
		found = false;
		ResultSet rs;
		try {
			String sql = "SELECT * FROM bank WHERE acno = ?";
			PreparedStatement upd = db.getConnectObj().prepareStatement(sql);
		    upd.setString(1, aNo);
		    rs = upd.executeQuery();
			if(rs.next()){
				namef.setText(rs.getString(2));
				addressf.setText(rs.getString(3));
				if(rs.getString(1).substring(0,1).equals("C")){
					acctypef.setText("Current");
				} else if(rs.getString(1).substring(0,1).equals("S")){
					acctypef.setText("Saving");
				}

				if(rs.getString(1).substring(1,2).equals("M")){
					genderf.setText("Male");
				} else if(rs.getString(1).substring(1,2).equals("F")){
					genderf.setText("Female");
				}
				found = true;
			}else{
				found = false;
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return found;

	}

}
