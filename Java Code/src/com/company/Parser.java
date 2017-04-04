package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Parser {
    private File file = new File("<INSERIR URL DO ARQUIVO PATHS_D.TXT>");
    List<List<Tuple>> peopleMatrix = new ArrayList<List<Tuple>>();

    public Parser() {
    }

    public void parseFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str;

            Pattern pDelta = Pattern.compile("\\d*\\t");
            Pattern px = Pattern.compile("\\(\\d*,");
            Pattern py = Pattern.compile(",\\d*,");
            str = br.readLine();
            while ((str = br.readLine()) != null) {
                List<Tuple> peopleLine = new ArrayList<Tuple>();
                System.out.println(str);

                Matcher m = px.matcher(str);
                int index = 0;
                while (m.find()) {
                    index++;
                }
                int x[] = new int[index];
                int y[] = new int[index];
                m = px.matcher(str);
                index = 0;
                while (m.find()) {
                    x[index] = Integer.parseInt(m.group().substring(1, m.group().length() - 1));
                    index++;
                }

                m = py.matcher(str);
                index = 0;
                while (m.find()) {
                    y[index] = Integer.parseInt(m.group().substring(1, m.group().length() - 1));
                    index++;
                }
                for (int i = 0; i < index; i++) {
                    System.out.println("(" + x[i] + "," + y[i] + ")");
                    peopleLine.add(new Tuple(x[i], y[i]));
                }
                peopleMatrix.add(peopleLine);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        int indexMatrix = peopleMatrix.size();
        System.out.println("indexMatrix Size: " + indexMatrix);
        for (int i=1; i < indexMatrix-1; i++) {
            List<Tuple> tempList = peopleMatrix.get(i);
            System.out.println("\n\nPerson "  + i + ":  ");
            for (int j=0; j < tempList.size()-1; j++) {
                Tuple tempTuple = tempList.get(j);
                System.out.print("(" + tempTuple.getX() + "," + tempTuple.getY() + ") ");
            }
        }
    }

}
