package com.lwjgl.graphics;

import static org.lwjgl.opengl.GL21.*;

import com.lwjgl.util.FileReader;

public class ShaderProgram {
	
	private final int programId;
	
	private int vertexShaderId;
	
	private int fragmentShaderId;
	
	
	
	public ShaderProgram(){
		programId = glCreateProgram();
		if(programId == 0){
			System.err.println("Cannot create program");
		}
	}
	
	
	public int createShader(String shaderCode,int type) {
		int shaderId = glCreateShader(type);
		
		
		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);
		
		
		if(shaderId == 0) {
			//exception
		}
		
		glAttachShader(this.programId, shaderId);
		
		return shaderId;
	}

	public void createFragmentShader(String code) {
		this.fragmentShaderId = createShader(code, GL_FRAGMENT_SHADER);
	}
	
	public void createVertexShader(String code) {
		this.vertexShaderId = createShader(code, GL_VERTEX_SHADER);
	}
	
	public void link() {
		glLinkProgram(programId);
		
		
		if(this.vertexShaderId != 0) {
			glDetachShader(programId, vertexShaderId);
		}
		if(this.fragmentShaderId != 0) {
			glDetachShader(programId, fragmentShaderId);
		}
		
		glValidateProgram(programId);
	}
	
	public void bind() {
		glUseProgram(programId);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void cleanup() {
		unbind();
		if(this.programId != 0) {
			glDeleteProgram(programId);
		}
	}
}
