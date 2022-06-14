package com.lwjgl;

import static org.lwjgl.opengl.GL21.*;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL21;

import com.lwjgl.camera.Camera;
import com.lwjgl.entity.Player;
import com.lwjgl.graphics.ShaderProgram;
import com.lwjgl.math.Vector3f;
import com.lwjgl.util.FileReader;
import com.lwjgl.window.Window;

public class Main {

	ShaderProgram shaderProgram = null;
	int width = 600;
	int height = 800;
	String title = "Winter Fantasy";
	float angle = 0.0f;
	float pov = 0.0f;
	Player player = new Player(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	Window window = new Window(width, height, title);
	Camera camera = new Camera(window);
	float[] lightArray = { 1.0f, 1.0f, 1.0f, 1 };
    int[] indicesArray = new int[4];
	float[] verticesArray = 
		{ 0.1f, 0.1f,  
				1, -1, 
				-1f, 1f,  
				1f, -1f,};
	float[] triangleArray = {1.0f,0.0f, 0.0f,
			                 -1.0f, 0.0f,0.0f,
			                 0.0f, 1.0f, 0.0f};
	float[] colorArray = {1,1,1,
			              0,1,0,
			              0,1,1,
			              0,0,1,
			              1,0,0,
			              1,0,1,
			              1,1,0,
			              1,1,1,
			              1,1,1,
			              1,1,1,
			              1,1,1,
			              1,1,1
			              };
	
	
	int vbo = 0;
	int ibo = 0;
	//int cbo = 0;
	
	
	
	public void run() {
		window.run();
		window.createCapabilites();
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(FileReader.loadResource("/shaders/vertex.vs"));
		shaderProgram.createFragmentShader(FileReader.loadResource("/shaders/fragment.fs"));
		shaderProgram.link();
	
		for(int i = 0; i < 4; i++) {
			indicesArray[i] = i;
		}
		FloatBuffer light = BufferUtils.createFloatBuffer(lightArray.length);
		FloatBuffer vertices = BufferUtils.createFloatBuffer(verticesArray.length);
		FloatBuffer position = BufferUtils.createFloatBuffer(4);
		FloatBuffer color = BufferUtils.createFloatBuffer(colorArray.length);
		IntBuffer indices = BufferUtils.createIntBuffer(indicesArray.length);
		
		
		light.put(lightArray).flip();
		position.put(new float[] { 0f, 0f, 0f, 1f, });
		position.flip();
		vertices.put(verticesArray).flip();
		indices.put(indicesArray).flip();
		color.put(colorArray).flip();

		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glFrustum(-1.0, 1.0, -1.0, 1.0, 1, 400);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT, light);
		glLightfv(GL_LIGHT0, GL_POSITION, position);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
	    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        ibo = glGenBuffers();
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		
		System.err.println(GL11.glGetString(GL_VERSION));
		System.err.println(GL21.glGetString(GL_VERSION));
		


		while (!window.isWindowShouldClose()) {
			glClearColor(0, 0, 0, 0);
			glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
			glPolygonMode(GL_FRONT_AND_BACK, GL_DEPTH);
			window.pollEvents();

			if (window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
				angle++;
			}
			if (window.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
				angle--;
			}
			if (window.isKeyPressed(GLFW.GLFW_KEY_UP)) {
				pov++;
			}
			if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
				pov--;
			}


			glEnableClientState(GL_VERTEX_ARRAY);
			
		    glBindBuffer( GL_ARRAY_BUFFER, vbo );
		    glVertexPointer(2, GL_FLOAT, 0, org.lwjgl.system.MemoryUtil.NULL);
		    
		    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		    glColor3f(1,1, 1);
		    glTranslatef(0, 0, pov);
		    glRotatef(angle,0,1,0);
		    glDrawElements(GL_QUADS, indices);
		    
		    glDisableClientState(GL_VERTEX_ARRAY);
			
			/*glPushMatrix();
			glEnableClientState(GL_VERTEX_ARRAY);
			glTranslatef(0, 0, pov);
			glColor3f(1, 1, 1);
			glBindBuffer(GL_VERTEX_ARRAY, vbo);
			glVertexPointer(3, GL_FLOAT, 0, triangle);
			this.shaderProgram.bind();
			glDrawArrays(GL_TRIANGLES, 0, 3);
			this.shaderProgram.unbind();
			glDisableClientState(GL_VERTEX_ARRAY);
			glPopMatrix();
			*/
			
			/*
			 * glEnableClientState(GL_VERTEX_ARRAY); glTranslatef(0, 0, pov);
			 * glRotatef(angle, 1, 0, 0); glColor3f(1, 1, 1); glVertexPointer(3, GL_FLOAT,
			 * 0, BufferUtils.createFloatBuffer(vertices.length).put(vertices));
			 * glDrawArrays(GL_QUADS, 0, 4);
			 * 
			 * glDisableClientState(GL_VERTEX_ARRAY);
			 */
			/*glPushMatrix();
			glTranslatef(0,0,pov);
			glRotatef(angle, 0, 1, 0);
			glBegin(GL_QUADS);
			glVertex3f(1,0,0);
			glVertex3f(1,1,0);
			glVertex3f(-1,1,0);
			glVertex3f(-1,0,0);

			glPopMatrix();*/

			window.swapBuffers();
		}
	}
	
	
	public void createTextureFromMemory(String path) {
	
		BufferedImage texture = null; 
		try {
			texture = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void createPlayer(Player player) {

	}

	public static void main(String[] args) {
		new Main().run();
	}
}
