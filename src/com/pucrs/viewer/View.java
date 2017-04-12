package com.pucrs.viewer;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.pucrs.analysis.Analyzer;
import com.pucrs.analysis.Relation;
import com.pucrs.parsing.DataPackage;
import com.pucrs.parsing.Person;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class View extends JFrame implements GLEventListener {
    private List<Person> dataMatrix;
    private int totalFrames;

    private Analyzer analyzer;

    private int width = 1200;
    private int height = 825;
    private Integer maxWidth, maxHeight;

    public View(DataPackage dataPackage) {
        super("crowd-analysis");

        this.dataMatrix = dataPackage.getDataMatrix();
        this.totalFrames = dataPackage.getTotalFrames();
        this.maxWidth = dataPackage.getMaxWidth();
        this.maxHeight = dataPackage.getMaxHeight();

        this.analyzer = new Analyzer(dataPackage);

        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

        this.setName("crowd-analysis");
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
        //List<List<Relation>> analysisData = analyzer.findPairs();

        // set colouring tools
        Random rand = new Random(255);
        List<List<Integer>> colorList  = new ArrayList<List<Integer>>();
        for (int i = 0; i < dataMatrix.size(); i++) {
            ArrayList<Integer> rgb = new ArrayList<Integer>();
            rgb.add(rand.nextInt());
            rgb.add(rand.nextInt());
            rgb.add(rand.nextInt());
            colorList.add(rgb);
        }

        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0f, maxWidth+75, maxHeight+75, 0.0f, 0.0f, 1.0f);
        gl.glClearColor(0.392f, 0.584f, 0.929f, 1.0f);

        float offset = 2.5f;
        float numberOffset;

        Float xCoord;
        Float yCoord;
        List<Float> xCoordsList;
        List<Float> yCoordsList;

        for (int i = 0; i < dataMatrix.size(); i++) {
            // get coordsList
            xCoordsList = dataMatrix.get(i).getxCoords();
            yCoordsList = dataMatrix.get(i).getyCoords();
            for (int j = 0; j < totalFrames; j++) {
                if (xCoordsList.get(j) != null) {
                    // set color
                    gl.glColor3f(colorList.get(i).get(0), colorList.get(i).get(1), colorList.get(i).get(2));

                    xCoord = xCoordsList.get(j);
                    yCoord = yCoordsList.get(j);

                    if (j == 0) {
                        offset = offset + 3.5f;
                        numberOffset = 9.0f;
                        for (int g = 0; g < dataMatrix.get(i).getId(); g++) {
                            gl.glBegin (gl.GL_LINES);
                            gl.glVertex3f((xCoord+numberOffset), (yCoord+offset), 0);
                            gl.glVertex3f((xCoord+numberOffset), (yCoord-offset), 0);
                            gl.glEnd();
                            numberOffset = numberOffset + 3.0f;
                        }
                    }

                    // drawing left side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord-offset), (yCoord-offset), 0);
                    gl.glVertex3f((xCoord-offset), (yCoord+offset), 0);
                    gl.glEnd();
                    // drawing top side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord-offset), (yCoord+offset), 0);
                    gl.glVertex3f((xCoord+offset), (yCoord+offset), 0);
                    gl.glEnd();
                    // drawing right side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord+offset), (yCoord+offset), 0);
                    gl.glVertex3f((xCoord+offset), (yCoord-offset), 0);
                    gl.glEnd();
                    // drawing bottom side
                    gl.glBegin (gl.GL_LINES);
                    gl.glVertex3f((xCoord+offset), (yCoord-offset), 0);
                    gl.glVertex3f((xCoord-offset), (yCoord-offset), 0);
                    gl.glEnd();
                    gl.glFlush();

                    if (j == 0) {
                        offset = offset - 3.5f;
                    }
                }
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
