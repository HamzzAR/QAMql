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
		f.setLayout(new GridLayout(10,3));
		
		accnol = new JLabel("Account No",SwingConstants.CENTER);
		namel = new JLabel("Name",SwingConstants.CENTER);
		addressl = new JLabel("Address",SwingConstants.CENTER);
		acctypel = new JLabel("Account Type",SwingConstants.CENTER);
		genderl = new JLabel("Gender",SwingConstants.CENTER);
		cbalancel = new JLabel("Current Balance",SwingConstants.CENTER);
		cball = new JLabel("");
		moneyl = new JLabel("",SwingConstants.CENTER);

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
		
		searchb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(searchAccount(accnof.getText())){
					JOptionPane.showMessageDialog(f, "Account Found");
				}else{
					JOptionPane.showMessageDialog(f, "Error! Account not found","Error",JOptionPane.ERROR_MESSAGE);
				}
				cball.setText(Integer.toString(getCurrentBalance(accnof.getText())));
			}
		});
		
		saveb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccount();
				cball.setText(Integer.toString(getCurrentBalance(accnof.getText())));
			}
		});
		
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
		
		if(searchAccount(accnof.getText())){
			try {
				int isInserted = 0;
				if(operation.equals("deposit")){
					isInserted = st.executeUpdate("INSERT INTO deposit VALUES ('"+accnof.getText()+"', '"+Integer.parseInt(newbalf.getText())+"', now())");
					JOptionPane.showMessageDialog(f, "Successful Transaction!!");
				}else{
					if(getCurrentBalance(accnof.getText()) >= Integer.parseInt(newbalf.getText())){
						isInserted = st.executeUpdate("INSERT INTO withdraw VALUES ('"+accnof.getText()+"', '"+Integer.parseInt(newbalf.getText())+"', now())");
						JOptionPane.showMessageDialog(f, "Successful Transaction!!");
					}else{
						JOptionPane.showMessageDialog(f, "You DON'T have enough money in your account","Alert",JOptionPane.WARNING_MESSAGE);
					}					
				}
				
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
	
	public int getCurrentBalance(String aNo){
		ResultSet rs,rs2;
		int depositM = 0;
		int withdrawM = 0;
		int currentBalance = 0;
		
		if(searchAccount(aNo)){
			 try {
			    	String sql = "SELECT SUM(amount) FROM deposit WHERE acno = ?";
					String sql2 = "SELECT SUM(amount) FROM withdraw WHERE acno = ?";
			    	PreparedStatement upd = db.getConnectObj().prepareStatement(sql);
			    	PreparedStatement upd2 = db.getConnectObj().prepareStatement(sql2);
					upd.setString(1, aNo);
					upd2.setString(1, aNo);
					rs = upd.executeQuery();
					rs2 = upd2.executeQuery();
					if(rs.next() && rs2.next()){
						depositM = rs.getInt(1);
						withdrawM = rs2.getInt(1);
						currentBalance = depositM - withdrawM;
					
					}else if(rs.next()){
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
					return currentBalance;
				}
		}
	    
	    return currentBalance;
	}
	
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
