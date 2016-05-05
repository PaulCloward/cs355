package cs355.model.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class MyImage extends CS355Image {

	public MyImage() {
		super();
	}

	public MyImage(int width, int height) {
		super(width, height);
	}

	@Override
	public BufferedImage getImage() {
		int[] rgb = new int[3];

		BufferedImage bufferedImage = new BufferedImage(this.getWidth(),
				this.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {

				getPixel(x, y, rgb);
				int value = new Color(rgb[0], rgb[1], rgb[2]).getRGB();
				bufferedImage.setRGB(x, y, value);
			}
		}
		return bufferedImage;
	}

	@Override
	public void edgeDetection() {

		// Preallocate the arrays.
		int[] rgb = new int[3];
		float[] hsb = new float[3];

		// int[] kernal = new int[] { 0, -1, 0, -1, 6, -1, 0, -1, 0 };
		float[][] kernalx = new float[3][3];
		kernalx[0][0] = -1;
		kernalx[0][1] = 0;
		kernalx[0][2] = 1;
		kernalx[1][0] = -2;
		kernalx[1][1] = 0;
		kernalx[1][2] = 2;
		kernalx[2][0] = -1;
		kernalx[2][1] = 0;
		kernalx[2][2] = 1;

		float[][] kernaly = new float[3][3];
		kernaly[0][0] = -1;
		kernaly[0][1] = -2;
		kernaly[0][2] = -1;
		kernaly[1][0] = 0;
		kernaly[1][1] = 0;
		kernaly[1][2] = 0;
		kernaly[2][0] = 1;
		kernaly[2][1] = 2;
		kernaly[2][2] = 1;

		// new Image buffer
		double[][] imagex = new double[this.getWidth()][this.getHeight()];
		double[][] imagey = new double[this.getWidth()][this.getHeight()];
		CS355Image newImage = new MyImage(this.getWidth(), this.getHeight());

		// Do the following for x and y derivative:
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {

				double totalx = 0;
				double totaly = 0;
				// int counter = 0;

				// add each neighbor to total
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						int newx = x + i;
						int newy = y + j;
						if (newx >= 0 && newy >= 0 && newx < this.getWidth()
								&& newy < this.getHeight()) {

							getPixel(newx, newy, rgb);
							// Convert to HSB.
							Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
							totalx += hsb[2] * kernalx[i + 1][j + 1];
							totaly += hsb[2] * kernaly[i + 1][j + 1];

						}
					}
				}

				imagex[x][y] = totalx / 8;
				imagey[x][y] = totaly / 8;
			}
		}

		// Do the following for combined x and y derivative:
		double brightness_x, brightness_y;
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {

				brightness_x = imagex[x][y];
				brightness_y = imagey[x][y];

				double total = Math.sqrt(Math.pow(brightness_x, 2)
						+ Math.pow(brightness_y, 2));

				total = total * 255;

				int value = (int) (total);

				if (value > 255) {
					value = 255;
				} else if (value < 0) {
					value = 0;
				}

				int[] rgbValue = new int[] { value, value, value };
				newImage.setPixel(x, y, rgbValue);
			}
		}
		// set Image buffer as image
		this.setPixels(newImage);
	}

	@Override
	public void sharpen() {
		// Preallocate the arrays.
		int[] rgb = new int[3];
		// int[] kernal = new int[] { 0, -1, 0, -1, 6, -1, 0, -1, 0 };
		int[][] kernal2D = new int[3][3];
		kernal2D[0][0] = 0;
		kernal2D[0][1] = -1;
		kernal2D[0][2] = 0;
		kernal2D[1][0] = -1;
		kernal2D[1][1] = 6;
		kernal2D[1][2] = -1;
		kernal2D[2][0] = 0;
		kernal2D[2][1] = -1;
		kernal2D[2][2] = 0;

		// new Image buffer
		CS355Image newImage = new MyImage(this.getWidth(), this.getHeight());

		// Do the following for each pixel:
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {

				int[] total = new int[3];
				// int counter = 0;

				// add each neighbor to total
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						int newx = x + i;
						int newy = y + j;
						if (newx >= 0 && newy >= 0 && newx < this.getWidth()
								&& newy < this.getHeight()) {
							getPixel(newx, newy, rgb);

							total[0] += rgb[0] * kernal2D[i + 1][j + 1];
							total[1] += rgb[1] * kernal2D[i + 1][j + 1];
							total[2] += rgb[2] * kernal2D[i + 1][j + 1];
						}
					}
				}
				for (int i = 0; i < 3; i++) {
					total[i] = total[i] / 2;
					if (total[i] > 255) {
						total[i] = 255;
					} else if (total[i] < 0) {
						total[i] = 0;
					}
				}
				newImage.setPixel(x, y, total);
			}
		}
		// set Image buffer as image
		this.setPixels(newImage);
	}

	@Override
	public void medianBlur() {
		// Preallocate the arrays.
		int[] rgb = new int[3];

		// new Image buffer
		CS355Image newImage = new MyImage(this.getWidth(), this.getHeight());
		int[] rgbZero = new int[] { 0, 0, 0 };

		// Do the following for each pixel:
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {

				ArrayList<Integer> red = new ArrayList<>();
				ArrayList<Integer> green = new ArrayList<>();
				ArrayList<Integer> blue = new ArrayList<>();

				// add each neighbor to total
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						int newx = x + i;
						int newy = y + j;
						if (newx >= 0 && newy >= 0 && newx < this.getWidth()
								&& newy < this.getHeight()) {
							getPixel(newx, newy, rgb);

							red.add(rgb[0]);
							green.add(rgb[1]);
							blue.add(rgb[2]);

						} else {
							red.add(rgb[0]);
							green.add(rgb[0]);
							blue.add(rgb[0]);

						}
					}
				}

				Collections.sort(red);
				Collections.sort(green);
				Collections.sort(blue);

				int[] mrgb = new int[] { red.get(4), green.get(4), blue.get(4) };

				double minDistance = Double.POSITIVE_INFINITY;
				int[] rgbAnswer = new int[3];

				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						int newx = x + i;
						int newy = y + j;
						double tempDistance;
						if (newx >= 0 && newy >= 0 && newx < this.getWidth()
								&& newy < this.getHeight()) {
							getPixel(newx, newy, rgb);
							tempDistance = leastSquaredDistance(mrgb, rgb);

						} else {
							tempDistance = leastSquaredDistance(mrgb, rgbZero);
						}

						if (tempDistance < minDistance) {
							minDistance = tempDistance;
							rgbAnswer[0] = rgb[0];
							rgbAnswer[1] = rgb[1];
							rgbAnswer[2] = rgb[2];
						}
					}
				}
				for (int i = 0; i < 3; i++) {
					if (rgbAnswer[i] > 255) {
						rgbAnswer[i] = 255;
					} else if (rgbAnswer[i] < 0) {
						rgbAnswer[i] = 0;
					}
				}
				newImage.setPixel(x, y, rgbAnswer);
			}
		}

		// set Image buffer as image
		this.setPixels(newImage);
	}

	private double leastSquaredDistance(int[] mrgb, int[] rgb) {
		int[] newrgb = new int[3];
		for (int i = 0; i < 3; i++) {
			newrgb[i] = mrgb[i] - rgb[i];

		}
		return Math.sqrt(Math.pow(newrgb[0], 2) + Math.pow(newrgb[1], 2)
				+ Math.pow(newrgb[2], 2));
	}

	@Override
	public void uniformBlur() {
		// Preallocate the arrays.
		int[] rgb = new int[3];

		// new Image buffer
		CS355Image newImage = new MyImage(this.getWidth(), this.getHeight());

		// Do the following for each pixel:
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {

				int[] total = new int[3];

				// add each neighbor to total
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						int newx = x + i;
						int newy = y + j;
						if (newx >= 0 && newy >= 0 && newx < this.getWidth()
								&& newy < this.getHeight()) {
							getPixel(newx, newy, rgb);

							total[0] += rgb[0];
							total[1] += rgb[1];
							total[2] += rgb[2];
						}
					}
				}

				for (int i = 0; i < 3; i++) {
					total[i] = total[i] / 9;
				}

				for (int i = 0; i < 3; i++) {
					if (total[i] > 255) {
						total[i] = 255;
					} else if (total[i] < 0) {
						total[i] = 0;
					}
				}

				newImage.setPixel(x, y, total);
			}
		}
		// set Image buffer as image
		this.setPixels(newImage);
	}

	@Override
	public void grayscale() {

		// Preallocate the arrays.
		int[] rgb = new int[3];
		float[] hsb = new float[3];

		// Do the following for each pixel:
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				// Get the color from the image.
				getPixel(x, y, rgb);

				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);

				// Do whatever operation you’re supposed to do...
				hsb[1] = 0;

				// Convert back to RGB.
				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = c.getRed();
				rgb[1] = c.getGreen();
				rgb[2] = c.getBlue();

				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
	}

	@Override
	public void contrast(int amount) {

		// Preallocate the arrays.
		int[] rgb = new int[3];
		float[] hsb = new float[3];

		// Do the following for each pixel:
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				// Get the color from the image.
				getPixel(x, y, rgb);

				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);

				// Do whatever operation you’re supposed to do...
				hsb[2] = this.contrastCalculation(hsb[2], amount);

				if (hsb[2] > 1) {
					hsb[2] = 1;
				} else if (hsb[2] < 0) {
					hsb[2] = 0;
				}

				// Convert back to RGB.
				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = c.getRed();
				rgb[1] = c.getGreen();
				rgb[2] = c.getBlue();

				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
	}

	public float contrastCalculation(float r, float c) {
		return (float) ((Math.pow((c + 100) / 100, 4)) * (r - 0.5) + 0.5);
	}

	@Override
	public void brightness(int amount) {
		// Preallocate the arrays.
		int[] rgb = new int[3];
		float[] hsb = new float[3];

		// Do the following for each pixel:
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				// Get the color from the image.
				getPixel(x, y, rgb);

				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);

				// Do whatever operation you’re supposed to do...
				float hsbamount = amount;
				hsbamount = hsbamount / 100;
				hsb[2] += hsbamount;

				if (hsb[2] > 1) {
					hsb[2] = 1;
				} else if (hsb[2] < 0) {
					hsb[2] = 0;
				}
				// Convert back to RGB.
				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = c.getRed();
				rgb[1] = c.getGreen();
				rgb[2] = c.getBlue();

				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}

	}
}
