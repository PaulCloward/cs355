package cs355.view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import cs355.controller.State3D;
import cs355.model.drawing.Line;

public class MatrixStuff {

	private ArrayList<Line> lines = new ArrayList<>();
	
	private static MatrixStuff matrixStuff;
	private double[][] mainMatrix;
	private double[][] translationMatrix;
	private double[][] identityMatrix;
	private double[][] clippingMatrix;
	private double[][] rotationMatrix;
	private double[][] projectionMatrix;
	
	private float aspectRatio = State3D.DISPLAY_WIDTH/State3D.DISPLAY_HEIGHT;
	private float nearPlane = 1;
	private float farPlane = 100;
	private double fov = Math.toRadians(45);
	private double angleRadians = 0;
	
	private double x;
	private double y;
	private double z;
	
	private Color color = Color.GREEN;
	
	private MatrixStuff(){
		mainMatrix = new double[4][4];
		translationMatrix = new double[4][4];
		identityMatrix = new double[4][4];
		rotationMatrix = new double[4][4];
		projectionMatrix = new double [4][4];
		
		for(int i = 0; i < 4; i++) {
			translationMatrix[i][i] = 1;
			identityMatrix[i][i] = 1;
			rotationMatrix[i][i] = 1;
			projectionMatrix[i][i] = 1;
			mainMatrix[i][i] = 1;
		}
	
		projectionMatrix[3][2] = 1;
		projectionMatrix[3][3] = 0;
		
		//Sets up the clippingMatrix
		clippingMatrix = new double[4][4];
		double zoomY = 1/(Math.tan(fov/2));
		double zoomX = zoomY/aspectRatio;
		double fPlusN= nearPlane + farPlane;
		double fMinusN = farPlane - nearPlane;
		clippingMatrix[0][0] = zoomX;
		clippingMatrix[1][1] = zoomY;
		clippingMatrix[2][2] = fPlusN/fMinusN;
		clippingMatrix[2][3] = (-2*nearPlane*farPlane)/fMinusN;
		clippingMatrix[3][2] = 1;
	}
	
	public static MatrixStuff getSingleton() {
		if(matrixStuff == null) {
			matrixStuff = new MatrixStuff();
		}
		return matrixStuff;
		
	}
	
	public void setRotateY(double angleRadians) {
		System.out.println("setRotateY");
		this.angleRadians = -angleRadians;
		rotationMatrix[0][0] = Math.cos(this.angleRadians);
		rotationMatrix[0][2] = Math.sin(this.angleRadians);
		rotationMatrix[2][0] = -Math.sin(this.angleRadians);
		rotationMatrix[2][2] = Math.cos(this.angleRadians);
		updateMatrix();
	}
	
	/*public void addToRadianY(double angleRadians) {
		this.angleRadians += angleRadians;
		applyRotationY();
	}*/
	
//	private void applyRotationY() {
//		rotationMatrix[0][0] = Math.cos(this.angleRadians);
//		rotationMatrix[2][2] = Math.cos(this.angleRadians);
//		rotationMatrix[0][2] = Math.sin(this.angleRadians);
//		rotationMatrix[2][0] = -Math.sin(this.angleRadians);
//	}
	
	public void setTranslate(double x, double y, double z) {
		System.out.println("setTranslate");
		this.x = -x;
		this.y = -y;
		this.z = z;
		applyTranslation();
		updateMatrix();
	}
	
	public void addToTranslate(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		applyTranslation();
		
	}
	
	private void applyTranslation() {
		translationMatrix[0][3] = -x;
		translationMatrix[1][3] = -y;
		translationMatrix[2][3] = -z;
	}
	
	public void clearLines() {
		lines.clear();
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void addLine(double startX, double startY, double startZ, double endX, double endY, double endZ) {

		double[] start = new double[] {startX, startY, startZ, 1 };
		double[] end = new double[] {endX, endY, endZ, 1 };
		
		System.out.println("start 0 " + start[0]);
		System.out.println("start 1 " + start[1]);
		System.out.println("start 2 " + start[2]);
		System.out.println("end 0 " + end[0]);
		System.out.println("end 1 " + end[1]);
		System.out.println("end 2 " + end[2]);
		
		start = pointMatrixMultiply(mainMatrix, start);
		
		start[3] = 1;
		start = pointMatrixMultiply(clippingMatrix, start);
		for(int i = 0; i < 3; i++) {
			start[i] = start[i] / start[3];
		}
		
		end = pointMatrixMultiply(mainMatrix, end);
		end[3] = 1;
		end = pointMatrixMultiply(clippingMatrix,end);
		for(int i = 0; i < 3; i++) {
			end[i] = end[i] / end[3];
		}
		System.out.println("add line beginning");
		
		System.out.println("start 0 " + start[0]);
		System.out.println("start 1 " + start[1]);
		System.out.println("start 2 " + start[2]);
		System.out.println("end 0 " + end[0]);
		System.out.println("end 1 " + end[1]);
		System.out.println("end 2 " + end[2]);
		
		if(start[0] > 1 && end[0] > 1) {
			return;
		}
		if(start[0] < -1 && end[0] < -1) {
			return;
		}
		if(start[1] > 1 && end[1] > 1) {
			return;
		}
		if(start[1] < -1 && end[1] < -1) {
			return;
		}
		if(start[2] < -1 || end[2] < -1) {
			return;
		}
		if(start[2] > 1 || end[2] > 1) {
			return;
		}
		System.out.println("add line end");
		screenTransformation(start,end);
		
		Line line = new Line(color, new Point2D.Double(start[0], start[1])
				,new Point2D.Double(end[0], end[1]));
		lines.add(line);
		System.out.println("add lines: " + lines.size());
	}
	
	public ArrayList<Line> getLines() {
		System.out.println("Get lines: " + lines.size());
		return lines;
	}
	
	private double[][] matrixMultiply4x4(double[][] matrixOne, double[][] matrixTwo) {
		double[][] resultMatrix = new double[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 4; k++) {
					resultMatrix[i][j] += matrixOne[i][k] * matrixTwo[k][j];
				}
			}
		}
		return resultMatrix;
	}
	
	private void updateMatrix() {
		
		mainMatrix = matrixMultiply4x4(rotationMatrix,translationMatrix);
		mainMatrix = matrixMultiply4x4(projectionMatrix, mainMatrix);
	}
	
	private double[] pointMatrixMultiply(double[][] matrix, double[] point) {
		
		double[] result = new double[4];
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				result[i] += matrix[i][j] * point[j]; 
			}
		}
		
		return result;
	}
	
	private void screenTransformation(double[] start, double[] end) {
		
		start[0] = start[0] * (State3D.DISPLAY_WIDTH / 2) + State3D.DISPLAY_WIDTH / 2;
		start[1] = start[1] * (-State3D.DISPLAY_HEIGHT / 2) + State3D.DISPLAY_HEIGHT / 2;
		
		end[0] = end[0] * (State3D.DISPLAY_WIDTH / 2) + State3D.DISPLAY_WIDTH / 2;
		end[1] = end[1] * (-State3D.DISPLAY_HEIGHT / 2) + State3D.DISPLAY_HEIGHT / 2;
	}
}
