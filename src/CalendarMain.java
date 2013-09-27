import javax.swing.*;
import java.awt.event.ActionListener;


/**
 * Calendar
 * 
 * TSP Program 1
 * 
 * @author Brady Mahar
 * @author Austin Browne
 * 
 */
@SuppressWarnings("serial")
public class CalendarMain extends JFrame {
	
	private JTextField addName = new JTextField(10);
	private JButton addEvent = new JButton("Add Event");
	private JTextField editName = new JTextField(10);
	private JButton editEvent = new JButton("Edit Event");
	private JTextField deleteName = new JTextField(10);
	private JButton deleteEvent = new JButton("Delete Event");
	
	CalendarMain(){
		JPanel eventPanel = new JPanel();
		this.add(eventPanel);
		this.setSize(175,250);
		
		eventPanel.add(addName);
		eventPanel.add(addEvent);
		eventPanel.add(editName);
		eventPanel.add(editEvent);
		eventPanel.add(deleteName);
		eventPanel.add(deleteEvent);
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	public String getAddEvent(){
		return addName.getText();
	}
	
	public String getEditEvent(){
		return editName.getText();
	}
	
	public String getDeleteName(){
		return deleteName.getText();
	}
	
	void addAddListener(ActionListener listenForAdd){
		addEvent.addActionListener(listenForAdd);
	}
	
	void addEditListener(ActionListener listenForEdit){
		editEvent.addActionListener(listenForEdit);
	}
	
	void addDeleteListener(ActionListener listenForDelete){
		deleteEvent.addActionListener(listenForDelete);
	}
	
	public static void main(String[] args) {
		CalendarMain theCal = new CalendarMain();
		theCal.setVisible(true);
	}

}
