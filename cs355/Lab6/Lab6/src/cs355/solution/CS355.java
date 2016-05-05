package cs355.solution;

import java.awt.Color;

import cs355.GUIFunctions;
import cs355.controller.CS355Controller;
import cs355.controller.MyController;
import cs355.model.drawing.MyModel;
import cs355.view.MyViewRefresher;
import cs355.view.ViewRefresher;

/**
 * This is the main class. The program starts here.
 * Make you add code below to initialize your model,
 * view, and controller and give them to the app.
 */
public class CS355 {

	/**
	 * This is where it starts.
	 * @param args = the command line arguments
	 */
	public static void main(String[] args) {

		MyViewRefresher viewRefresher = new MyViewRefresher();
		
		MyModel model = new MyModel(viewRefresher);
		
		MyController controller = new MyController(model);
		
		viewRefresher.setModel(model);
		viewRefresher.setController(controller);
	
		// Fill in the parameters below with your controller and view.
		GUIFunctions.createCS355Frame(controller, viewRefresher);

		GUIFunctions.changeSelectedColor(Color.WHITE);
		GUIFunctions.setHScrollBarKnob((int)(MyController.MAXDISPLAYSIZE/4));
		GUIFunctions.setVScrollBarKnob((int)(MyController.MAXDISPLAYSIZE/4));
		
		GUIFunctions.refresh();
	}
}
