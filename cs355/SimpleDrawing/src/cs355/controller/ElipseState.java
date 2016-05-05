package cs355.controller;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import cs355.model.drawing.Ellipse;
import cs355.model.drawing.MyModel;

public class ElipseState extends State {

	protected Ellipse currentEllipse;
	protected Point2D.Double firstPoint;
	
	public ElipseState(MyModel model) {
		super(model);
	}

	@Override
	public void mousePress(MouseEvent e) {
		this.firstPoint = new Point2D.Double(e.getX(),e.getY());
		currentEllipse = new Ellipse(color, this.firstPoint,0,0);
		model.addShape(currentEllipse);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		double height = mouseY - this.firstPoint.getY();
		double width = mouseX - this.firstPoint.getX();
		
		Point2D.Double newCenter = new Point2D.Double(this.firstPoint.getX() + (width/2),  this.firstPoint.getY() + (height/2));
		
		currentEllipse.setCenter(newCenter);
		currentEllipse.setHeight(Math.abs(height));
		currentEllipse.setWidth(Math.abs(width));
		
		model.update();
	}

	@Override
	public void mouseRelease(MouseEvent e) {
		this.currentEllipse = null;
		this.firstPoint = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
