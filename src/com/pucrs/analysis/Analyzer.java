package com.pucrs.analysis;

import com.pucrs.parsing.Person;

import java.util.List;

public class Analyzer {
    private List<List<Person>> peopleMatrix;

    public Analyzer(List<List<Person>> peopleMatrix) {
        this.peopleMatrix = peopleMatrix;
    }

    // analyzes if there are pairs of people walking or standing still together
    public void findPairs() {
        List<List<Person>> analysisMatrix;
        // cópia da peopleMatrix com listas de Pessoas dentro (se no frame uma pessoa estava a X de distancia de
        // outra pessoa, adicona seu companheiro nessa lista. Se ao menos em Y% dos frames totais existentes eles estao
        // um na lista do outro, contar como um par (evento))
    }

    // analyzes if some person crossed paths with another
    public void findCrossings() {
        // se duas linhas se cruzarem = evento
        //
    }
}
