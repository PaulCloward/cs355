package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import cs355.GUIFunctions;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Shape;
import cs355.view.MyAffineTransform;

public class MyController implements CS355Controller {

	public static final double MAXDISPLAYSIZE = 2048;
	public static final double MINZOOM = 0.25;
	private MyModel model;
	private State state;
	
	private double zoomFactor = 1;
	private int hScroll = 0;
	private int vScroll = 0;
	
	
	public MyController(MyModel model) {
		this.model = model;
		this.state = new LineState(model);
		this.state.setColor(Color.WHITE);
	}
	
	
	public Point2D.Double getScrollValues() {
		return new Point2D.Double(hScroll, vScroll);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D.Double worldPoint = viewToWorld(e);
		state.mouseClicked(worldPoint);
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		Point2D.Double worldPoint = viewToWorld(e);
		state.mousePress(worldPoint);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point2D.Double worldPoint = viewToWorld(e);
		state.mouseRelease(worldPoint);
		
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
		Point2D.Double worldPoint = viewToWorld(e);
		state.mouseDragged(worldPoint);
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

	public void updateZoom(boolean zoomIn) {

		int portionOfScrollBar = (int)(zoomFactor/MINZOOM);
		int scrollBarSize = (int)(MAXDISPLAYSIZE/portionOfScrollBar);
		
		if(zoomIn) {
			if((hScroll + scrollBarSize/2) >= MAXDISPLAYSIZE-scrollBarSize*2) {
				GUIFunctions.setHScrollBarPosit(0);
				GUIFunctions.setHScrollBarKnob(scrollBarSize);
				hScroll = (int)(MAXDISPLAYSIZE-scrollBarSize*(1.5));
			} else {
				hScroll += scrollBarSize/2;
			}
			
			if((vScroll + scrollBarSize/2) >= MAXDISPLAYSIZE-scrollBarSize*2) {
				GUIFunctions.setVScrollBarPosit(0);
				GUIFunctions.setVScrollBarKnob(scrollBarSize);
				vScroll = (int)(MAXDISPLAYSIZE-scrollBarSize*(1.5));
			} else {
				vScroll += scrollBarSize/2;
			}
				
		} else {
			if(hScroll > MyController.MAXDISPLAYSIZE - scrollBarSize) {
				hScroll = (int)MyController.MAXDISPLAYSIZE - scrollBarSize;
			}
		 if(hScroll < scrollBarSize/4) {
				hScroll = 0;
			} 
		 	else {
				hScroll -= (scrollBarSize/4);
			}
		 
		 if(vScroll > MyController.MAXDISPLAYSIZE - scrollBarSize) {
				vScroll = (int)MyController.MAXDISPLAYSIZE - scrollBarSize;
			}
		 if(vScroll < scrollBarSize/4) {
				vScroll = 0;
			} else {
				vScroll -= (scrollBarSize/4);
			}
		 
		}
		
		
		
		GUIFunctions.setHScrollBarPosit(hScroll);
		GUIFunctions.setVScrollBarPosit(vScroll);
		
		GUIFunctions.setHScrollBarKnob(scrollBarSize);
		GUIFunctions.setVScrollBarKnob(scrollBarSize);
		
		GUIFunctions.setZoomText(zoomFactor);
	
	}
	
	@Override
	public void zoomInButtonHit() {
		if(zoomFactor < 4) {
			zoomFactor *= 2;
			updateZoom(true);
		}
		System.out.println("Zoom Factor: " + zoomFactor);
		model.update();
	}

	@Override
	public void zoomOutButtonHit() {
		
		if(zoomFactor > .25) {
			zoomFactor *= .5;
			updateZoom(false);
		}
		System.out.println("Zoom Factor: " + zoomFactor);
		model.update();
	}

	@Override
	public void hScrollbarChanged(int value) {
		System.out.println(value);
		hScroll = value;
		model.update();
	}

	@Override
	public void vScrollbarChanged(int value) {
		System.out.println(value);
		vScroll = value;
		model.update();
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
	
	public double getZoomFactor() {
		return zoomFactor;
	}
	
	private Point2D.Double viewToWorld(MouseEvent e) {
		Point2D.Double viewPoint = new Point2D.Double(e.getX(),e.getY());
		Point2D.Double worldPoint = new Point2D.Double();
		AffineTransform viewToWorld = new AffineTransform();
		MyAffineTransform.translate(viewToWorld, hScroll, vScroll);
		MyAffineTransform.scale(viewToWorld, 1/zoomFactor, 1/zoomFactor);
		viewToWorld.transform(viewPoint, worldPoint);
		return worldPoint;
	}

}
