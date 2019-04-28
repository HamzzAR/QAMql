import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Bank {
	DB db;
	JFrame win;
	
	public static void main(String[] args) {
		new Bank();
	}
	
	
	public Bank() {
		db = new DB();
		win = new JFrame();
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
				new NewAccountWin(db);
			}
			
		});
		
		mdeposit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdateAccountWin(db,"deposit");
			}
			
		});
		
		mwithdraw.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdateAccountWin(db,"withdraw");
			}
		});
		
		mbalance.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkBalance();
			}
		});
		
		mbank.add(maccount);
		mbank.add(mdeposit);
		mbank.add(mwithdraw);
		mbank.add(mbalance);
		
		menubar.add(mbank);
		
		win.setJMenuBar(menubar);
		
		win.setLocation(300, 300);
		win.setSize(600, 300);
		win.setVisible(true);
	}
	
	public void checkBalance() {
		String aNo = JOptionPane.showInputDialog(win,"Please enter your account number");   
		UpdateAccountWin ua = new UpdateAccountWin(db);
		int balance = ua.getCurrentBalance(aNo);
		if(balance != 0){
			JOptionPane.showMessageDialog(win, "Your current balance is: "+balance,"Info",JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}

}
