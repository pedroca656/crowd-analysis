package com.pucrs.viewer;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import com.jogamp.opengl.*;
import com.pucrs.parsing.Parser;
import com.pucrs.parsing.Person;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.List;

/**
 * A template for a basic JOGL application with support for animation and for
 * keyboard and mouse event handling.  To enable the support, uncomment the
 * appropriate lines in the init() method.  As an example, the program draws
 * the GLUT teapot.
 */
public class ViewTwo extends JPanel implements
        GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {
    private List<Person> personList;

    private Float rotX = 90f, rotY = 90f, rotX_ini, rotY_ini;
    private Float obsX = 500f, obsY = 500f, obsZ = 400f, obsX_ini, obsY_ini, obsZ_ini;
    private Float fAspect = 1f, angle = 44f;
    int x_ini, y_ini;
    private float posx = 500f, posy = 500f, posz = 0f;

    private Camera camera;

    private static final Float SCALER = 5f;
    private static final Float SENS_ROT = 5f;
    private static final Float SENS_OBS = 10f;
    private static final Float SENS_TRANSL = 30f;

    private GL2 gl;
    private GLU glu = new GLU();
    private GLJPanel display;
    private Timer animationTimer;
    private float rotateX, rotateY;   // rotation amounts about axes, controlled by keyboard

    public ViewTwo() {
        JFrame window = new JFrame("JOGL");
        window.setContentPane(this);
        window.pack();
        window.setLocation(50, 50);
        window.setSize(1000, 1000);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.setPreferredSize(new Dimension(1000, 1000));  // TODO: set display size here
        display.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(display, BorderLayout.CENTER);

        personList = Parser.personList;

        // TODO:  Other components could be added to the main panel.

        rotateX = 15;  // initialize some variables used in the drawing.
        rotateY = 15;

        // TODO:  Uncomment the next two lines to enable keyboard event handling
        requestFocusInWindow();
        addKeyListener(this);

        // TODO:  Uncomment the next one or two lines to enable mouse event handling
        display.addMouseListener(this);
        display.addMouseMotionListener(this);

        //TODO:  Uncomment the following line to start the animation
        startAnimation();

    }

    // ---------------  Methods of the GLEventListener interface -----------

    private GLUT glut = new GLUT();  // for drawing the teapot

    /**
     * This method is called when the OpenGL display needs to be redrawn.
     */
    public void display(GLAutoDrawable drawable) {
        // called when the panel needs to be drawn

        GL2 gl = drawable.getGL().getGL2();
        this.gl = gl;

        camera = new Camera();
        camera.apply(gl);

        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);  // TODO: Set up a better projection?
        gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -2, 2);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glLoadIdentity();             // Set up modelview transform.
        gl.glRotatef(rotateY, 0, 1, 0);
        gl.glRotatef(rotateX, 1, 0, 0);

        // TODO: add drawing code!!
        visualization();

        gl.glColor3f(255f, 0.0f, 0.0f);

        gl.glPushMatrix();
        gl.glTranslatef(posx, 5f, posy );

        glut.glutSolidCube(10);
        //gl.glTranslatef(posx, posy, posz);

        gl.glPopMatrix();

        drawFloor();

        carregaPessoas();

        gl.glFlush();

    }

    private void carregaPessoas() {
        //for (int j = 0; j < 100; j++) {
            gl.glColor3f(0f, 0f, 255f);
            for (int i = 0; i < personList.size() - 1; i++) {
                gl.glPushMatrix();
                gl.glTranslatef(personList.get(i).getCoordsList().get(0).getX(), 5f, personList.get(i).getCoordsList().get(0).getY());

                if(i > (personList.size() - 1) / 3 ) glut.glutSolidCube(10);
                else glut.glutSolidSphere(10, 1, 1);

                gl.glPopMatrix();


            }
        //}
    }

    /**
     * This is called when the GLJPanel is first created.  It can be used to initialize
     * the OpenGL drawing context.
     */
    public void init(GLAutoDrawable drawable) {
        // called when the panel is created
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.8F, 0.8F, 0.8F, 1.0F);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
    }

    /**
     * Called when the size of the GLJPanel changes.  Note:  glViewport(x,y,width,height)
     * has already been called before this method is called!
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    /**
     * This is called before the GLJPanel is destroyed.  It can be used to release OpenGL resources.
     */
    public void dispose(GLAutoDrawable drawable) {
    }


    // ------------ Support for keyboard handling  ------------

    /**
     * Called when the user presses any key.
     */

    private Boolean onlyCamera = false;
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        if(key == KeyEvent.VK_P){
            if(onlyCamera){
                onlyCamera = false;
                obsX = posy;
                obsY = posx;
                obsZ = 400f;
                startAnimation();
            }
            else {
                onlyCamera = true;
                pauseAnimation();
            }
        }
        if(key == KeyEvent.VK_MINUS && onlyCamera){
            obsZ += 100f;
        }
        if (key == KeyEvent.VK_LEFT) {
            obsX -= 15f;
            if(!onlyCamera) {
                posy -= 15f;
            }
        }
        else if (key == KeyEvent.VK_RIGHT) {
            obsX += 15f;
            if(!onlyCamera) {
                posy += 15f;
            }
        }
        else if (key == KeyEvent.VK_DOWN) {
            obsY -= 15f;
            if(!onlyCamera) {
                posx -= 15f;
            }
        }
        else if (key == KeyEvent.VK_UP) {
            obsY += 15f;
            if(!onlyCamera) {
                posx += 15f;
            }
        }
        else if (key == KeyEvent.VK_HOME) {
            obsY = obsY_ini;
            if(!onlyCamera) {
                obsX = obsX_ini;
            }
        }
        //repaint();
    }

    /**
     * Called when the user types a character.
     */
    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();  // Which character was typed.
    }

    public void keyReleased(KeyEvent e) {
    }

    // --------------------------- animation support ---------------------------

    private int frameNumber = 0;

    private boolean animating;

    private void updateFrame() {
        // TODO:  add any other updating required for the next frame.
        //desenhaPessoas(frameNumber);
        frameNumber++;
    }

    public void startAnimation() {
        if (!animating) {
            if (animationTimer == null) {
                animationTimer = new Timer(30, this);
            }
            animationTimer.start();
            animating = true;
        }
    }

    public void pauseAnimation() {
        if (animating) {
            animationTimer.stop();
            animating = false;
        }
    }

    public void actionPerformed(ActionEvent evt) {
        updateFrame();
        display.repaint();
    }


    // ---------------------- support for mouse events ----------------------

    private boolean dragging;  // is a drag operation in progress?

    private int startX, startY;  // starting location of mouse during drag
    private int prevX, prevY;    // previous location of mouse during drag

    /**
     * Called when the user presses a mouse button on the display.
     */
    public void mousePressed(MouseEvent e) {
        //pressionado = true;
    }

    private boolean pressionado = false;
    /**
     * Called when the user releases a mouse button after pressing it on the display.
     */
    public void mouseReleased(MouseEvent evt) {
        if (!dragging) {
            //pressionado = false;
            return;
        }
        dragging = false;
        // TODO:  finish drag (generally nothing to do here)
    }

    /**
     * Called during a drag operation when the user drags the mouse on the display/
     */
    public void mouseDragged(MouseEvent e) {

    }


    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }


    private void setCamera() {
        //System.out.println("obsX: " + obsX + ", obsY; " + obsY + ", obsZ: " + obsZ);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(-obsX, -obsY, -obsZ);
        gl.glRotatef(rotX, 1, 0, 0);
        gl.glRotatef(rotY, 0, 1, 0);
        camera.lookAt(obsX, obsY, obsZ, posx, posy, posz, 0.0, 1.0, 0.0);
    }

    private void drawFloor() {
        gl.glColor3f(0, 0, 1);
        gl.glLineWidth(10f);
        gl.glBegin(gl.GL_LINES);
        for (float z = 0; z <= 2000; z += 10) {
            gl.glVertex3f(0000, -0.1f, z);
            gl.glVertex3f(2000, -0.1f, z);
        }
        for (float x = 0; x <= 2000; x += 10) {
            gl.glVertex3f(x, -0.1f, 0000);
            gl.glVertex3f(x, -0.1f, 2000);
        }
        gl.glEnd();
        gl.glLineWidth(1);
    }

    private void visualization() {
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(angle, fAspect, 0.5, 500);
        setCamera();
    }

}