package cs355.controller;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import cs355.model.drawing.Line;
import cs355.model.drawing.MyModel;

public class LineState extends State {

	protected Line currentLine;
	
	public LineState(MyModel model) {
		super(model);
	}

	@Override
	public void mousePress(Point2D.Double e) {
		
		Point2D.Double start = new Point2D.Double(e.getX(), e.getY());
		currentLine = new Line(color,start, new Point2D.Double(e.getX() - start.getX(), e.getY() - start.getY()));
		model.addShape(currentLine);
	}

	@Override
	public void mouseDragged(Point2D.Double e) {
		
		Point2D.Double endPoint = new Point2D.Double(e.getX() - currentLine.getCenter().getX(), e.getY() - currentLine.getCenter().getY());
		currentLine.setEnd(endPoint);
		model.update();
	}

	@Override
	public void mouseRelease(Point2D.Double e) {
		
		currentLine = null;
	}

	@Override
	public void mouseClicked(Point2D.Double e) {
		// TODO Auto-generated method stub
		
	}

}
