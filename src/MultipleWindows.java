import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MultipleWindows {
	Frame dataentryf,dataviewf;
	ResultSet rec;
	TextField t1,t2,t3,t4,t5;
	
	public MultipleWindows() {
		Frame f = new Frame();
		Button b1 = new Button("Data Entry");
		Button b2 = new Button("Data View");
		f.setLayout(new GridLayout(2,1));
		
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dataEntryWindow();
				dataentryf.setVisible(true);
			}
			
		});
		
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dataViewWindow();
				dataviewf.setVisible(true);
			}
			
		});
		
		
		f.add(b1); f.add(b2);
		f.setSize(500, 250);
		f.setVisible(true);
		
	}

	public static void main(String[] args) {
		new MultipleWindows();
	}
	
	
	public void dataViewWindow() {
		Button b1,b2;
		Label l1,l2,l3,l4,l5;
		
		t1 = new TextField(10);
		t2 = new TextField(10);
		t3 = new TextField(10);
		t4 = new TextField(10);
		t5 = new TextField(10);

		b1 = new Button("Next");
		b2 = new Button("Previous");
		
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(rec.next()) {
						showRecords();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(rec.previous()) {
						showRecords();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		l1 = new Label("RegoNo");
		l2 = new Label("Name");
		l3 = new Label("Marks");
		l4 = new Label("Percentage");
		l5 = new Label("Results");
		
		dataviewf = new Frame();
		dataviewf = new Frame("View data from DB");
		
		dataviewf.setLayout(new GridLayout(6,2));
		dataviewf.add(l1); dataviewf.add(t1); 
		dataviewf.add(l2); dataviewf.add(t2);
		dataviewf.add(l3); dataviewf.add(t3);
		dataviewf.add(l4); dataviewf.add(t4);
		dataviewf.add(l5); dataviewf.add(t5);
		dataviewf.add(b2); dataviewf.add(b1); 
		
		dataviewf.setSize(400,400);
		dataviewf.setVisible(false);;
 		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //load the driver
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/QA", "root", ""); //connect to db
			Statement st = con.createStatement();
			rec = st.executeQuery("Select * from school");
			System.out.println("Data Successfully Retrived");
			
			if(rec.next()) {
				showRecords();
			}
			
		}catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("Ops.. Something went wrong buddy. Try again");
		}
		
	}
	
	public void showRecords() {
		int regno,marks;
		double per;
		String name,result;
		
		
			try {
				regno = rec.getInt(1);
				marks = rec.getInt(3);
				name = rec.getString(2);
				per = marks*100/150;
				if(per>60) {
					result = "Pass";
				}else{
					result = "Fail";
				}
				
				t1.setText(Integer.toString(regno));
				t2.setText(name);
				t3.setText(Integer.toString(marks));
				t4.setText(Double.toString(per));
				t5.setText(result);
			} catch (Exception e) {
				System.out.println("SQL exception");
				e.toString();
			}
		
	}
	
	public void dataEntryWindow() {
		Button save;
		TextField nametf, markstf;
		Label namelabel, markslabel, success;
		
		dataentryf = new Frame("Insert data into DB");
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.setLayout(new GridLayout(3, 2));

		save = new Button("Save");

		nametf = new TextField(25);
		markstf = new TextField(25);

	
		namelabel = new Label("Name");
		markslabel = new Label("Marks");
		success = new Label("");
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nametf.getText().equals("") || markstf.getText().equals("")) {
					success.setText("One or more fields empty!");
				} else {
					
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost/QA", "root", "");
						Statement st = con.createStatement();
						ResultSet reg = st.executeQuery("select Max(regno)+1 as r from school");
						int regno = 0;
						if(reg.next()) {
							regno = reg.getInt(1);
						}
						
						st.executeUpdate("insert into school values(" + regno + ",'"
								+ nametf.getText() + "'," + Integer.parseInt(markstf.getText()) + ")");
						success.setText("Data Successfully Saved!");
						
					}catch (NumberFormatException e1) {
						success.setText("You cannot use non-numerical values for RegNo and Marks");
					} 
					
					catch (Exception e1) {
						System.out.println(e1.toString());
						success.setText("Ops.. Something went wrong buddy. Try again");
					}

				}
				
			}
			
		});

		p1.add(namelabel);
		p1.add(nametf);
		p1.add(markslabel);
		p1.add(markstf);

		p2.add(save);

		dataentryf.add(p1, BorderLayout.NORTH);
		dataentryf.add(p2, BorderLayout.SOUTH);
		dataentryf.add(success, BorderLayout.CENTER);

		dataentryf.setSize(400, 250);
		dataentryf.setVisible(false);
	}

}
