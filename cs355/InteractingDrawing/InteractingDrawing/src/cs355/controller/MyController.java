package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import cs355.GUIFunctions;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Shape;

public class MyController implements CS355Controller {

	private MyModel model;
	private State state;
	
	public MyController(MyModel model) {
		this.model = model;
		this.state = new LineState(model);
		this.state.setColor(Color.WHITE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		state.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		state.mousePress(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		state.mouseRelease(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		state.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colorButtonHit(Color c) {
		this.state.setColor(c);
		this.state.changeSelectedShapeColor(c);
		GUIFunctions.changeSelectedColor(c);
	}

	@Override
	public void lineButtonHit() {
		this.state = new LineState(model);
	}

	@Override
	public void squareButtonHit() {
		this.state = new SquareState(model);
		
	}

	@Override
	public void rectangleButtonHit() {
		this.state = new RectangleState(model);
	}

	@Override
	public void circleButtonHit() {
		this.state = new CircleState(model);
	}

	@Override
	public void ellipseButtonHit() {
		this.state = new ElipseState(model);
	}

	@Override
	public void triangleButtonHit() {
		this.state = new TriangleState(model);
	}

	@Override
	public void selectButtonHit() {
		
		this.state = new SelectState(model);
	}

	@Override
	public void zoomInButtonHit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zoomOutButtonHit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hScrollbarChanged(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vScrollbarChanged(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openScene(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggle3DModelDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openImage(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveImage(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggleBackgroundDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDrawing(File file) {
		model.save(file);
		
	}

	@Override
	public void openDrawing(File file) {
		model.open(file);
		
	}

	@Override
	public void doDeleteShape() {
		Shape shape = getSelectedShape();
		if(shape != null) {
			List<Shape> shapeList = model.getShapes();
			for(int i = 0; i < shapeList.size(); i++) {
		
				if(shapeList.get(i).equals(shape))
				model.deleteShape(i);
			}
		}
		state.deleteSelectedShape();
		model.update();
	}

	@Override
	public void doEdgeDetection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSharpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doMedianBlur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUniformBlur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doGrayscale() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doMoveForward() {
		Shape shape = getSelectedShape();
		if(shape != null) {
			List<Shape> shapeList = model.getShapes();
			for(int i = 0; i < shapeList.size(); i++) {
				
				if(shapeList.get(i).equals(shape)) {
					model.moveForward(i);
				}
			}
		}
		model.update();
	}

	@Override
	public void doMoveBackward() {
		Shape shape = getSelectedShape();
		if(shape != null) {
			List<Shape> shapeList = model.getShapes();
			for(int i = 0; i < shapeList.size(); i++) {
				
				if(shapeList.get(i).equals(shape)) {
					model.moveBackward(i);
				}
			}
		}
		model.update();
	}

	@Override
	public void doSendToFront() {
		Shape shape = getSelectedShape();
		if(shape != null) {
			List<Shape> shapeList = model.getShapes();
			for(int i = 0; i < shapeList.size(); i++) {
				
				if(shapeList.get(i).equals(shape)) {
					model.moveToFront(i);
				}
			}
		}
		model.update();
	}

	@Override
	public void doSendtoBack() {
		Shape shape = getSelectedShape();
		if(shape != null) {
			List<Shape> shapeList = model.getShapes();
			for(int i = 0; i < shapeList.size(); i++) {
				
				if(shapeList.get(i).equals(shape)) {
					model.movetoBack(i);
				}
			}
		}
		model.update();
		
	}
	
	public Shape getSelectedShape() {
		return state.getSelectedShape();
	}
	
	public void changeColor(Color c) {
		this.state.setColor(c);
	}

}
