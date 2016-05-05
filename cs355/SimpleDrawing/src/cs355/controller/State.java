package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import cs355.model.drawing.MyModel;

public abstract class State {

	protected MyModel model;
	protected static Color color;
	
	public State(MyModel model) {
		this.model = model;
	}
	
	public void setColor(Color color) {
		State.color = color;
	}
	
	public abstract void mousePress(MouseEvent e);
	
	public abstract void mouseDragged(MouseEvent e);
	
	public abstract void mouseRelease(MouseEvent e);
	
	public abstract void mouseClicked(MouseEvent e);
	
	
	
	
	
}
