import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MainInterface extends JFrame{

	private JLabel title = new JLabel("Event Panel");
	private JTextField addName = new JTextField(10);
	private JButton addEvent = new JButton("Add Event");
	private JTextField editName = new JTextField(10);
	private JButton editEvent = new JButton("Edit Event");
	private JTextField deleteName = new JTextField(10);
	private JButton deleteEvent = new JButton("Delete Event");
	private JTextField viewName = new JTextField(10);
	private JButton viewEvent = new JButton("View Event");

	MainInterface(){

		addEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				StoreData storeName = new StoreData();
				storeName.setName(addName.getText());
				AddEvent add = new AddEvent(storeName);
				add.setVisible(true);
			}
		});
		
		viewEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				StoreData pullViewData = new StoreData();
				pullViewData.setName(viewName.getText());
				SendToDB storeDB = new SendToDB();
				storeDB.runStore(pullViewData, 1);

				ViewEvent view = new ViewEvent(pullViewData);
				view.setVisible(true);
			}
		});
		
		editEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				StoreData storeName = new StoreData();
				storeName.setName(editName.getText());
				SendToDB editDB = new SendToDB();
				editDB.runStore(storeName, 1);
				
				EditEvent edit = new EditEvent(storeName);
				edit.setVisible(true);
			}
		});
		
		deleteEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				StoreData storeName = new StoreData();
				storeName.setName(deleteName.getText());
				
			}
		});
		
		title.setFont(new Font("Serif", Font.PLAIN, 22));
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 0;
		s.gridy = 0;
		eventPanel.add(title, s);
		s.gridx = 0;
		s.gridy = 1;
		eventPanel.add(addName, s);
		s.gridx = 0;
		s.gridy = 2;
		eventPanel.add(addEvent, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(editName, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(editEvent, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(deleteName, s);
		s.gridx = 0;
		s.gridy = 6;
		eventPanel.add(deleteEvent, s);
		s.gridx = 0;
		s.gridy = 7;
		eventPanel.add(viewName, s);
		s.gridx = 0;
		s.gridy = 8;
		eventPanel.add(viewEvent, s);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
