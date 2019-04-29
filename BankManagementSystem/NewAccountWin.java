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
	DB db;

	public NewAccountWin(DB d) {
		db = d;
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
				// if any of the fields are empty then don't save account
				if(namef.getText().equals("") || addressf.getText().equals("")
						|| !currentb.isSelected() && !savingb.isSelected() || !maleb.isSelected()
						&& !femaleb.isSelected()){
					JOptionPane.showMessageDialog(f, "You CANNOT leave any field blank!","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					saveAccount();	
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
		
		f.setLocation(300, 320);
		f.setSize(500, 300);
		f.setVisible(true);
		
	}
	
	public void saveAccount() {
		String name = namef.getText();
		String address = addressf.getText();
		String regNo = "";
		
		//add the char to regNo according to what is selected
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
			rs = st.executeQuery("SELECT MAX(SUBSTRING(acno, 3, 5)) FROM bank"); //get the max number from bank from acno last 3 chars 
			if(rs.next()){
				String maxNo = rs.getString(1);
				if(Integer.parseInt(maxNo)>=99){  //if maxNo >= 99 then we dont need to add any 0s before it
					regNo+=Integer.parseInt(maxNo)+1; //because we can only have a 3 digit number
				}else if(Integer.parseInt(maxNo)>=9){  // if its a 2 digit number add 1 zero before it
					regNo+="0"+(Integer.parseInt(maxNo)+1);
				}else{
					regNo+="00"+(Integer.parseInt(maxNo)+1); // if its a 1 digit number add 2 zero before it
				}
			}
			
			st.executeUpdate("insert into bank values('" + regNo+ "','" + name + "','" + address + "')");
			JOptionPane.showMessageDialog(f, "Account Successfully Created: "+regNo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		
			//catch number format exception and add the fist record
		} catch(java.lang.NumberFormatException e){
			try {
				regNo+="001";
				st.executeUpdate("insert into bank values('" + regNo+ "','" + name + "','" + address + "')");
				JOptionPane.showMessageDialog(f, "Account Successfully Created, First Record");
		
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

}
