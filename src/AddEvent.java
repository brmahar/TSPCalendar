import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

		addEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				StoreData storeName = new StoreData();
				storeName.setName(addName.getText());
				AddData add = new AddData(storeName);
				add.setVisible(true);
			}
		});

		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 0;
		s.gridy = 0;
		eventPanel.add(addName, s);
		s.gridx = 0;
		s.gridy = 1;
		eventPanel.add(addEvent, s);
		s.gridx = 0;
		s.gridy = 2;
		eventPanel.add(editName, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(editEvent, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(deleteName, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(deleteEvent, s);

		this.add(eventPanel);
		this.setSize(300,400);		
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

}
