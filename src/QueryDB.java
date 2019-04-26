import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryDB implements ActionListener{
	Frame f;
	ResultSet rec;
	Button b1,b2;
	TextField t1,t2,t3,t4,t5;
	Label l1,l2,l3,l4,l5;
	
	public QueryDB() {
		t1 = new TextField(10);
		t2 = new TextField(10);
		t3 = new TextField(10);
		t4 = new TextField(10);
		t5 = new TextField(10);

		b1 = new Button("Next");
		b2 = new Button("Previous");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		l1 = new Label("RegoNo");
		l2 = new Label("Name");
		l3 = new Label("Marks");
		l4 = new Label("Percentage");
		l5 = new Label("Results");
		
		f = new Frame();
		f = new Frame("View data from DB");
		
		f.setLayout(new GridLayout(6,2));
		f.add(l1); f.add(t1); 
		f.add(l2); f.add(t2);
		f.add(l3); f.add(t3);
		f.add(l4); f.add(t4);
		f.add(l5); f.add(t5);
		f.add(b2); f.add(b1); 
		
		f.setSize(400,400);
		f.setVisible(true);;
 		
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

	public static void main(String[] args) {
		new QueryDB();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Button b = (Button) e.getSource();

		try {
		if(b == b1) {
			if(rec.next()) {
				showRecords();
			}
		} else if(b == b2) {
			if(rec.previous()) {
				showRecords();
			}
		}

		} catch (SQLException s) {
			s.printStackTrace();
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

}
