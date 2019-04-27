import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class NewAccountWin {
	JTextField namef,addressf;
	JLabel acctypel,genderl,namel,addressl;
	JRadioButton currentb,savingb,maleb,femaleb;
	JButton createaccountb;
	JPanel topp,bottomp;
	JFrame f;

	public NewAccountWin() {
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
		
		f = new JFrame();
		f.setTitle("Create an account");
		
		topp = new JPanel(); 
		bottomp = new JPanel();
		
		currentb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				savingb.setSelected(false);
			}
		});
		
		savingb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentb.setSelected(false);
			}
		});
		
		maleb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				femaleb.setSelected(false);
			}
		});
		
		femaleb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				maleb.setSelected(false);
			}
		});
		
		
	
		createaccountb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAccount();	
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
	
	public void saveAccount() {
		String name = namef.getText();
		String address = addressf.getText();
		String regNo = "";
		DB db = new DB();

		if(currentb.isSelected()){
			regNo+="C";
		}else if(savingb.isSelected()){
			regNo+="S";
		}
		if(maleb.isSelected()){
			regNo+="M";
		}else if(femaleb.isSelected()){
			regNo+="F";
		}
		
		Statement st = db.getStatement();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT MAX(SUBSTRING(acno, 3, 5)) FROM bank");
			if(rs.next()){
				String maxNo = rs.getString(1);
				regNo+=Integer.parseInt(maxNo)+1;
			}
			
			st.executeUpdate("insert into bank values('" + regNo+ "','" + name + "','" + address + "')");
			JOptionPane.showMessageDialog(f, "Account Successfully Created");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

}