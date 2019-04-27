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
		JFrame win = new JFrame();
		win.setTitle("Bank Management System");
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu mbank = new JMenu("Bank");
	
		JMenuItem maccount = new JMenuItem("Open Account");
		JMenuItem mdeposit = new JMenuItem("Deposit Money");
		JMenuItem mwithdraw = new JMenuItem("Withdraw Money");
		JMenuItem mbalance = new JMenuItem("Check Balance");
		
		maccount.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				createAccount();
			}
			
		});
		
		mdeposit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccount("deposit");
			}
			
		});
		
		mwithdraw.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccount("withdraw");
			}
		});
		
		mbank.add(maccount);
		mbank.add(mdeposit);
		mbank.add(mwithdraw);
		mbank.add(mbalance);
		
		menubar.add(mbank);
		
		win.setJMenuBar(menubar);
		
		win.setSize(600, 300);
		win.setVisible(true);
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
					
				}else{
					
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
	
	public void updateAccount(String operation) {
		JLabel accnol,namel,addressl,acctypel,genderl,cbalancel,cball,moneyl;
		JTextField accnof,namef,addressf,acctypef,genderf,newbalf;
		JButton searchb,saveb;
		
		JFrame f = new JFrame();
		
		f.setLayout(new GridLayout(10,3));
		
		accnol = new JLabel("Account No");
		namel = new JLabel("Name");
		addressl = new JLabel("Address");
		acctypel = new JLabel("Account Type");
		genderl = new JLabel("Gender");
		cbalancel = new JLabel("Current Balance");
		cball = new JLabel("100");
		moneyl = new JLabel();
		
		if(operation.equals("deposit")){
			f.setTitle("Deposit Money into Account");
			moneyl.setText("Deposit Money");
		}else{
			f.setTitle("Withdraw Money from Account");
			moneyl.setText("Withdraw Money");
		}
		
		accnof = new JTextField();
		namef = new JTextField();
		addressf = new JTextField();
		acctypef = new JTextField();
		genderf = new JTextField();
		newbalf = new JTextField();
		
		searchb = new JButton("Search");
		saveb = new JButton("Save");
		
		
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
		
		f.setSize(600, 400);
		f.setVisible(true);
		
	}


	public static void main(String[] args) {
		new Bank();
//		try {
//			ResultSet rec;
//			Class.forName("com.mysql.cj.jdbc.Driver"); //load the driver
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/bank","root","012345"); //connect to db
//			Statement st = con.createStatement();
//			rec = st.executeQuery("Select * from bank");
//			if(rec.next()) {
//				System.out.println(rec.getString(1));
//			}
//			
//		
//		}catch (Exception e) {
//			System.out.println(e.toString());
//			System.out.println("Ops.. Something went wrong buddy. Try again");
//		}

	}

}
