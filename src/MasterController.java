import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sun.tools.tree.ThisExpression;


public class MasterController {
	
	private AddEvent theAdd;
	
	public MasterController(AddEvent theAdder){
		this.theAdd = theAdder;
		this.theAdd.addAddListener(new AddListener());
	}
	
	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			AddData deats = new AddData();
			deats.setVisible(true);
			
			
		}
	}
}
