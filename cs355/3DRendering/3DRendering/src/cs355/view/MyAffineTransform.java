package cs355.view;

import java.awt.geom.AffineTransform;

public class MyAffineTransform {

	public static void rotate(AffineTransform at, double theta) {
		AffineTransform rotationMatrix = new AffineTransform(Math.cos(theta), Math.sin(theta), -Math.sin(theta),Math.cos(theta),0 ,0);
		at.concatenate(rotationMatrix);
	}
	
	public static void translate(AffineTransform at, double tx, double ty) {
		AffineTransform translationMatrix = new AffineTransform(1, 0, 0, 1, tx, ty);
		at.concatenate(translationMatrix);
	}
	
	public static void scale(AffineTransform at, double sx, double sy) {
		AffineTransform scaleMatrix = new AffineTransform(sx, 0, 0, sy, 0, 0);
		at.concatenate(scaleMatrix);
	}
}
