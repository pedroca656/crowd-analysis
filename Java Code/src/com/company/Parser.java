package src.com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Parser {
    private String paths_d_1 = "Paths_D (1).txt";
    private String paths_d_2 = "Paths_D (2).txt";
    private String paths_d_3 = "Paths_D (3).txt";
    private String paths_d_4 = "Paths_D (4).txt";

    private File file = new File(paths_d_4);

    private int pixelsToMeters;
    private int index;

    private List<List<Tuple>> peopleMatrix = new ArrayList<List<Tuple>>();

    public Parser() {
    }

    public void parseFileToMatrix() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // matchers
            Pattern pIndex = Pattern.compile("\\d*\\t");    // line start (number of coordinates per person)
            Pattern px = Pattern.compile("\\(\\d*,");       // x coordinates
            Pattern py = Pattern.compile(",\\d*,");         // y coordinates

            int index = 0;
            String str;

            // get pixels to meters
            str = br.readLine();
            pixelsToMeters = Integer.parseInt(str.substring(1, str.length()-1));

            // read new line
            while ((str = br.readLine()) != null) {
                List<Tuple> peopleLine = new ArrayList<Tuple>();

                Matcher m = pIndex.matcher(str);
                if (m.find()) {
                    this.index = Integer.parseInt(m.group().substring(0, (m.group().length()-1)));
                }

                // create arrays
                int x[] = new int[this.index];
                int y[] = new int[this.index];

                // apply x matcher
                m = px.matcher(str);
                index = 0;
                while (m.find()) {
                    x[index] = Integer.parseInt(m.group().substring(1, m.group().length() - 1));
                    index++;
                }

                // apply y matcher
                m = py.matcher(str);
                index = 0;
                while (m.find()) {
                    y[index] = Integer.parseInt(m.group().substring(1, m.group().length() - 1));
                    index++;
                }

                // add coordinates to list
                for (int i = 0; i < index; i++) {
                    peopleLine.add(new Tuple(x[i], y[i]));
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
        System.out.println("Pixels to meters: " + pixelsToMeters);
        System.out.print("Matrix size: " + indexMatrix);
        for (int i=0; i < indexMatrix; i++) {
            List<Tuple> tempList = peopleMatrix.get(i);
            System.out.println("\n\nPerson "  + i + ":  ");
            for (int j=0; j < tempList.size()-1; j++) {
                Tuple tempTuple = tempList.get(j);
                System.out.print("(" + tempTuple.getX() + "," + tempTuple.getY() + ") ");
            }
        }
    }

}
