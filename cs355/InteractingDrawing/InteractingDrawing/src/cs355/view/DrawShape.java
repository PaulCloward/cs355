package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;

public class DrawShape {

	public static void drawShapes(Shape shape, Graphics2D g2d, boolean border) {

		Color invertedColor = new Color(255 - shape.getColor().getRed(), 255 - shape.getColor().getGreen(), 255 - shape.getColor().getBlue());
		
		if (shape instanceof Line) {
			Line line = (Line) shape;
			Point2D.Double center = line.getCenter();
			Point2D.Double end = line.getEnd();
			g2d.setColor(shape.getColor());

			// create a new transformation (defaults to identity)
			AffineTransform objToWorld = new AffineTransform();
			
			// translate
			objToWorld.translate(center.getX(), center.getY());
			
			// rotate to its orientation (first transformation)
			objToWorld.rotate(shape.getRotation());
			
			// set the drawing transformation
			g2d.setTransform(objToWorld);
			
			if(border) {
				g2d.setColor(Color.BLUE);
				g2d.drawOval(-5, -5, 10, 10);
				g2d.drawOval((int)end.getX() - 5, (int)end.getY() - 5, 10, 10);
				
				g2d.setColor(Color.orange);
				g2d.drawOval(-6, -6, 12, 12);
				g2d.drawOval((int)end.getX() - 6, (int)end.getY() - 6, 12, 12);
				
			} else {
				g2d.drawLine(0,0,
					(int)end.getX(), (int)end.getY());
			}
			

		} else if (shape instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) shape;
			Point2D.Double center = ellipse.getCenter();
			g2d.setColor(shape.getColor());
			double width = ellipse.getWidth();
			double height = ellipse.getHeight();

			// create a new transformation (defaults to identity)
			AffineTransform objToWorld = new AffineTransform();
			// translate to its position in the world (last transformation)
			objToWorld.translate(center.getX(), center.getY());
			// rotate to its orientation (first transformation)
			objToWorld.rotate(ellipse.getRotation());
			// set the drawing transformation
			g2d.setTransform(objToWorld);
			
			if(border) {
			g2d.setColor(invertedColor);
			g2d.drawRect((int) -width/2, (int) -height/2, (int)width, (int)height);
			g2d.drawOval(-5, (int)-height/2-15,10,10);
			
			g2d.setColor(Color.ORANGE);
			g2d.drawRect((int) -width/2 - 1, (int) -height/2 - 1, (int)width + 2, (int)height + 2);
			g2d.drawOval(-6, (int)-height/2-16,12,12);
			} else {
			// and finally draw
			g2d.fillOval((int) -width / 2, (int) -height / 2, (int) width,
					(int) height);
			}
		} else if (shape instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) shape;
			Point2D.Double center = rectangle.getCenter();
			g2d.setColor(shape.getColor());

			double width = rectangle.getWidth();
			double height = rectangle.getHeight();

			// create a new transformation (defaults to identity)
			AffineTransform objToWorld = new AffineTransform();
			// translate to its position in the world (last transformation)
			objToWorld.translate(center.getX(), center.getY());
			// rotate to its orientation (first transformation)
			objToWorld.rotate(rectangle.getRotation());
			// set the drawing transformation
			g2d.setTransform(objToWorld);
			
			if(border) {
				
				g2d.setColor(invertedColor);
				g2d.drawRect((int) -width / 2, (int) -height / 2, (int) width,
						(int) height);
				g2d.drawOval(-5, (int)-height/2-15,10,10);
				
				g2d.setColor(Color.ORANGE);
				g2d.drawRect((int) -width / 2 - 1, (int) -height / 2 -1, (int) width + 2,
						(int) height + 2);
				g2d.drawOval(-6, (int)-height/2 - 16, 12, 12);
				
			} else {
				// and finally draw
				g2d.fillRect((int) -width / 2, (int) -height / 2, (int) width,
						(int) height);
			}

		} else if (shape instanceof Square) {
			Square square = (Square) shape;
			Point2D.Double center = square.getCenter();
			g2d.setColor(shape.getColor());

			double sideLength = square.getSize();

			// create a new transformation (defaults to identity)
			AffineTransform objToWorld = new AffineTransform();
			// translate to its position in the world (last transformation)
			objToWorld.translate(center.getX(), center.getY());
			// rotate to its orientation (first transformation)
			objToWorld.rotate(square.getRotation());
			// set the drawing transformation
			g2d.setTransform(objToWorld);
			// and finally draw
			
			if(border) {
				
				g2d.setColor(invertedColor);
				g2d.drawRect((int) -sideLength/2, (int) -sideLength/2, (int)sideLength, (int)sideLength);
				g2d.drawOval(-5, (int)-sideLength/2-15,10,10);
				
				g2d.setColor(Color.ORANGE);
				g2d.drawRect((int) -sideLength/2 - 1, (int) -sideLength/2 - 1, (int)sideLength + 2, (int)sideLength + 2);
				g2d.drawOval(-6, (int)-sideLength/2-16,12,12);
				
			} else {
			g2d.fillRect((int) -sideLength / 2, (int) -sideLength / 2,
					(int) sideLength, (int) sideLength);
			}
			/*
			 * if(shape.selected()){
			 * 
			 * }
			 */

		} else if (shape instanceof Circle) {

			Circle circle = (Circle) shape;
			Point2D.Double center = circle.getCenter();
			Double radius = circle.getRadius();
			g2d.setColor(shape.getColor());
			int diameter = (int)(radius*2);

			// create a new transformation (defaults to identity)
			AffineTransform objToWorld = new AffineTransform();
			// translate to its position in the world (last transformation)
			objToWorld.translate(center.getX(), center.getY());
			// rotate to its orientation (first transformation)
			objToWorld.rotate(circle.getRotation());
			// set the drawing transformation
			g2d.setTransform(objToWorld);
			
			if(border) {
				
				g2d.setColor(invertedColor);
				g2d.drawRect((int) -radius, (int) -radius, diameter, diameter);
				g2d.drawOval(-5, (int)-radius-15,10,10);
				
				g2d.setColor(Color.ORANGE);
				g2d.drawRect((int) -radius - 1, (int) -radius - 1, diameter + 2, diameter + 2);
				g2d.drawOval(-6, (int)-radius-16,12,12);
				
			} else {
			// and finally draw
			g2d.fillOval((int) -radius, (int) -radius, diameter,
					diameter);
			}
		} else if (shape instanceof Triangle) {
			
			Triangle triangle = (Triangle) shape;

            Point2D.Double center = triangle.getCenter();
            Point2D.Double pointA = triangle.getA();
            Point2D.Double pointB = triangle.getB();
            Point2D.Double pointC = triangle.getC();

            Point2D.Double topPoint = triangle.getA();
            
            if(triangle.getB().getY() < topPoint.getY()) {
            	topPoint = triangle.getB();
            }
            
            if(triangle.getC().getY() < topPoint.getY()) {
            	topPoint = triangle.getC();
            }
            
            
            int[] xPoints = new int[3];
            int[] yPoints = new int[3];

            xPoints[0] = (int) (pointA.getX());
            yPoints[0] = (int) (pointA.getY());

            xPoints[1] = (int) (pointB.getX());
            yPoints[1] = (int) (pointB.getY());

            xPoints[2] = (int) (pointC.getX());
            yPoints[2] = (int) (pointC.getY());

            g2d.setColor(shape.getColor());

            // create a new transformation (defaults to identity)
            AffineTransform objToWorld = new AffineTransform();

            // translate to its position in the world (last transformation)
            objToWorld.translate(center.getX(), center.getY());
            // rotate to its orientation (first transformation)
            objToWorld.rotate(triangle.getRotation());
            // set the drawing transformation
            g2d.setTransform(objToWorld);
            
            if(border) {
            	
            	g2d.setColor(invertedColor);
            	
            	g2d.drawLine(0, 0, (int)pointA.getX(), (int)pointA.getY());
            	g2d.drawLine(0, 0, (int)pointB.getX(), (int)pointB.getY());
            	g2d.drawLine(0, 0, (int)pointC.getX(), (int)pointC.getY());
            	g2d.drawPolygon(xPoints, yPoints, 3);
            	
            	g2d.drawOval(-5, (int)topPoint.getY()-15,10,10);
            	g2d.drawLine(0, 0, 0, (int)topPoint.getY() - 10);
            	g2d.setColor(Color.ORANGE);
            	g2d.drawOval(-6, (int)topPoint.getY()-16,12,12);
            	
            } else {
          
            	 g2d.fillPolygon(xPoints, yPoints, 3);
            }
		}

	}
}
