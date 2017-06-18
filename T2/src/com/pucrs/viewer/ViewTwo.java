package com.pucrs.viewer;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import com.jogamp.opengl.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A template for a basic JOGL application with support for animation and for
 * keyboard and mouse event handling.  To enable the support, uncomment the
 * appropriate lines in the init() method.  As an example, the program draws
 * the GLUT teapot.
 */
public class ViewTwo extends JPanel implements
        GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {

    private Float rotX = 0f, rotY = 0f, rotX_ini, rotY_ini;
    private Float obsX = 0f, obsY = 0f, obsZ = 200f, obsX_ini, obsY_ini, obsZ_ini;
    private Float fAspect = 1f, angle = 44f;
    int x_ini, y_ini;

    private static final Float SENS_ROT = 5f;
    private static final Float SENS_OBS = 10f;
    private static final Float SENS_TRANSL = 30f;

    private GL2 gl;
    private GLU glu = new GLU();
    private GLJPanel display;
    private Timer animationTimer;
    private float rotateX, rotateY;   // rotation amounts about axes, controlled by keyboard

    public static void main(String[] args) {
        JFrame window = new JFrame("JOGL");
        window.setContentPane(new ViewTwo());
        window.pack();
        window.setLocation(50, 50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    public ViewTwo() {
        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.setPreferredSize(new Dimension(600, 600));  // TODO: set display size here
        display.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(display, BorderLayout.CENTER);
        // TODO:  Other components could be added to the main panel.

        rotateX = 15;  // initialize some variables used in the drawing.
        rotateY = 15;

        // TODO:  Uncomment the next two lines to enable keyboard event handling
        //requestFocusInWindow();
        //addKeyListener(this);

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
        EspecificaParametrosVisualizacao();

        gl.glColor3f(0.0f, 0.0f, 1.0f);

        gl.glPushMatrix();
        gl.glTranslatef(0,28,0);

        glut.glutWireTeapot(35);

        gl.glPopMatrix();

        desenhaChao();

        gl.glFlush();

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
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        if (key == KeyEvent.VK_LEFT)
            rotateY -= 15;
        else if (key == KeyEvent.VK_RIGHT)
            rotateY += 15;
        else if (key == KeyEvent.VK_DOWN)
            rotateX += 15;
        else if (key == KeyEvent.VK_UP)
            rotateX -= 15;
        else if (key == KeyEvent.VK_HOME)
            rotateX = rotateY = 0;
        repaint();
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
    public void mousePressed(MouseEvent evt) {
        if (dragging) {
            return;  // don't start a new drag while one is already in progress
        }
        int x = evt.getX();
        int y = evt.getY();
        // TODO: respond to mouse click at (x,y)
        dragging = true;  // might not always be correct!
        prevX = startX = x;
        prevY = startY = y;
        display.repaint();    //  only needed if display should change
        System.out.println("mousePressed ended");
    }

    /**
     * Called when the user releases a mouse button after pressing it on the display.
     */
    public void mouseReleased(MouseEvent evt) {
        if (!dragging) {
            return;
        }
        dragging = false;
        // TODO:  finish drag (generally nothing to do here)
    }

    /**
     * Called during a drag operation when the user drags the mouse on the display/
     */
    public void mouseDragged(MouseEvent evt) {
        if (!dragging) {
            return;
        }
        int x = evt.getX();
        int y = evt.getY();
        // TODO:  respond to mouse drag to new point (x,y)
        prevX = x;
        prevY = y;
        display.repaint();
        System.out.println("mouseDragged ended");
    }

    public void mouseMoved(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
        System.out.println("clicked!");
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }


    void PosicionaObservador() {
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(-obsX, -obsY, -obsZ);
        gl.glRotatef(rotX, 1, 0, 0);
        gl.glRotatef(rotY, 0, 1, 0);
        glu.gluLookAt(0.0, 80.0, 200.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
    }

    void desenhaChao() {
        gl.glColor3f(1, 0, 1);
        gl.glLineWidth(3);
        gl.glBegin(gl.GL_LINES);
        for (float z = -1000; z <= 1000; z += 10) {
            gl.glVertex3f(-1000, -0.1f, z);
            gl.glVertex3f(1000, -0.1f, z);
        }
        for (float x = -1000; x <= 1000; x += 10) {
            gl.glVertex3f(x, -0.1f, -1000);
            gl.glVertex3f(x, -0.1f, 1000);
        }
        gl.glEnd();
        gl.glLineWidth(1);
    }

    void EspecificaParametrosVisualizacao() {
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(angle,fAspect,0.5,500);
        PosicionaObservador();
    }

}