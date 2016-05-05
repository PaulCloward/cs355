package cs355.model.drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs355.view.MyViewRefresher;

public class MyModel extends CS355Drawing {

	private ArrayList<Shape> shapeList;
	
	public MyModel(MyViewRefresher viewRefresher) {
		
		this.addObserver(viewRefresher);
		shapeList = new ArrayList<Shape>();
	}
	
	@Override
	public Shape getShape(int index) {
		
		return shapeList.get(index);
	}

	@Override
	public int addShape(Shape s) {
		
		shapeList.add(0,s);
		this.update();
		return 0;
	}

	@Override
	public void deleteShape(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToFront(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movetoBack(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveForward(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBackward(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Shape> getShapes() {
		// TODO Auto-generated method stub
		return shapeList;
	}

	@Override
	public List<Shape> getShapesReversed() {
		List<Shape> shapesReversed = new ArrayList<Shape>(shapeList);
		Collections.reverse(shapesReversed);
		return shapesReversed;
	}

	@Override
	public void setShapes(List<Shape> shapes) {
		
		this.shapeList = (ArrayList<Shape>) shapes;
		this.update();
	}
	
	public void update() {
		this.setChanged();
		this.notifyObservers();
	}

}
