package com.pucrs.viewer;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.awt.*;

/**
 * Created by wos on 05/04/17.
 */
public class View extends JFrame implements GLEventListener {

    final private int width = 800;
    final private int height = 600;

    public View() {
        super("Crowd Viewer Analysis");

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


        gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);

        gl.glBegin (gl.GL_LINES);
        //drawing the base
        gl.glBegin (gl.GL_LINES);
        gl.glVertex3f(-0.50f, -0.50f, 0);
        gl.glVertex3f(0.50f, -0.50f, 0);
        gl.glEnd();
        //drawing the right edge
        gl.glBegin (gl.GL_LINES);
        gl.glVertex3f(0f, 0.50f, 0);
        gl.glVertex3f(-0.50f, -0.50f, 0);
        gl.glEnd();
        //drawing the lft edge
        gl.glBegin (gl.GL_LINES);
        gl.glVertex3f(0f, 0.50f, 0);
        gl.glVertex3f(0.50f, -0.50f, 0);
        gl.glEnd();
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
