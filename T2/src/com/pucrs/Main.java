package com.pucrs;

import com.pucrs.parsing.Parser;
import com.pucrs.viewer.Jogo;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parseFile();

        new Jogo();

        // OPTIONAL:
        // print pessoaList content
        Parser.imprimeListaPessoas();
    }
}
