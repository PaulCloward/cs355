package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import cs355.GUIFunctions;
import cs355.model.drawing.Circle;
import cs355.model.drawing.Line;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Shape;
import cs355.view.MyAffineTransform;

public class SelectState extends State{

	Shape selectedShape;
	Circle selectedCircle;
	Point2D oldPoint;
	boolean handleHit = false;
	boolean lineEndHit = false;
	boolean lineStartHit = false;
	
	boolean rotationHandle;
	
	public SelectState(MyModel model) {
		super(model);
	}

	@Override
	public void mousePress(Point2D.Double e) {
		
		oldPoint = new Point2D.Double(e.getX(),e.getY());
		
		
		if(selectedShape != null) {
			if(selectedShape instanceof Line) {
				Line line = (Line)selectedShape;
				int test = HitTest.lineHitTest(line, e);
				if(test == 0) {
					lineStartHit = true;
				} else if(test == 1) {
					lineEndHit = true;
				}
				
			} else {
				handleHit = HitTest.handleHitTest(selectedShape,e);
			}
		} 
		
		if(!handleHit && !lineEndHit && !lineStartHit) {
			hitTest(e);
		}
		
	}
	
	public void hitTest(Point2D.Double e) {
		
		List<Shape> shapes = model.getShapes();
		
		for(Shape shape : shapes) {
			if(HitTest.hitTest(shape, e)) {
			
				this.selectedShape = shape;
				GUIFunctions.changeSelectedColor(shape.getColor());
				model.update();
				break;
				
			} else {
				this.selectedShape = null;
			}
		}
	}

	@Override
	public void mouseDragged(Point2D.Double e) {
		
		if(this.selectedShape != null) {
			if(handleHit) {
				
				Point2D.Double hitPoint = new Point2D.Double(e.getX(), e.getY());
				Point2D.Double translateHitPoint = new Point2D.Double();
				Point2D.Double center = selectedShape.getCenter();
				
				AffineTransform worldToObj = new AffineTransform();
				
				MyAffineTransform.rotate(worldToObj, -selectedShape.getRotation());
				
				MyAffineTransform.translate(worldToObj, -center.getX(), -center.getY());
				
				worldToObj.transform(hitPoint, translateHitPoint);
				
				double x = translateHitPoint.getX();
				double y = translateHitPoint.getY();
				
				this.getSelectedShape().setRotation(selectedShape.getRotation() + Math.atan2(x, -y));
				
			} else {
			
				Point2D.Double newPoint = new Point2D.Double(e.getX(), e.getY());
				Point2D.Double changePoint = new Point2D.Double(newPoint.getX() - oldPoint.getX(), newPoint.getY() - oldPoint.getY());
				this.oldPoint = newPoint;
				if(lineEndHit) {
					Line line = (Line)selectedShape;
					line.getEnd().setLocation(line.getEnd().getX() + changePoint.getX(), line.getEnd().getY() + changePoint.getY());
				} else {
					selectedShape.getCenter().setLocation(selectedShape.getCenter().getX() + changePoint.getX(), selectedShape.getCenter().getY() + changePoint.getY());
					if(lineStartHit) {
						Line line = (Line)selectedShape;
						line.getEnd().setLocation(line.getEnd().getX() - changePoint.getX(), line.getEnd().getY() - changePoint.getY());
					}
				}
			}
		}
		model.update();
		
		
	}

	@Override
	public void mouseRelease(Point2D.Double e) {
		handleHit = false;
		lineStartHit = false;
		lineEndHit = false;
		
	}

	@Override
	public void mouseClicked(Point2D.Double e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Shape getSelectedShape() { 	
		return this.selectedShape;
	}

	@Override
	public void changeSelectedShapeColor(Color color) {
		// TODO Auto-generated method stub
		if(selectedShape != null){
			
			this.selectedShape.setColor(color);
			model.update();
		}
	}
	
	@Override
	public void deleteSelectedShape() {
		this.selectedShape = null;
	}

}
