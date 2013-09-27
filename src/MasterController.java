import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

<<<<<<< HEAD
=======


>>>>>>> 5fc0f05b63b3a2ab42042d4dd55e23887dd091bf
public class MasterController {
	
	private AddEvent theAdd;
	
	public MasterController(AddEvent theAdder){
		this.theAdd = theAdder;
		//this.theAdd.addAddListener(new AddListener());
	}
	
	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			AddData deats = new AddData();
			deats.setVisible(true);
			
			
		}
	}
}
