package CS355.LWJGL;


//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your milage may vary. Don't feel restricted by this list of imports.
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController 
{
  
	
  //This is a model of a house.
  //It has a single method that returns an iterator full of Line3Ds.
  //A "Line3D" is a wrapper class around two Point2Ds.
  //It should all be fairly intuitive if you look at those classes.
  //If not, I apologize.
  private WireFrame model = new HouseModel();
  private float aspectRatio = LWJGLSandbox.DISPLAY_WIDTH/LWJGLSandbox.DISPLAY_HEIGHT;
  private float nearPlane = 2;
  private float farPlane = 800;
  private float fov = 45;
  
  private float x = 0;
  private float y = -2;
  private float z = -50;
  
  private float angleDegrees = 0;
  private double angleRadians = Math.toRadians(angleDegrees);
  
  //This method is called to "resize" the viewport to match the screen.
  //When you first start, have it be in perspective mode.
  @Override
  public void resizeGL() 
  {
	  
	  glMatrixMode(GL_PROJECTION);
	  glLoadIdentity();
	  gluPerspective(fov, aspectRatio, nearPlane, farPlane);
  }

    @Override
    public void update() 
    {
        
    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    @Override
    public void updateKeyboard() 
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) 
        {
            System.out.println("You are pressing A!");
            x += Math.cos(angleRadians);
            z += Math.sin(angleRadians);
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
        	
        	System.out.println("You are pressing D!");
        	x -= Math.cos(angleRadians);
        	z -= Math.sin(angleRadians);
        }
		else if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			
			System.out.println("You are pressing W!");
			x -= Math.sin(angleRadians);
			z += Math.cos(angleRadians);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			
			System.out.println("You are pressing S!");
			x += Math.sin(angleRadians);
			z -= Math.cos(angleRadians);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			
			System.out.println("You are pressing Q!");
			angleDegrees--;
			angleRadians = Math.toRadians(angleDegrees);
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
					
			System.out.println("You are pressing E!");
			angleDegrees++;
			angleRadians = Math.toRadians(angleDegrees);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
			
			System.out.println("You are pressing R!");
			y--;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
			
			System.out.println("You are pressing F!");
			y++;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_H)) {
			
			System.out.println("You are pressing H!");
			x = 0;
			y = -2;
			z = -50;
			angleDegrees = 0;
			angleRadians = Math.toRadians(angleDegrees);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
			
			System.out.println("You are pressing O!");
			glMatrixMode(GL_PROJECTION);
			  glLoadIdentity();
			  glOrtho(-15, 15, -15, 15, nearPlane, farPlane);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
			
			System.out.println("You are pressing P!");
			resizeGL();
		}
    }

    //This method is the one that actually draws to the screen.
    @Override
    public void render() 
    {
    	glMatrixMode(GL_MODELVIEW);//camera
    	glLoadIdentity();
    	glRotatef(angleDegrees,0,1,0);
    	glTranslatef(x, y, z);//move the camera
    	
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT);
        
        for(int i = 0; i < 10; i++) {
        
        	glPushMatrix();
        	
        	glTranslatef(15 * i, 0,0);
        	
		    glBegin(GL_LINES);
		    glColor3f(173,0,76);
		    
		    //Do your drawing here.
		    Iterator<Line3D> lines = model.getLines();
		    
		    while(lines.hasNext()) {
		    	
		    	Line3D line = lines.next();
		    	glVertex3d(line.start.x,line.start.y,line.start.z);
		    	glVertex3d(line.end.x,line.end.y,line.end.z);
		    }
		    
		    glEnd();
		    
		    glPopMatrix();
        }
        
        glRotatef(180,0,1,0);
        
        for(int i = 0; i < 10; i++) {
            
        	glPushMatrix();
        	
        	glTranslatef(-15 * i, 0, -30);
        	
		    glBegin(GL_LINES);
		    glColor3f(173,0,76);
		    
		    //Do your drawing here.
		    Iterator<Line3D> lines = model.getLines();
		    
		    while(lines.hasNext()) {
		    	
		    	Line3D line = lines.next();
		    	glVertex3d(line.start.x,line.start.y,line.start.z);
		    	glVertex3d(line.end.x,line.end.y,line.end.z);
		    }
		    
		    glEnd();
		    
		    glPopMatrix();
        }
    }
}
