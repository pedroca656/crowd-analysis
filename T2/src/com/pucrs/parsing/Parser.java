package com.pucrs.parsing;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final String paths_d_1 = "res/Paths_D (1).txt";


    public static List<Pessoa> pessoaList;

    private final File file = new File(paths_d_1);

    public static Integer colunas = 0;
    public static Integer linhas = 0;

    public Parser() {
        pessoaList = new ArrayList<>();
    }

    public void parseFile() {
        System.out.println("\n|## LENDO ARQUIVO ############");

        // define matcher patterns
        Pattern px = Pattern.compile("\\(\\d*");        // x coordinates
        Pattern py = Pattern.compile(",\\d*,");         // y coordinates

        // declare matcher
        Matcher m;

        // str receives the input stream
        String str;

        // person counter
        int personNumber = 0;

        // begin read
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            // discard first line
            str = br.readLine();

            while ((str = br.readLine()) != null) {
                // loop-long list for coords
                List<Cordenadas> cordenadasList = new ArrayList<>();

                // create lists for x & y coordinates
                List<Float> x = new ArrayList<>();
                List<Float> y = new ArrayList<>();

                // apply x matcher
                m = px.matcher(str);
                while (m.find()) {
                    // get x coordinates
                    x.add(new Float(Float.parseFloat((m.group().substring(1, m.group().length())))));
                    // discover max width
                    if (colunas < x.get(x.size()-1)) {
                        colunas = x.get(x.size()-1).intValue();
                    }
                }

                // apply y matcher
                m = py.matcher(str);
                while (m.find()) {
                    // get y coordinates
                    y.add(new Float(Float.parseFloat(m.group().substring(1, m.group().length() - 1))));
                    // discover height
                    if (linhas < y.get(y.size()-1)) {
                        linhas = y.get(y.size()-1).intValue();
                    }
                }

                // prepare cordenadasList
                for (int i = 0; i < x.size(); i++) {
                    Cordenadas temp = new Cordenadas(x.get(i), y.get(i));
                    temp.setIdOwner(personNumber);
                    cordenadasList.add(temp);
                }

                // create Pessoa and add it to pessoaList
                pessoaList.add(new Pessoa(cordenadasList, personNumber));

                personNumber++;
            }
            br.close();

            System.out.println("|--total de Pessoas: " + pessoaList.size());
            System.out.println("|--colunas: " + colunas);
            System.out.println("|--linhas: " + linhas);
            System.out.println("|---------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imprimeListaPessoas() {

        System.out.println("\n|##### Imprimindo ####################");
        for (Pessoa p : pessoaList) {
            System.out.println("| " + p.toString());
        }
        System.out.println("##########################################");
    }
}