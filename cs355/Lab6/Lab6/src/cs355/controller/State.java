package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import cs355.model.drawing.MyModel;
import cs355.model.drawing.Shape;

public abstract class State {

	protected MyModel model;
	protected static Color color;
	
	public State(MyModel model) {
		this.model = model;
	}
	
	public void setColor(Color color) {
		State.color = color;
	}
	
	public void changeSelectedShapeColor(Color color) {
		return;
	}
	public abstract void mousePress(Point2D.Double e);
	
	public abstract void mouseDragged(Point2D.Double e);
	
	public abstract void mouseRelease(Point2D.Double e);
	
	public abstract void mouseClicked(Point2D.Double e);
	
	public Shape getSelectedShape() {
		return null;
	}
	
	public void deleteSelectedShape() {
		return;
	}

	public void setSelectedShapeColor(Color c) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
