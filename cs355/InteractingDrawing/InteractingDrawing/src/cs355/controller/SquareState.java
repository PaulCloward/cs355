package cs355.controller;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import cs355.model.drawing.Line;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Square;

public class SquareState extends State {

	protected Point2D.Double firstPoint;
	protected Square currentSquare;
	
	public SquareState(MyModel model) {
		super(model);
	}

	@Override
	public void mousePress(MouseEvent e) {
		
		this.firstPoint = new Point2D.Double(e.getX(),e.getY());
		currentSquare = new Square(color,this.firstPoint,0);
		model.addShape(currentSquare);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		double height = mouseY - this.firstPoint.getY();
		double width = mouseX - this.firstPoint.getX();
		
		Point2D.Double newUpperLeft;
		Double sideLength = 0.0;
		
		if(Math.abs(height) > Math.abs(width)) {
			sideLength = Math.abs(width);
		} else {
			sideLength = Math.abs(height);
		}
		
		
		if(height > 0 && width > 0) {
			
			newUpperLeft = firstPoint;
		} else if (height <= 0 && width > 0) {
			
			newUpperLeft = new Point2D.Double(this.firstPoint.getX(), this.firstPoint.getY() -sideLength);
			
		} else if(height <= 0 && width <= 0) {
			
			newUpperLeft = new Point2D.Double(this.firstPoint.getX() -sideLength,this.firstPoint.getY() -sideLength);
		} else {
			
			newUpperLeft = new Point2D.Double(this.firstPoint.getX() -sideLength, this.firstPoint.getY());
		}
		
		Point2D.Double center = new Point2D.Double(newUpperLeft.getX() + sideLength/2, newUpperLeft.getY() + sideLength/2);
		
		this.currentSquare.setCenter(center);
		this.currentSquare.setSize(Math.abs(sideLength));
		
		model.update();
	}

	@Override
	public void mouseRelease(MouseEvent e) {
		
		this.firstPoint = null;
		this.currentSquare = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
