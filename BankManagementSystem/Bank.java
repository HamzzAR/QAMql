import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Bank {
	
	public static void main(String[] args) {
		new Bank();
	}
	
	
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
				new NewAccountWin();
			}
			
		});
		
		mdeposit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccountWin("deposit");
			}
			
		});
		
		mwithdraw.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateAccountWin("withdraw");
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
	
	
	public void updateAccountWin(String operation) {
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

}
