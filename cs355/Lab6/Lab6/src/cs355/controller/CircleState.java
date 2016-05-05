package cs355.controller;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.MyModel;

public class CircleState extends State {

	protected Circle currentCircle;
	protected Point2D.Double firstPoint;
	
	public CircleState(MyModel model) {
		super(model);
	}

	@Override
	public void mousePress(Point2D.Double e) {
		this.firstPoint = new Point2D.Double(e.getX(),e.getY());
		currentCircle= new Circle(color, this.firstPoint,0);
		model.addShape(currentCircle);
	}

	@Override
	public void mouseDragged(Point2D.Double e) {
		int mouseX = (int)e.getX();
		int mouseY = (int)e.getY();
		
		double height = mouseY - this.firstPoint.getY();
		double width = mouseX - this.firstPoint.getX();
		
		Point2D.Double center;
		double radius = 0.0;
		
		if(Math.abs(height) > Math.abs(width)) {
			radius = (Math.abs(width)/2);
		} else {
			radius = (Math.abs(height)/2);
		}
		
		if(height > 0 && width > 0) {
			
			center = new Point2D.Double(this.firstPoint.getX() + radius, this.firstPoint.getY() + radius);
			
		} else if (height <= 0 && width > 0) {
			
			center = new Point2D.Double(this.firstPoint.getX() + radius, this.firstPoint.getY() - radius);
			
		} else if(height <= 0 && width <= 0) {
			
			center = new Point2D.Double(this.firstPoint.getX() - radius,this.firstPoint.getY() - radius);
			
		} else {
			
			center = new Point2D.Double(this.firstPoint.getX() - radius, this.firstPoint.getY() + radius);
		}
		
		this.currentCircle.setCenter(center);
		this.currentCircle.setRadius(radius);
		
		model.update();
	}

	@Override
	public void mouseRelease(Point2D.Double e) {
		this.currentCircle = null;
		this.firstPoint = null;
	}

	@Override
	public void mouseClicked(Point2D.Double e) {
		// TODO Auto-generated method stub
		
	}

}