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

    private List<List<Person>> dataMatrix = new ArrayList<>();

    private final File file = new File(paths_d_4);

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
                List<Person> peopleLine = new ArrayList<>();

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

                // add coordinates to list
                for (int i = 0; i < (firstFrame); i++) {
                    peopleLine.add(null);
                }
                for (int i = 0; i < size; i++) {
                    peopleLine.add(new Person((x.get(i)), (y.get(i)), personNumber));
                }
                for (int i = 0; i < (totalFrames - firstFrame - size); i++) {
                    peopleLine.add(null);
                }
                // add list to matrix
                dataMatrix.add(peopleLine);
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
//        for (int i=0; i < indexMatrix; i++) {
//            List<Person> tempList = dataMatrix.get(i);
//            System.out.println("\n|--Person "  + i + ":  ");
//            for (int j=0; j < tempList.size(); j++) {
//                if (tempList.get(j) != null) {
//                    Person tempPerson = tempList.get(j);
//                    System.out.println("| frame " + j + ": (" + tempPerson.getX() + "," + tempPerson.getY() + ") ");
//                } else {
//                    System.out.println("| frame " + j + ": (null)");
//                }
//            }
//        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    public DataPackage getDataPackage() {
        return new DataPackage(dataMatrix, maxWidth, maxHeight, totalFrames);
    }
}