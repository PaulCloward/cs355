package cs355.controller;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import cs355.model.drawing.MyModel;
import cs355.model.drawing.Triangle;

public class TriangleState extends State {

	protected Triangle currentTriangle;
	protected ArrayList<Point2D.Double> points;
	public TriangleState(MyModel model) {
		super(model);
		points = new ArrayList<>();
	}

	@Override
	public void mousePress(MouseEvent e) {
		
	/*	int size = points.size();
		Point2D.Double trianglePoint;
		trianglePoint = new Point2D.Double(e.getX(),e.getY());
		
		if(size < 2) {

			points.add(trianglePoint);
		} else {
			
			currentTriangle = new Triangle(color, points.get(0), points.get(1), trianglePoint);
			model.addShape(currentTriangle);
			points = new ArrayList<>();
		}*/
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	
		
	}

	@Override
	public void mouseRelease(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int size = points.size();
		Point2D.Double trianglePoint;
		trianglePoint = new Point2D.Double(e.getX(),e.getY());
		
		if(size < 2) {

			points.add(trianglePoint);
		} else {
			
			currentTriangle = new Triangle(color, points.get(0), points.get(1), trianglePoint);
			model.addShape(currentTriangle);
			points = new ArrayList<>();
		}
		
		
	}

}