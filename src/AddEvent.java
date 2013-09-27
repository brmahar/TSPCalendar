import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class AddEvent extends JFrame{

	private JTextField addName = new JTextField(10);
	private JButton addEvent = new JButton("Add Event");
	private JTextField editName = new JTextField(10);
	private JButton editEvent = new JButton("Edit Event");
	private JTextField deleteName = new JTextField(10);
	private JButton deleteEvent = new JButton("Delete Event");

	AddEvent(){
		JPanel eventPanel = new JPanel();
		this.add(eventPanel);
		this.setSize(150,250);

		addEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				AddData add = new AddData();
				add.setVisible(true);
			}
		});
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

	public static void Main(String[] args){

	}
}
