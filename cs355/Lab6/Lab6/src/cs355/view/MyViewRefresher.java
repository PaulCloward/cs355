package cs355.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.controller.MyController;
import cs355.model.drawing.Line;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Shape;

public class MyViewRefresher implements ViewRefresher {

	private MyModel model;
	private MyController controller;
	private MatrixStuff matrixStuff = MatrixStuff.getSingleton();

	@Override
	public void update(Observable o, Object arg1) {

		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {

		Point2D.Double scrollValues = controller.getScrollValues();
		double zoomFactor = controller.getZoomFactor();

		BufferedImage image = controller.getBufferedImage();
		if (image != null) {

			// List<Line> lines = matrixStuff.getLines();

			AffineTransform worldToView = new AffineTransform();
			MyAffineTransform.scale(worldToView, zoomFactor, zoomFactor);
			MyAffineTransform.translate(worldToView, -scrollValues.getX(),
					-scrollValues.getY());

			g2d.setTransform(worldToView);

			int x = (2048 - image.getWidth()) / 2;
			int y = (2048 - image.getHeight()) / 2;
			g2d.drawImage(image, null, x, y);
		}

		List<Shape> refreshShapes = model.getShapesReversed();
		for (Shape shape : refreshShapes) {
			DrawShape.drawShapes(shape, g2d, false, zoomFactor, scrollValues);
		}

		Shape selectedShape = controller.getSelectedShape();
		if (selectedShape != null) {
			controller.changeColor(selectedShape.getColor());
			// System.out.println("Selected Shape");
			DrawShape.drawShapes(selectedShape, g2d, true, zoomFactor,
					scrollValues);

		}

		List<Line> lines = matrixStuff.getLines();

		AffineTransform worldToView = new AffineTransform();
		MyAffineTransform.scale(worldToView, zoomFactor, zoomFactor);
		MyAffineTransform.translate(worldToView, -scrollValues.getX(),
				-scrollValues.getY());

		g2d.setTransform(worldToView);
		for (Line line : lines) {
			// System.out.println("refreshView - drawLine");
			g2d.setColor(line.getColor());
			g2d.drawLine((int) line.getCenter().getX(), (int) line.getCenter()
					.getY(), (int) line.getEnd().getX(), (int) line.getEnd()
					.getY());
		}

	}

	public void setModel(MyModel model) {
		this.model = model;
	}

	public void setController(MyController controller) {
		this.controller = controller;
	}

}
