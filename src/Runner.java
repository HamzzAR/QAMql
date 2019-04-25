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
import java.sql.Statement;

public class Runner implements ActionListener {
	Button save;
	TextField regtf, nametf, markstf;
	Label reglable, namelabel, markslabel, success;

	public Runner() {
		Frame f = new Frame("Insert data into DB");
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		p1.setLayout(new GridLayout(3, 2));

		save = new Button("Save");

		regtf = new TextField(25);
		nametf = new TextField(25);
		markstf = new TextField(25);

		reglable = new Label("RegNo");
		namelabel = new Label("Name");
		markslabel = new Label("Marks");
		success = new Label("");

		save.addActionListener(this);

		p1.add(reglable);
		p1.add(regtf);
		p1.add(namelabel);
		p1.add(nametf);
		p1.add(markslabel);
		p1.add(markstf);

		p2.add(save);

		f.add(p1, BorderLayout.NORTH);
		f.add(p2, BorderLayout.SOUTH);
		f.add(success, BorderLayout.CENTER);

		f.setSize(400, 250);
		f.setVisible(true);

	}

	public static void main(String[] args) {
		new Runner();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (regtf.getText().equals("") || nametf.getText().equals("") || markstf.getText().equals("")) {
			success.setText("One or more fields empty!");
		} else {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/QA", "root", "");
				Statement st = con.createStatement();
				st.executeUpdate("insert into school values(" + Integer.parseInt(regtf.getText()) + ",'"
						+ nametf.getText() + "'," + Integer.parseInt(markstf.getText()) + ")");
				success.setText("Data Successfully Saved!");
				
			}catch (NumberFormatException e) {
				success.setText("You cannot use non-numerical values for RegNo and Marks");
			} 
			
			catch (Exception e) {
				System.out.println(e.toString());
				success.setText("Ops.. Something went wrong buddy. Try again");
			}

		}

	}

}
