package cs355.model.drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs355.model.image.CS355Image;
import cs355.view.MyViewRefresher;

public class MyModel extends CS355Drawing {

	private ArrayList<Shape> shapeList;
	private CS355Image image;
	
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
		shapeList.remove(index);
		
	}

	@Override
	public void moveToFront(int index) {
		Shape shape = shapeList.get(index);
		shapeList.remove(index);
		shapeList.add(0, shape);
	}

	@Override
	public void movetoBack(int index) {
		Shape shape = shapeList.get(index);
		shapeList.remove(index);
		shapeList.add(shape);
	}

	@Override
	public void moveForward(int index) {
		Shape shape = shapeList.get(index);
		Shape shape2 = shapeList.get(index - 1);
		shapeList.set(index - 1, shape);
		shapeList.set(index, shape2);
		
	}

	@Override
	public void moveBackward(int index) {
		Shape shape = shapeList.get(index);
		Shape shape2 = shapeList.get(index + 1);
		shapeList.set(index + 1, shape);
		shapeList.set(index, shape2);
		
	}

	@Override
	public List<Shape> getShapes() {
		// TODO Auto-generated method stub
		return shapeList;
	}

	public void setImage(CS355Image image) {
		this.image = image;
	}
	
	public CS355Image getImage() {
		return image;
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

	public int getShapeIndex(Shape selectedShape) {
		// TODO Auto-generated method stub
		return 0;
	}

}
