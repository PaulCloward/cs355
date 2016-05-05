package cs355.controller;

import java.awt.Color;
import java.util.Iterator;

import cs355.model.drawing.*;
import cs355.view.MatrixStuff;

public class State3D {
	
	public static final int DISPLAY_HEIGHT = 2048;
	  public static final int DISPLAY_WIDTH = 2048;
	
	  private WireFrame model = new HouseModel();
	
	  private final float xHome = 0;
	  private final float yHome = 0;
	  private final float zHome = -50;
	  
	  
	  private float x = xHome;
	  private float y = yHome;
	  private float z = zHome;
	  
	  private float angleDegrees = 0;
	  private double angleRadians = Math.toRadians(angleDegrees);
	  
	  private MatrixStuff matrixStuff;
	  
	  //This method is called to "resize" the viewport to match the screen.
	  //When you first start, have it be in perspective mode.
	  public State3D() 
	  {
		  
		 // glMatrixMode(GL_PROJECTION);
		 // glLoadIdentity();
		  //(fov, aspectRatio, nearPlane, farPlane);
		  matrixStuff = MatrixStuff.getSingleton();
	  }

	    //This is called every frame, and should be responsible for keyboard updates.
	    //An example keyboard event is captured below.
	    //The "Keyboard" static class should contain everything you need to finish
	    // this up.
	    
	    public void keyPressed(Iterator<Integer> iterator) 
	    {
	    	
	    	
	    	
	    	
	    	while(iterator.hasNext()) {
	    		char key = (char)iterator.next().intValue();
	    	
	        if(key == 'A') 
	        {
	            System.out.println("You are pressing A!");
	            x += Math.cos(angleRadians);
	            z += Math.sin(angleRadians);
	        }
	        else if(key == 'D') {
	        	
	        	System.out.println("You are pressing D!");
	        	x -= Math.cos(angleRadians);
	        	z -= Math.sin(angleRadians);
	        }
			else if(key == 'W') {
				
				System.out.println("You are pressing W!");
				x -= Math.sin(angleRadians);
				z += Math.cos(angleRadians);
			}
			else if(key == 'S') {
				
				System.out.println("You are pressing S!");
				x += Math.sin(angleRadians);
				z -= Math.cos(angleRadians);
			}
			else if(key == 'Q') {
				
				System.out.println("You are pressing Q!");
				angleDegrees--;
				angleRadians = Math.toRadians(angleDegrees);
				
			}
			else if(key == 'E') {
						
				System.out.println("You are pressing E!");
				angleDegrees++;
				angleRadians = Math.toRadians(angleDegrees);
			}
			else if(key == 'R') {
				
				System.out.println("You are pressing R!");
				y--;
			}
			else if(key == 'F') {
				
				System.out.println("You are pressing F!");
				y++;
			}
			else if(key == 'H') {
				
				System.out.println("You are pressing H!");
				x = 0;
				y = -2;
				z = -50;
				angleDegrees = 0;
				angleRadians = Math.toRadians(angleDegrees);
			}
			/*else if(key == 'O') {
				
				System.out.println("You are pressing O!");
				glMatrixMode(GL_PROJECTION);
				  glLoadIdentity();
				  glOrtho(-15, 15, -15, 15, nearPlane, farPlane);
			}
			else if(key == 'P') {
				
				System.out.println("You are pressing P!");
				resizeGL();
			}*/
	    	}
	        render();
	    }
	    
	    public void render()  {
	    	matrixStuff.setRotateY(angleRadians);
	    	matrixStuff.setTranslate(x, y, z);
	    	
	        matrixStuff.clearLines();
	        matrixStuff.setColor(Color.GREEN);
	        System.out.println("render");
	        
	        Iterator<Line3D> lines = model.getLines();
		   
		    while(lines.hasNext()) {
		    	
		    	Line3D line = lines.next();
		    	matrixStuff.addLine(line.start.x, line.start.y, line.start.z, line.end.x, line.end.y, line.end.z);
		    }
	    }
	    
	    public void clear() {
	    	matrixStuff.clearLines();
	    }

	  /*  public void renderExtraCredit() 
	    {
	   // 	glMatrixMode(GL_MODELVIEW);//camera
	    //	glLoadIdentity();
	    	//glRotatef(angleDegrees,0,1,0);
	    	//glTranslatef(x, y, z);//move the camera
	    	
	    	matrixStuff.setRotateY(angleRadians);
	    	matrixStuff.setTranslate(x, y, z);
	    	
	    	
	        //This clears the screen.
	        matrixStuff.clearLines();
	        
	        for(int i = 0; i < 30; i++) {
	        
	        	//glPushMatrix();
	        	matrixStuff.addToTranslate(15*i, 0, 0);
	        	
			   // glBegin(GL_LINES);
			   // glColor3f(173,0,76);
			    
			    //Do your drawing here.
			    Iterator<Line3D> lines = model.getLines();
			    
			    while(lines.hasNext()) {
			    	
			    	Line3D line = lines.next();
			    //	glVertex3d(line.start.x,line.start.y,line.start.z);
			    //  glVertex3d(line.end.x,line.end.y,line.end.z);
			    	matrixStuff.addLine(line.start.x, line.start.y, line.start.z, line.end.x, line.end.y, line.end.z);
			    }
			    
			  //  glEnd();
			    
			 //   glPopMatrix();
	        }
	        
	       // glRotatef(180,0,1,0);
	        matrixStuff.addToRadianY(Math.toRadians(100));
	        
	        for(int i = 0; i < 30; i++) {
	            
	        	//glPushMatrix();
	        	
	        	//glTranslatef(-15 * i, 0, -30);
	        	
			  //  glBegin(GL_LINES);
			  //  glColor3f(173,0,76);
			    
			    //Do your drawing here.
			    Iterator<Line3D> lines = model.getLines();
			    
			    while(lines.hasNext()) {
			    	
			    	Line3D line = lines.next();
			  //  	glVertex3d(line.start.x,line.start.y,line.start.z);
			    //	glVertex3d(line.end.x,line.end.y,line.end.z);
			    }
			    
			  //  glEnd();
			    
			   // glPopMatrix();
	        }
	        
	    }	        */
}
