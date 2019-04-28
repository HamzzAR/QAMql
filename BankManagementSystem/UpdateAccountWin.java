import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class UpdateAccountWin {
	JLabel accnol,namel,addressl,acctypel,genderl,cbalancel,cball,moneyl;
	JTextField accnof,namef,addressf,acctypef,genderf,newbalf;
	JButton searchb,saveb;
	JFrame f;
	DB db;
	boolean found;
	String operation;

	public UpdateAccountWin(DB d, String oper) {
		db = d;
		operation = oper;
		f = new JFrame();
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
		
		searchb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				searchAccount();
			}
		});
		
		saveb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(operation.equals("deposit")){
					depositMoney();
				}
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
	
	public void depositMoney(){
		Statement st = db.getStatement();
		
		searchAccount();
		if(found == true){
			try {
				int isInserted = st.executeUpdate("INSERT INTO deposit VALUES ('"+accnof.getText()+"', '"+Integer.parseInt(newbalf.getText())+"', now())");
				if(isInserted <= 0){
					JOptionPane.showMessageDialog(f, "Unsuccessful Transaction!!");
				}else{
					JOptionPane.showMessageDialog(f, "Successful Transaction");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void searchAccount() {
		found = false;
		ResultSet rs;
		try {
			String sql = "SELECT * FROM bank WHERE acno = ?";
			PreparedStatement upd = db.getConnectObj().prepareStatement(sql);
		    upd.setString(1, accnof.getText());
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
				JOptionPane.showMessageDialog(f, "Found details for account "+rs.getString(1));
				found = true;
			}else{
				found = false;
				JOptionPane.showMessageDialog(f, "Error! Account not found");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		

	}

}
