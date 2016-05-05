package cs355.controller;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;

public class HitTest {

	public static int lineHitTest(Line line, MouseEvent e) {
		
		Point2D.Double hitPoint = new Point2D.Double(e.getX(), e.getY());
		Point2D.Double translateHitPoint = new Point2D.Double();
		Point2D.Double center = line.getCenter();
		
		AffineTransform worldToObj = new AffineTransform();
		
		worldToObj.rotate(-line.getRotation());
		
		worldToObj.translate(-center.getX(), -center.getY());
		
		worldToObj.transform(hitPoint, translateHitPoint);
		
		double x = translateHitPoint.getX();
		double y = translateHitPoint.getY();
		
			if(Math.abs(x) < 6 && Math.abs(y) < 6) {
			
			double hitDistance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			
			if(hitDistance < 6) {
				
				System.out.println("Line Start Hit");
				return 0;
			}
			}
			
			x = x - line.getEnd().getX();
			y = y - line.getEnd().getY();
			
			if(Math.abs(x) < 6 && Math.abs(y) < 6) {
				
				double hitDistance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
				
				if(hitDistance < 6) {
					
					System.out.println("Line End Hit");
					return 1;
				}
			}
		return -1;
	}
	
	
	public static boolean handleHitTest(Shape shape, MouseEvent e) {
		
		Point2D.Double hitPoint = new Point2D.Double(e.getX(), e.getY());
		Point2D.Double translateHitPoint = new Point2D.Double();
		Point2D.Double center = shape.getCenter();
		
		AffineTransform worldToObj = new AffineTransform();
		
		worldToObj.rotate(-shape.getRotation());
		
		worldToObj.translate(-center.getX(), -center.getY());
		
		worldToObj.transform(hitPoint, translateHitPoint);
		
		double x = translateHitPoint.getX();
		double y = translateHitPoint.getY();
		
		double offset = 0;
		
		
		if(shape instanceof Line) {
			return false;
			
		} else if(shape instanceof Rectangle) {
		
			Rectangle rectangle = (Rectangle)shape;
			offset = rectangle.getHeight()/2;
		}
		else if(shape instanceof Square) {
			Square square = (Square) shape;
			offset = square.getSize()/2;
		}
		else if(shape instanceof Ellipse) {
			
			Ellipse ellipse = (Ellipse)shape;
			offset = ellipse.getHeight()/2;
			
			
		}
		else if(shape instanceof Triangle) {
			
			Triangle triangle = (Triangle)shape;
			Point2D.Double topPoint = triangle.getA();
			if(triangle.getB().getY() < topPoint.getY()) {
				topPoint = triangle.getB();
			}
			if(triangle.getC().getY() < topPoint.getY()) {
				topPoint = triangle.getC();
			}
			
			offset = Math.abs(topPoint.getY());
		} else if(shape instanceof Circle) {
			
			Circle circle = (Circle)shape;
			offset= circle.getRadius();
		}
				
		offset = offset + 10;
		offset = -offset;
		y = y - offset;
		
		double absX = Math.abs(x);
		double absY = Math.abs(y);
	
		if(absX < 6 && absY < 6) {
			
			double hitDistance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			
			if(hitDistance < 6) {
				
				System.out.println("Circle Hit");
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
	
	public static boolean hitTest(Shape shape, MouseEvent e) {
		
		Point2D.Double hitPoint = new Point2D.Double(e.getX(), e.getY());
		Point2D.Double translateHitPoint = new Point2D.Double();
		Point2D.Double center = shape.getCenter();
		
		AffineTransform worldToObj = new AffineTransform();
		
		worldToObj.rotate(-shape.getRotation());
		
		worldToObj.translate(-center.getX(), -center.getY());
		
		worldToObj.transform(hitPoint, translateHitPoint);
		
		double x = translateHitPoint.getX();
		double y = translateHitPoint.getY();
		double absX = Math.abs(x);
		double absY = Math.abs(y);
		
		if(shape instanceof Line) {
			
			Line line = (Line)shape;
			Point2D.Double p = line.getEnd();
			double pDistance = Math.sqrt(Math.pow(p.getX(),2) + Math.pow(p.getY(),2));
			Point2D.Double nHat = new Point2D.Double(-(p.getY()/pDistance), (p.getX()/pDistance));
			double d = (p.getX() * nHat.getX()) + (p.getY() * nHat.getY());
			
			double qNHat = ((x * nHat.getX()) + (y * nHat.getY()));
			
			double qDistance = Math.abs(qNHat - d);
			
			if(qDistance <= 4) {
				double dMinusQHat = d - qNHat;
				Point2D.Double crazyNHat = new Point2D.Double(dMinusQHat * nHat.getX(), dMinusQHat * nHat.getY());
				
				Point2D.Double qHat = new Point2D.Double(x + crazyNHat.getX(), y + crazyNHat.getY());
				
				double qHatX = qHat.getX();
				double qHatY = qHat.getY();
			
				if(p.getX() <= 0 && p.getY() <= 0) {
					if(qHatX < 0 && qHatX > p.getX() && qHatY < 0 && qHatY > p.getY()) {
						System.out.println("Line hit");
						return true;
					}
				} else if(p.getX() > 0 && p.getY() <= 0) {
					if(qHatX > 0 && qHatX < p.getX() && qHatY < 0 && qHatY > p.getY()) {
						System.out.println("Line hit");
						return true;
					}
				} else if(p.getX() > 0 && p.getY() > 0) {
					if(qHatX > 0 && qHatX < p.getX() && qHatY > 0 && qHatY < p.getY()) {
						System.out.println("Line hit");
						return true;
					}
				} else if(p.getX() <= 0 && p.getY() > 0) {
					if(qHatX < 0 && qHatX > p.getX() && qHatY > 0 && qHatY < p.getY()) {
						System.out.println("Line hit");
						return true;
					}
				}
			}
		} else if(shape instanceof Rectangle) {
		
			Rectangle rectangle = (Rectangle)shape;
			double halfWidth = rectangle.getWidth()/2;
			double halfHeight = rectangle.getHeight()/2;
			
			if(absX < halfWidth && absY < halfHeight) {
				System.out.println("In the rectangle");
				return true;
				
			}
			
		}
		else if(shape instanceof Square) {
			Square square = (Square) shape;
			double halfSideLength = square.getSize()/2;
			if(absX < halfSideLength && absY < halfSideLength) {
				System.out.println("Square Hit");
					return true;
			}
			
		}
		else if(shape instanceof Ellipse) {
			
			Ellipse ellipse = (Ellipse)shape;
			
			double halfWidth = ellipse.getWidth()/2;
			double halfHeight = ellipse.getHeight()/2;
			if(absX < halfWidth && absY < halfHeight) {

				double value = Math.pow((x/halfWidth),2) + Math.pow((y/halfHeight),2);
	
				if(value <= 1) {
					System.out.println("Ellipse Hit");
					return true;
				}
			}
			
		}
		else if(shape instanceof Triangle) {
			
			Triangle triangle = (Triangle)shape;
			Point2D.Double p0Expression1 = new Point2D.Double(x - triangle.getA().getX(), y - triangle.getA().getY());
			Point2D.Double p0Expression2 = new Point2D.Double(triangle.getB().getX() - triangle.getA().getX(), triangle.getB().getY() - triangle.getA().getY());
			p0Expression2.setLocation(-p0Expression2.getY(), p0Expression2.getX());
			double p0 = ((p0Expression1.getX() * p0Expression2.getX()) + (p0Expression1.getY() * p0Expression2.getY()));
			
			Point2D.Double p1Expression1 = new Point2D.Double(x - triangle.getB().getX(), y - triangle.getB().getY());
			Point2D.Double p1Expression2 = new Point2D.Double(triangle.getC().getX() - triangle.getB().getX(), triangle.getC().getY() - triangle.getB().getY());
			p1Expression2.setLocation(-p1Expression2.getY(), p1Expression2.getX());
			double p1 = ((p1Expression1.getX() * p1Expression2.getX()) + (p1Expression1.getY() * p1Expression2.getY()));
			
			Point2D.Double p2Expression1 = new Point2D.Double(x - triangle.getC().getX(), y - triangle.getC().getY());
			Point2D.Double p2Expression2 = new Point2D.Double(triangle.getA().getX() - triangle.getC().getX(), triangle.getA().getY() - triangle.getC().getY());
			p2Expression2.setLocation(-p2Expression2.getY(), p2Expression2.getX());
			double p2 = ((p2Expression1.getX() * p2Expression2.getX()) + (p2Expression1.getY() * p2Expression2.getY()));
			
			if((p0 >= 0 && p1 >= 0 && p2 >= 0) || (p0 <= 0 && p1 <= 0 && p2 <= 0)) {
				
				System.out.println("triangle hit");
				return true;
			}
			
		} else if(shape instanceof Circle) {
			
			Circle circle = (Circle)shape;
			double radius = circle.getRadius();
			if(absX < radius && absY < radius) {
				
				double hitDistance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
				
				if(hitDistance < radius) {
					
					System.out.println("Circle Hit");
					return true;
				}
			}
			
		}
		return false;
		
	}
}
