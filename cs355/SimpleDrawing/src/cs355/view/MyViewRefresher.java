package cs355.view;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Shape;

public class MyViewRefresher implements ViewRefresher {

	private MyModel model;
	
	@Override
	public void update(Observable o, Object arg) {
		
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		
		List<Shape> refreshShapes = model.getShapesReversed();
		for(Shape shape : refreshShapes) {
			DrawShape.drawShapes(shape, g2d);
		}
	}
	
	public void setModel(MyModel model) {
		this.model = model;
	}

}
