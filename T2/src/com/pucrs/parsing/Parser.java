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

    private final String germany = "res/germany_1.txt";
    private final String portugal = "res/portugal.txt";
    private final String spain = "res/spain.txt";
    private final String turkey = "res/turkey.txt";

    private List<Person> dataMatrix = new ArrayList<Person>();

    private final File file = new File(spain);

    private Integer maxWidth = 0;
    private Integer maxHeight = 0;

    private int totalFrames = 0;

    public Parser() {}

    public void parseFile() {
        // matcher
        Pattern pindex = Pattern.compile("\\d*\\t");    // number of coordinates per person
        Pattern px = Pattern.compile("\\(\\d*");        // x coordinates
        Pattern py = Pattern.compile(",\\d*,");         // y coordinates
        Pattern pframe = Pattern.compile("\\d*\\)");    // frame

        String str;

        int size = 0;
        int firstFrame = 0;
        int personNumber = 0;

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

            // discard first line
            str = br.readLine();

            while ((str = br.readLine()) != null) {
                personNumber++;

                // get amount of coordinates per person
                Matcher m = pindex.matcher(str);
                if (m.find()) {
                    size = Integer.parseInt(m.group().substring(0, (m.group().length() - 1)));
                }

                // get first frame number matcher
                m = pframe.matcher(str);
                if (m.find()) {
                    firstFrame = Integer.parseInt(m.group(0).substring(0, m.group().length() - 1)) - 1;
                }

                // create lists
                List<Float> x = new ArrayList<Float>();
                List<Float> y = new ArrayList<Float>();
                for (int i = 0; i < (firstFrame); i++) {
                    x.add(null);
                    y.add(null);
                }

                // apply x matcher
                m = px.matcher(str);
                while (m.find()) {
                    x.add(Float.parseFloat(m.group().substring(1, m.group().length())));
                    if (maxWidth < x.get(x.size()-1)) {
                        maxWidth = x.get(x.size()-1).intValue();
                    }
                }

                // apply y matcher
                m = py.matcher(str);
                while (m.find()) {
                    y.add(Float.parseFloat(m.group().substring(1, m.group().length() - 1)));
                    if (maxHeight < y.get(y.size()-1)) {
                        maxHeight = y.get(y.size()-1).intValue();
                    }
                }

                for (int i = 0; i < (totalFrames - firstFrame - size); i++) {
                    x.add(null);
                    y.add(null);
                }
                // add Person to matrix
                dataMatrix.add(new Person(x, y, personNumber));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println("print()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("|--number of people in set: " + dataMatrix.size());
        System.out.println("|--total number of frames: " + totalFrames);
        System.out.println("|--maxWidth: " + maxWidth);
        System.out.println("|--maxHeight: " + maxHeight);
        for (int i=0; i < dataMatrix.size(); i++) {
            System.out.println("\n|--Person "  + i + ":  ");
            for (int j=0; j < totalFrames; j++) {
                if (dataMatrix.get(i).getxCoords().get(j) != null) {
                    System.out.println("| frame " + j + ": (" + dataMatrix.get(i).getxCoords().get(j) + "," + dataMatrix.get(i).getyCoords().get(j) + ") ");
                } else {
                    System.out.println("| frame " + j + ": (null, null)");
                }
            }
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    public DataPackage getDataPackage() {
        return new DataPackage(dataMatrix, maxWidth, maxHeight, totalFrames);
    }
}