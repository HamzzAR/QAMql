import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;


public class Bank {
	DB db;
	JFrame win;
	
	public static void main(String[] args) {
		new Bank(); //call bank
	}
	
	
	public Bank() {
		db = new DB();
		win = new JFrame();
		win.setTitle("Bank Management System");
		
		JMenuBar menubar = new JMenuBar(); //create a menu bar
		
		JMenu mbank = new JMenu("Bank"); //create a menu
	
		JMenuItem maccount = new JMenuItem("Open Account"); //
		JMenuItem mdeposit = new JMenuItem("Deposit Money"); // create menu items
		JMenuItem mwithdraw = new JMenuItem("Withdraw Money"); //
		JMenuItem mbalance = new JMenuItem("Check Balance"); //
		JMenuItem mDeleteAcc = new JMenuItem("Delete Account"); //
		
		maccount.addActionListener(new ActionListener(){    //when maccount button is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewAccountWin(db);                     //then open the NewAccountWin window
			}
			
		});
		
		mdeposit.addActionListener(new ActionListener(){   //when maccount button is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdateAccountWin(db,"deposit");        //then open the UpdateAccountWin window
			}
			
		});
		
		mwithdraw.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdateAccountWin(db,"withdraw");
			}
		});
		
		mbalance.addActionListener(new ActionListener(){    //when check balance buttton is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				checkBalance();                            //then get the current balance of a spacific account
			}
		});
		
		mDeleteAcc.addActionListener(new ActionListener(){    //when check balance buttton is clicked
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					deleteAccount();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		mbank.add(maccount);
		mbank.add(mdeposit);   //add menu items to menu
		mbank.add(mwithdraw);
		mbank.add(mbalance);
		mbank.add(mDeleteAcc);
		
		
		menubar.add(mbank);  //add menu to menuabr
		
		win.setJMenuBar(menubar);  //set window menu ba
		
		win.setLocation(300, 300); //set the window location - where it appears on the screen
		win.setSize(600, 300); // set how big the window is
		win.setVisible(true); // make it visible
	}
	
	public void deleteAccount() throws SQLException {
		ResultSet rs;
		String aNo = JOptionPane.showInputDialog(win,"Please enter your account number");  
    	String sql = "DELETE FROM bank WHERE acno = ?";
    	String sql2 = "DELETE FROM deposit WHERE acno = ?";
    	String sql3 = "DELETE FROM withdraw WHERE acno = ?";
		PreparedStatement upd = db.getConnectObj().prepareStatement(sql);
		PreparedStatement upd2 = db.getConnectObj().prepareStatement(sql2);
		PreparedStatement upd3 = db.getConnectObj().prepareStatement(sql3);
	    upd.setString(1, aNo);
	    upd2.setString(1, aNo);
	    upd3.setString(1, aNo);
		int isDeleted = upd.executeUpdate();
		int isDeleted2 = upd2.executeUpdate();
		int isDeleted3 = upd3.executeUpdate();
		if(isDeleted < 1 && isDeleted2 < 1 && isDeleted3 < 1){
			JOptionPane.showMessageDialog(win, "Account doesn't exist","Error",JOptionPane.ERROR_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(win, "Account successfully deleted","Info",JOptionPane.INFORMATION_MESSAGE);
		}

	}
	
	//ask user for account number
	//call getCurrentBalance() from UpdateAccountWin class to give you the current balance for that account
	public void checkBalance() {
		String aNo = JOptionPane.showInputDialog(win,"Please enter your account number");  
		UpdateAccountWin ua = new UpdateAccountWin(db);
		int balance = ua.getCurrentBalance(aNo,true);
		if(balance != 0){
			JOptionPane.showMessageDialog(win, "Your current balance is: "+balance,"Info",JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(win, "Your current balance is: 0","Info",JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}


}
