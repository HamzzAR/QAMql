import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;


public class Bank {
	
	
	public Bank() {
		createAccount();
//		JFrame win = new JFrame();
//		win.setTitle("Bank Management System");
//		
//		JMenuBar menubar = new JMenuBar();
//		
//		JMenu mbank = new JMenu("Bank");
//	
//		JMenuItem maccount = new JMenuItem("Open Account");
//		JMenuItem mdeposit = new JMenuItem("Deposit Money");
//		JMenuItem mwithdraw = new JMenuItem("Withdraw Money");
//		JMenuItem mbalance = new JMenuItem("Check Balance");
//		
//		mbank.add(maccount);
//		mbank.add(mdeposit);
//		mbank.add(mwithdraw);
//		mbank.add(mbalance);
//		
//		menubar.add(mbank);
//		
//		win.setJMenuBar(menubar);
//		
//		win.setSize(600, 300);
//		win.setVisible(true);
	}
	
	
	public void createAccount() {
		JTextField namef,addressf;
		JLabel acctypel,genderl,namel,addressl;
		JRadioButton currentb,savingb,maleb,femaleb;
		JButton createaccountb;
		JPanel topp,bottomp;
		
		namef = new JTextField();
		addressf = new JTextField();
		acctypel = new JLabel("Account Type");
		genderl = new JLabel("Gender");
		namel = new JLabel("Name");
		addressl = new JLabel("Address");
		currentb = new JRadioButton("Current");
		savingb = new JRadioButton("Saving");
		maleb = new JRadioButton("Male");
		femaleb = new JRadioButton("Female");
		createaccountb = new JButton("Create Account");
		
		JFrame f = new JFrame();
		f.setTitle("Create an account");
		
		topp = new JPanel(); 
		bottomp = new JPanel();
		
		maleb.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(maleb.isSelected()){
					System.out.println("Male selected");
				}else{
					System.out.println("Male not selected");
				}
				
			}
			
		});
		
	
		topp.setLayout(new GridLayout(2,2));
		topp.add(namel); topp.add(namef);
		topp.add(addressl); topp.add(addressf);
		
		bottomp.setLayout(new GridLayout(4,2));
		bottomp.add(acctypel); bottomp.add(genderl);
		bottomp.add(currentb); bottomp.add(maleb);
		bottomp.add(savingb); bottomp.add(femaleb);
		
		
		f.add(topp,BorderLayout.NORTH);
		f.add(bottomp,BorderLayout.CENTER);
		f.add(createaccountb,BorderLayout.SOUTH);
		
		f.setSize(500, 300);
		f.setVisible(true);
		
	}

	public static void main(String[] args) {
		new Bank();
//		try {
//			ResultSet rec;
//			Class.forName("com.mysql.jdbc.Driver"); //load the driver
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", ""); //connect to db
//			Statement st = con.createStatement();
//			rec = st.executeQuery("Select * from bank");
//			System.out.println("Data Successfully Retrived");
//			
//		
//		}catch (Exception e) {
//			System.out.println(e.toString());
//			System.out.println("Ops.. Something went wrong buddy. Try again");
//		}

	}

}
