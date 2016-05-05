package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import cs355.GUIFunctions;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Shape;
import cs355.model.image.CS355Image;
import cs355.model.image.MyImage;
import cs355.view.MyAffineTransform;

public class MyController implements CS355Controller {

	public static final double MAXDISPLAYSIZE = 2048;
	public static final double MINZOOM = 0.25;

	private State state2D;
	private State3D state3D;
	private MyModel model;

	private double zoomFactor = 1;
	private int hScroll = 0;
	private int vScroll = 0;

	public CS355Image image;
	private boolean displayImage = false;

	public MyController(MyModel model) {
		this.model = model;
		this.state2D = new LineState(model);
		this.state2D.setColor(Color.WHITE);

	}

	public Point2D.Double getScrollValues() {
		return new Point2D.Double(hScroll, vScroll);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D.Double worldPoint = viewToWorld(e);
		state2D.mouseClicked(worldPoint);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point2D.Double worldPoint = viewToWorld(e);
		state2D.mousePress(worldPoint);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point2D.Double worldPoint = viewToWorld(e);
		state2D.mouseRelease(worldPoint);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point2D.Double worldPoint = viewToWorld(e);
		state2D.mouseDragged(worldPoint);

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void colorButtonHit(Color c) {
		this.state2D.setColor(c);
		this.state2D.setSelectedShapeColor(c);
		GUIFunctions.changeSelectedColor(c);

	}

	@Override
	public void lineButtonHit() {
		this.state2D = new LineState(model);
		model.update();

	}

	@Override
	public void squareButtonHit() {
		this.state2D = new SquareState(model);
		model.update();

	}

	@Override
	public void rectangleButtonHit() {
		this.state2D = new RectangleState(model);
		model.update();
	}

	@Override
	public void circleButtonHit() {
		this.state2D = new CircleState(model);
		model.update();

	}

	@Override
	public void ellipseButtonHit() {
		this.state2D = new ElipseState(model);
		model.update();
	}

	@Override
	public void triangleButtonHit() {
		this.state2D = new TriangleState(model);
		model.update();

	}

	@Override
	public void selectButtonHit() {
		this.state2D = new SelectState(model);
		model.update();

	}

	public void updateZoom(boolean zoomIn) {

		int portionOfScrollBar = (int) (zoomFactor / MINZOOM);
		int scrollBarSize = (int) (MAXDISPLAYSIZE / portionOfScrollBar);

		if (zoomIn) {
			if ((hScroll + scrollBarSize / 2) >= MAXDISPLAYSIZE - scrollBarSize
					* 2) {

				GUIFunctions.setHScrollBarPosit(0);
				GUIFunctions.setHScrollBarKnob(scrollBarSize);
				hScroll = (int) (MAXDISPLAYSIZE - scrollBarSize * 1.5);
			} else {
				hScroll += scrollBarSize / 2;
			}

			if ((vScroll + scrollBarSize / 2) >= MAXDISPLAYSIZE - scrollBarSize
					* 2) {

				GUIFunctions.setVScrollBarPosit(0);
				GUIFunctions.setVScrollBarKnob(scrollBarSize);
				vScroll = (int) (MAXDISPLAYSIZE - scrollBarSize * 1.5);
			} else {
				vScroll += scrollBarSize / 2;
			}
		}

		else {
			if (hScroll > MyController.MAXDISPLAYSIZE - scrollBarSize) {
				hScroll = (int) MyController.MAXDISPLAYSIZE - scrollBarSize;
			} else if (hScroll < scrollBarSize / 4) {
				hScroll = 0;
			} else {
				hScroll -= scrollBarSize / 4;
			}
			if (vScroll > MyController.MAXDISPLAYSIZE - scrollBarSize) {
				vScroll = (int) MyController.MAXDISPLAYSIZE - scrollBarSize;
			} else if (vScroll < scrollBarSize / 4) {
				vScroll = 0;
			} else {
				vScroll -= scrollBarSize / 4;
			}

		}
		GUIFunctions.setHScrollBarPosit(hScroll);
		GUIFunctions.setVScrollBarPosit(vScroll);

		// System.out.println("hScroll: " + hScroll);
		// System.out.println("vScroll: " + vScroll);

		GUIFunctions.setHScrollBarKnob(scrollBarSize);
		GUIFunctions.setVScrollBarKnob(scrollBarSize);

		GUIFunctions.setZoomText(zoomFactor);
	}

	@Override
	public void zoomInButtonHit() {
		if (zoomFactor < 4) {
			zoomFactor *= 2;
			updateZoom(true);
		}
		model.update();
	}

	@Override
	public void zoomOutButtonHit() {
		if (zoomFactor > .25) {
			zoomFactor *= .5;
			updateZoom(false);
		}
		model.update();
	}

	@Override
	public void hScrollbarChanged(int value) {
		hScroll = value;
		model.update();

	}

	@Override
	public void vScrollbarChanged(int value) {
		vScroll = value;
		model.update();
	}

	@Override
	public void openScene(File file) {

	}

	@Override
	public void toggle3DModelDisplay() {
		if (this.state3D == null) {
			this.state3D = new State3D();
			this.state3D.render();
		} else {
			this.state3D.clear();
			this.state3D = null;
		}
		model.update();
	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		if (state3D != null) {
			state3D.keyPressed(iterator);
		}
		model.update();

	}

	@Override
	public void openImage(File file) {
		// Make a MyImage object
		BufferedImage in;
		try {
			in = ImageIO.read(file);
			MyImage image = new MyImage(in.getWidth(), in.getHeight());
			image.setPixels(in);
			this.image = image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		displayImage = true;
		model.update();
	}

	@Override
	public void saveImage(File file) {
		try {
			ImageIO.write(image.getImage(), "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toggleBackgroundDisplay() {
		if (displayImage) {
			displayImage = false;
		} else {
			displayImage = true;
		}
		model.update();
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
		Shape selectedShape = getSelectedShape();
		if (selectedShape != null) {
			int index = model.getShapeIndex(selectedShape);
			model.deleteShape(index);
		}
		state2D.deleteSelectedShape();
		model.update();
	}

	@Override
	public void doEdgeDetection() {
		image.edgeDetection();
		model.update();
	}

	@Override
	public void doSharpen() {
		image.sharpen();
		model.update();
	}

	@Override
	public void doMedianBlur() {
		image.medianBlur();
		model.update();
	}

	@Override
	public void doUniformBlur() {
		image.uniformBlur();
		model.update();
	}

	@Override
	public void doGrayscale() {
		image.grayscale();
		model.update();
	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		image.contrast(contrastAmountNum);
		model.update();
	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		image.brightness(brightnessAmountNum);
		model.update();
	}

	@Override
	public void doMoveForward() {
		// System.out.println("Forward");
		Shape selectedShape = getSelectedShape();
		if (selectedShape != null) {
			int index = model.getShapeIndex(selectedShape);
			model.moveForward(index);
		}
		model.update();
	}

	@Override
	public void doMoveBackward() {
		// System.out.println("Backward");
		Shape selectedShape = getSelectedShape();
		if (selectedShape != null) {
			int index = model.getShapeIndex(selectedShape);
			model.moveBackward(index);
		}
		model.update();
	}

	@Override
	public void doSendToFront() {
		// System.out.println("SendToFront");
		Shape selectedShape = getSelectedShape();
		if (selectedShape != null) {
			int index = model.getShapeIndex(selectedShape);
			model.moveToFront(index);
		}
		// model.update();
	}

	@Override
	public void doSendtoBack() {
		System.out.println("SendToBack");
		Shape selectedShape = getSelectedShape();
		if (selectedShape != null) {
			int index = model.getShapeIndex(selectedShape);
			model.movetoBack(index);
		}
		model.update();
	}

	public Shape getSelectedShape() {
		return state2D.getSelectedShape();
	}

	public void changeColor(Color c) {
		this.state2D.setColor(c);
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public Point2D.Double viewToWorld(MouseEvent e) {
		Point2D.Double viewPoint = new Point2D.Double(e.getX(), e.getY());
		Point2D.Double worldPoint = new Point2D.Double();

		AffineTransform viewToWorld = new AffineTransform();
		MyAffineTransform.translate(viewToWorld, hScroll, vScroll);
		MyAffineTransform.scale(viewToWorld, 1 / zoomFactor, 1 / zoomFactor);
		viewToWorld.transform(viewPoint, worldPoint);
		return worldPoint;
	}

	public BufferedImage getBufferedImage() {
		if (image != null && displayImage) {
			return image.getImage();
		}
		return null;
	}

}

