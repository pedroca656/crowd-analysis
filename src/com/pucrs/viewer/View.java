package com.pucrs.viewer;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.pucrs.parsing.Tuple;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class View extends JFrame implements GLEventListener {
    private List<List<Tuple>> peopleMatrix;
    private int totalFrames;
    private Float pixelsToMeters;

    final private int width = 1100;
    final private int height = 900;

    public View(List<List<Tuple>> peopleMatrix, int totalFrames, Float pixelsToMeters) {
        super("Crowd Viewer Analysis");

        this.peopleMatrix = peopleMatrix;
        this.totalFrames = totalFrames;
        this.pixelsToMeters = pixelsToMeters;

        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

        this.setName("Minimal OpenGL");
        this.getContentPane().add(canvas);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        canvas.requestFocusInWindow();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        Random rand = new Random(255);

        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0f, 3.8f, 4.8f, 0.0f, 0.0f, 1.0f);
        gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);

        Float xCoord;
        Float yCoord;

        for (int j = 0; j < peopleMatrix.size(); j++) {
            gl.glColor3f(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            for (int i= 0; i < totalFrames; i++) {
                if (peopleMatrix.get(j).get(i) != null) {

                    xCoord = peopleMatrix.get(j).get(i).getX()/333; // division for scaling
                    yCoord = peopleMatrix.get(j).get(i).getY()/333; // division for scaling


                    gl.glBegin (gl.GL_LINES);
                    // drawing left side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord-0.01f), (yCoord-0.01f), 0);
                    gl.glVertex3f((xCoord-0.01f), (yCoord+0.01f), 0);
                    gl.glEnd();
                    // drawing top side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord-0.01f), (yCoord+0.01f), 0);
                    gl.glVertex3f((xCoord+0.01f), (yCoord+0.01f), 0);
                    gl.glEnd();
                    // drawing right side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord+0.01f), (yCoord+0.01f), 0);
                    gl.glVertex3f((xCoord+0.01f), (yCoord-0.01f), 0);
                    gl.glEnd();
                    // drawing bottom side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord+0.01f), (yCoord-0.01f), 0);
                    gl.glVertex3f((xCoord-0.01f), (yCoord-0.01f), 0);
                    gl.glEnd();
                    gl.glFlush();
                }
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
