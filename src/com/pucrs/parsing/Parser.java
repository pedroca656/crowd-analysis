package com.pucrs.parsing;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final String paths_d_1 = "res/Paths_D (1).txt";
    private final String paths_d_2 = "res/Paths_D (2).txt";
    private final String paths_d_3 = "res/Paths_D (3).txt";
    private final String paths_d_4 = "res/Paths_D (4).txt";

    private List<List<Tuple>> peopleMatrix = new ArrayList<>();

    private final File file = new File(paths_d_4);

    public static Float pixelsToMeters;
    private int totalFrames = 0;
    private int size;

    public Parser() {}

    public void parseFileToMatrix() {
        // matcher
        Pattern pindex = Pattern.compile("\\d*\\t");    // number of coordinates per person
        Pattern px = Pattern.compile("\\(\\d*");        // x coordinates
        Pattern py = Pattern.compile(",\\d*,");         // y coordinates
        Pattern pframe = Pattern.compile("\\d*\\)");    // frame

        String str;

        int size = 0;
        int firstFrame = 0;
        int index;

        // first read: get total number of frames
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((str = br.readLine()) != null) {
                // apply frame matcher
                Matcher m = pframe.matcher(str);
                while (m.find()) {
                    if (totalFrames < Integer.parseInt(m.group().substring(0, m.group().length() - 1))) {
                        totalFrames = Integer.parseInt(m.group().substring(0, m.group().length() - 1));
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // second read: build matrix
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            // get pixels to meters
            str = br.readLine();
            pixelsToMeters = Float.parseFloat(str.substring(1, str.length()-1));

            while ((str = br.readLine()) != null) {
                List<Tuple> peopleLine = new ArrayList<>();

                // get amount of coordinates per person
                Matcher m = pindex.matcher(str);
                if (m.find()) {
                    size = Integer.parseInt(m.group().substring(0, (m.group().length()-1)));
                }

                // get first frame number matcher
                m = pframe.matcher(str);
                if (m.find()) {
                    firstFrame = Integer.parseInt(m.group(0).substring(0, m.group().length()-1))-1;
                }

                // create arrays
                Float x[] = new Float[size];
                Float y[] = new Float[size];

                // apply x matcher
                m = px.matcher(str);
                index = 0;
                while (m.find()) {
                    x[index] = Float.parseFloat(m.group().substring(1, m.group().length()));
                    index++;
                }

                // apply y matcher
                m = py.matcher(str);
                index = 0;
                while (m.find()) {
                    y[index] = Float.parseFloat(m.group().substring(1, m.group().length() - 1));
                    index++;
                }

                // add coordinates to list
                for (int i = 0; i < (firstFrame); i++) {
                    peopleLine.add(null);
                }
                for (int i = 0; i < size; i++) {
                    peopleLine.add(new Tuple(x[i], y[i]));
                }
                for (int i = 0; i < (totalFrames-firstFrame-size); i++) {
                    peopleLine.add(null);
                }
                // add list to matrix
                peopleMatrix.add(peopleLine);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void print() {
        int indexMatrix = peopleMatrix.size();
        System.out.println("print()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("|--pixels to meters: " + pixelsToMeters);
        System.out.println("|--number of people in set: " + indexMatrix);
        System.out.println("|--total number of frames: " + totalFrames);
        for (int i=0; i < indexMatrix; i++) {
            List<Tuple> tempList = peopleMatrix.get(i);
            System.out.println("\n|--Person "  + i + ":  ");
            for (int j=0; j < tempList.size(); j++) {
                if (tempList.get(j) != null) {
                    Tuple tempTuple = tempList.get(j);
                    System.out.println("| frame " + j + ": (" + tempTuple.getX() + "," + tempTuple.getY() + ") ");
                } else {
                    System.out.println("| frame " + j + ": (null)");
                }
            }
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public List<List<Tuple>> getPeopleMatrix() {
        return peopleMatrix;
    }

    public Float getPixelsToMeters() {
        return pixelsToMeters;
    }
}