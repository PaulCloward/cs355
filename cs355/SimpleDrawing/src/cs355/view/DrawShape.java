package cs355.view;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;

public class DrawShape {

	public static void drawShapes(Shape shape, Graphics2D g2d) {
		
		if(shape instanceof Line) {
			Line line = (Line)shape;
			Point2D.Double start = line.getStart();
			Point2D.Double end = line.getEnd();
			g2d.setColor(shape.getColor());
			g2d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
			
		} else if(shape instanceof Ellipse) {
			Ellipse ellipse = (Ellipse)shape;
			Point2D.Double center = ellipse.getCenter();
			Point2D.Double upperLeft = new Point2D.Double((center.getX() - ellipse.getWidth()/2),center.getY() - ellipse.getHeight()/2);
			g2d.setColor(shape.getColor());
			g2d.fillOval((int)upperLeft.getX(),(int)upperLeft.getY(),(int)ellipse.getWidth(),(int)ellipse.getHeight());
			
			
		} else if(shape instanceof Rectangle) {
			Rectangle rectangle = (Rectangle)shape;
			Point2D.Double upperLeft = rectangle.getUpperLeft();
			g2d.setColor(shape.getColor());
			g2d.fillRect((int)upperLeft.getX(), (int)upperLeft.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
			
			
		} else if(shape instanceof Square) {
			Square square = (Square)shape;
			Point2D.Double upperLeft = square.getUpperLeft();
			g2d.setColor(shape.getColor());
			g2d.fillRect((int)upperLeft.getX(), (int)upperLeft.getY(), (int)square.getSize(), (int)square.getSize());
			
		} else if(shape instanceof Circle) {
			Circle circle = (Circle)shape;
			Point2D.Double center = circle.getCenter();
			Double radius = circle.getRadius();
			g2d.setColor(shape.getColor());
			g2d.fillOval((int)(center.getX() - radius), (int)(center.getY() - radius), (int)(2*radius), (int)(2*radius));
			
		} else if(shape instanceof Triangle) {
			Triangle triangle = (Triangle)shape;
			g2d.setColor(shape.getColor());
			Polygon p = new Polygon();
			p.addPoint((int)triangle.getA().getX(), (int)triangle.getA().getY());
			p.addPoint((int)triangle.getB().getX(), (int)triangle.getB().getY());
			p.addPoint((int)triangle.getC().getX(), (int)triangle.getC().getY());
			
			g2d.fillPolygon(p);
		}
		
	}
}
