import javax.swing.*;

public class JframeGUI {
	
	public JframeGUI() {
		JFrame win = new JFrame();
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("Record");
		
		JMenuItem item1 = new JMenuItem("New");
		JMenuItem item2 = new JMenuItem("Open File");
		JMenuItem item3 = new JMenuItem("Cut");
		JMenuItem item4 = new JMenuItem("Copy");
		

		JOptionPane.showMessageDialog(win, "Press OK now!!!!");
		String name=JOptionPane.showInputDialog(win,"Enter Name");   
		
		   int a = JOptionPane.showConfirmDialog(win,"Are you sure?");  
		   if(a==JOptionPane.YES_OPTION){  
		       win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		   }  
		  
		
		menu1.add(item1);
		menu1.add(item2);
		menu2.add(item3);
		menu2.add(item4);
		
		menubar.add(menu1);
		menubar.add(menu2);
		
		win.setJMenuBar(menubar);
		
		win.setSize(500, 400);
		win.setVisible(true);
	}

	public static void main(String[] args) {
		new JframeGUI();
	}

}
