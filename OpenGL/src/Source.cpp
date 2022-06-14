#include <windows.h>
#include <GL/glut.h>
#include <GL/glext.h>
#include <cmath>
#define GL_PI 3.1415f



GLint width = 1000;
GLint height = 800;
GLint nRange = 100;

unsigned int buffer;
GLfloat squadY = 0.0f;
GLfloat fovSquad = 0.0f;
GLfloat fov = 0.0f;
GLfloat angle = 0.0f;

void RenderScene(void) {
	// Light values and coordinate

	static GLfloat corners[] = {1.0f, 1.0f, 1.0f,
	                            -1.0f, 1.0f, 1.0f,
								-1.0f, -1.0f, 1.0f,
								1.0f, -1.0f, 1.0f};


	/*static GLfloat corners[] = { -25.0f, 25.0f, 25.0f, // 0 // Front of cube
			25.0f, 25.0f, 25.0f,
			//
			25.0f, -25.0f, 25.0f,// 2
			-25.0f, -25.0f, 25.0f,// 3
			-25.0f, 25.0f, -25.0f,// 4  // Back of cube
			25.0f, 25.0f, -25.0f,// 5
			25.0f, -25.0f, -25.0f,// 6
			-25.0f, -25.0f, -25.0f };// 7
*/
	static GLubyte indexes[4] = {0,1,2,3};


	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(0, 0, 0, 0);
	glPolygonMode(GL_FRONT_AND_BACK, GL_DEPTH);


	glMatrixMode(GL_MODELVIEW);




	glPushMatrix();
	glScalef(10, 10, 10);
	glColor3f(1, 1, 1);
	glEnableClientState(GL_VERTEX_ARRAY);
	glTranslatef(0, 0, fov);
	glRotatef(angle,1,1,0);
	glVertexPointer(3, GL_FLOAT, 0, corners);
	glDrawArrays(GL_QUADS, 0, 4);
	glDisableClientState(GL_VERTEX_ARRAY);
	glPopMatrix();

	glutSwapBuffers();
}






/*void TimerFunction(int value){


	glutPostRedisplay();
	glutTimerFunc(33, TimerFunction, 1);
}
*/
void changeSize(GLsizei w, GLsizei h) {
	GLfloat aspect;

	if (h == 0) {
		h = 1;
	}

	glViewport(0, 0, w, h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();



	aspect = w / h;
	gluPerspective(67, aspect, 0.1, 400);


	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}


void timer(int i){
	glutTimerFunc(33, timer, 0);
	glutPostRedisplay();
}

void specialKeys(int key, int x, int y){
	if(key == GLUT_KEY_LEFT){
		angle--;
	}
	if(key == GLUT_KEY_RIGHT){
		angle++;
	}
	if(key == GLUT_KEY_DOWN){
			fov--;
	}

	if(key == GLUT_KEY_UP){
			fov++;
	}
	if(key == GLUT_KEY_F1){
		squadY--;
	}
	if(key == GLUT_KEY_F2){
		squadY++;
	}
	if(key == GLUT_KEY_F3){
		fovSquad--;
	}
	if(key == GLUT_KEY_F4){
		fovSquad++;
	}


}

void setup(){
	GLfloat lightPosition[] = {1.0f, 0.1f, 0.0f, 1.0f};
	GLfloat  specular[] = { 0.1f, 1.0f, 1.0f, 1.0f};
	GLfloat ambient[] = {0, 0, 0, 1.0f};

	glEnable(GL_DEPTH_TEST);
	glFrontFace(GL_CCW);
	glEnable(GL_CULL_FACE);


	glEnable(GL_LIGHTING);

	glLightfv(GL_LIGHT0, GL_AMBIENT, ambient);
	glLightfv(GL_LIGHT0, GL_SPECULAR, specular);
	glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);
	glEnable(GL_LIGHT0);


	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT);

	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ambient);
	glMateriali(GL_FRONT_AND_BACK, GL_SHININESS, 128);


	glEnable(GL_NORMALIZE);

}

int main(int argc, char *argv[]) {


	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH | GLUT_ACCUM);
	glutCreateWindow("Medieval");
	glutInitWindowSize(width, height);
	glutTimerFunc(33, timer, 0);
	glutReshapeFunc(changeSize);
	setup();
	glutDisplayFunc(RenderScene);
	glutSpecialFunc(specialKeys);

	glutMainLoop();

	return 0;
}

