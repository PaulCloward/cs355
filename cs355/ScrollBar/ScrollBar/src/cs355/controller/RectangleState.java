package cs355.controller;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import cs355.model.drawing.Line;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Rectangle;

public class RectangleState extends State {

	protected Point2D.Double firstPoint;
	protected Rectangle currentRectangle;
	
	public RectangleState(MyModel model) {
		super(model);
	}

	@Override
	public void mousePress(Point2D.Double e) {
		
		this.firstPoint = new Point2D.Double(e.getX(),e.getY());
		currentRectangle = new Rectangle(color,this.firstPoint,0,0);
		model.addShape(currentRectangle);
	}

	@Override
	public void mouseDragged(Point2D.Double e) {
		int mouseX = (int)e.getX();
		int mouseY = (int)e.getY();
		
		double height = mouseY - this.firstPoint.getY();
		double width = mouseX - this.firstPoint.getX();
		
		Point2D.Double newUpperLeft;
		
		if(height > 0 && width > 0) {
			
			newUpperLeft = firstPoint;
		} else if (height <= 0 && width > 0) {
			
			newUpperLeft = new Point2D.Double(this.firstPoint.getX(), this.firstPoint.getY() + height);
			
		} else if(height <= 0 && width <= 0) {
			
			newUpperLeft = new Point2D.Double(mouseX,mouseY);
		} else {
			
			newUpperLeft = new Point2D.Double(this.firstPoint.getX() + width, this.firstPoint.getY());
		}
		
		Point2D.Double center = new Point2D.Double(newUpperLeft.getX() + width/2, newUpperLeft.getY() + height/2);
		this.currentRectangle.setCenter(center);
		this.currentRectangle.setHeight(Math.abs(height));
		this.currentRectangle.setWidth(Math.abs(width));
		
		model.update();
	}

	@Override
	public void mouseRelease(Point2D.Double e) {
		
		this.firstPoint = null;
		this.currentRectangle = null;
	}

	@Override
	public void mouseClicked(Point2D.Double e) {
		// TODO Auto-generated method stub
		
	}

}
