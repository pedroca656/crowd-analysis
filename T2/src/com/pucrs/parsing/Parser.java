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

    public static List<Person> parsedData = new ArrayList<Person>();

    public static List<Coords> coordsList;

    private final File file = new File(paths_d_1);

    public static Integer maxWidth = 0;
    public static Integer maxHeight = 0;

    public Parser() {
        parsedData = new ArrayList<Person>();
    }

    public void parseFile() {
        // matcher
        Pattern px = Pattern.compile("\\(\\d*");        // x coordinates
        Pattern py = Pattern.compile(",\\d*,");         // y coordinates

        Matcher m;

        String str;

        int personNumber = 0;

        // second read: build matrix
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            // discard first line
            str = br.readLine();

            while ((str = br.readLine()) != null) {
                personNumber++;

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

                // prepare coordsList
                for (int i = 0; i < x.size(); i++) {
                    coordsList.add(new Coords(x.get(i), y.get(i))));
                }

                // create Person and add it to parsedData
                parsedData.add(new Person(coordsList, personNumber);

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println("print()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("|--number of people in set: " + parsedData.size());
        System.out.println("|--maxWidth: " + maxWidth);
        System.out.println("|--maxHeight: " + maxHeight);
        for (int i = 0; i < parsedData.size(); i++) {
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}