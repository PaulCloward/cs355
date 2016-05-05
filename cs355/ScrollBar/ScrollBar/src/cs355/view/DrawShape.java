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
	
	public static int smallCornerX = -5;
	public static int smallDiameter = 10;
	public static int bigCornerX = -6;
	public static int bigDiameter = 12;
	public static double zoomFactor = 1;
	
	public static void drawShapes(Shape shape, Graphics2D g2d, boolean border, double zoomFactor, Point2D.Double scrollValues) {
		DrawShape.zoomFactor = zoomFactor;
		smallCornerX = (int)(-5/zoomFactor);
		int smallCornerY = (int) (-15/zoomFactor);
		smallDiameter = (int)(10/zoomFactor);
		bigCornerX = (int)(-6/zoomFactor);
		int bigCornerY = (int) (-16/zoomFactor);
		bigDiameter = (int)(12/zoomFactor);
		
		Color invertedColor = new Color(255 - shape.getColor().getRed(), 255 - shape.getColor().getGreen(), 255 - shape.getColor().getBlue());
		g2d.setColor(shape.getColor());
		Point2D.Double center = shape.getCenter();

		 
		AffineTransform objToWorld = new AffineTransform();
        
		MyAffineTransform.translate(objToWorld, center.getX(), center.getY());
		
		MyAffineTransform.rotate(objToWorld, shape.getRotation());
        
		AffineTransform worldToView = new AffineTransform();
		MyAffineTransform.scale(worldToView,zoomFactor, zoomFactor);
		MyAffineTransform.translate(worldToView, -scrollValues.getX(), -scrollValues.getY());
        worldToView.concatenate(objToWorld);
        
        g2d.setTransform(worldToView);
        
		if (shape instanceof Line) {
			Line line = (Line) shape;
			Point2D.Double end = line.getEnd();
			
			if(border) {
				g2d.setColor(invertedColor);
				g2d.drawOval(smallCornerX, smallCornerX,
						smallDiameter,smallDiameter);
				g2d.drawOval((int)end.getX()+smallCornerX, (int)end.getY()+smallCornerX, smallDiameter,smallDiameter);
				
				g2d.setColor(Color.orange);
				g2d.drawOval(bigCornerX, bigCornerX, bigDiameter, bigDiameter);
				g2d.drawOval((int)end.getX() + bigCornerX, (int)end.getY() + bigCornerX, bigDiameter, bigDiameter);
				
			} else {
				g2d.drawLine(0,0,
					(int)end.getX(), (int)end.getY());
			}
			

		} else if (shape instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) shape;
			double width = ellipse.getWidth();
			double height = ellipse.getHeight();
			
			if(border) {
			g2d.setColor(invertedColor);
			g2d.drawRect((int) -width/2, (int) -height/2, (int)width, (int)height);
			g2d.drawOval(smallCornerX, (int)-height/2+smallCornerY,smallDiameter,smallDiameter);
			
			g2d.setColor(Color.ORANGE);
			g2d.drawRect((int) -width/2 - 1, (int) -height/2 - 1, (int)width + 2, (int)height + 2);
			g2d.drawOval(bigCornerX, (int)-height/2+bigCornerY,bigDiameter,bigDiameter);
			} else {
			// and finally draw
			g2d.fillOval((int) -width / 2, (int) -height / 2, (int) width,
					(int) height);
			}
		} else if (shape instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) shape;

			double width = rectangle.getWidth();
			double height = rectangle.getHeight();
			
			if(border) {
				
				g2d.setColor(invertedColor);
				g2d.drawRect((int) -width / 2, (int) -height / 2, (int) width,
						(int) height);
				g2d.drawOval(smallCornerX, (int)-height/2+smallCornerY,smallDiameter,smallDiameter);
				
				g2d.setColor(Color.ORANGE);
				g2d.drawRect((int) -width / 2 - 1, (int) -height / 2 -1, (int) width + 2,
						(int) height + 2);
				g2d.drawOval(bigCornerX, (int)-height/2+bigCornerY, bigDiameter, bigDiameter);
				
			} else {
				// and finally draw
				g2d.fillRect((int) -width / 2, (int) -height / 2, (int) width,
						(int) height);
			}

		} else if (shape instanceof Square) {
			Square square = (Square) shape;

			double sideLength = square.getSize();
			
			if(border) {
				
				g2d.setColor(invertedColor);
				g2d.drawRect((int) -sideLength/2, (int) -sideLength/2, (int)sideLength, (int)sideLength);
				g2d.drawOval(smallCornerX, (int)-sideLength/2+smallCornerY,smallDiameter,smallDiameter);
				
				g2d.setColor(Color.ORANGE);
				g2d.drawRect((int) -sideLength/2 - 1, (int) -sideLength/2 - 1, (int)sideLength + 2, (int)sideLength + 2);
				g2d.drawOval(bigCornerX, (int)-sideLength/2+bigCornerY,bigDiameter, bigDiameter);
				
			} else {
			g2d.fillRect((int) -sideLength / 2, (int) -sideLength / 2,
					(int) sideLength, (int) sideLength);
			}

		} else if (shape instanceof Circle) {

			Circle circle = (Circle) shape;
			Double radius = circle.getRadius();
			int diameter = (int)(radius*2);
			
			if(border) {
				
				g2d.setColor(invertedColor);
				g2d.drawRect((int) -radius, (int) -radius, diameter, diameter);
				g2d.drawOval(smallCornerX, (int)-radius+smallCornerY,smallDiameter,smallDiameter);
				
				g2d.setColor(Color.ORANGE);
				g2d.drawRect((int) -radius - 1, (int) -radius - 1, diameter + 2, diameter + 2);
				g2d.drawOval(bigCornerX, (int)-radius+bigCornerY,bigDiameter,bigDiameter);
				
			} else {
			// and finally draw
			g2d.fillOval((int) -radius, (int) -radius, diameter,
					diameter);
			}
		} else if (shape instanceof Triangle) {
			
			Triangle triangle = (Triangle) shape;
			
            Point2D.Double pointA = triangle.getA();
            Point2D.Double pointB = triangle.getB();
            Point2D.Double pointC = triangle.getC();
            
            
            int[] xPoints = new int[3];
            int[] yPoints = new int[3];

            xPoints[0] = (int) (pointA.getX());
            yPoints[0] = (int) (pointA.getY());

            xPoints[1] = (int) (pointB.getX());
            yPoints[1] = (int) (pointB.getY());

            xPoints[2] = (int) (pointC.getX());
            yPoints[2] = (int) (pointC.getY());

    
            
            if(border) {
            	
            	  Point2D.Double topPoint = triangle.getA();
            	
            	 if(triangle.getB().getY() < topPoint.getY()) {
                 	topPoint = triangle.getB();
                 }
                 
                 if(triangle.getC().getY() < topPoint.getY()) {
                 	topPoint = triangle.getC();
                 }
            	
            	g2d.setColor(invertedColor);
            	
            	g2d.drawLine(0, 0, (int)pointA.getX(), (int)pointA.getY());
            	g2d.drawLine(0, 0, (int)pointB.getX(), (int)pointB.getY());
            	g2d.drawLine(0, 0, (int)pointC.getX(), (int)pointC.getY());
            	g2d.drawPolygon(xPoints, yPoints, 3);
            	
            	g2d.drawOval(smallCornerX, (int)topPoint.getY()+ smallCornerY,smallDiameter,smallDiameter);
            	g2d.drawLine(0, 0, 0, (int)topPoint.getY() - 10);
            	g2d.setColor(Color.ORANGE);
            	g2d.drawOval(bigCornerX, (int)topPoint.getY()+bigCornerY,bigDiameter,bigDiameter);
            	
            } else {
          
            	 g2d.fillPolygon(xPoints, yPoints, 3);
            }
		}

	}
}
