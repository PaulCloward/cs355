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
	public void mousePress(MouseEvent e) {
		
		Point2D.Double start = new Point2D.Double(e.getX(), e.getY());
		currentLine = new Line(color,start,start);
		model.addShape(currentLine);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		Point2D.Double endPoint = new Point2D.Double(e.getX(), e.getY());
		currentLine.setEnd(endPoint);
		model.update();
	}

	@Override
	public void mouseRelease(MouseEvent e) {
		
		currentLine = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
