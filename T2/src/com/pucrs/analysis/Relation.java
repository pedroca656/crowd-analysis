//package com.pucrs.analysis;
//
//import com.pucrs.parsing.Pessoa;
//
//public class Relation {
//    private Pessoa first, second;
//    private Float weight;
//
//    public Relation(Pessoa first, Pessoa second, Float weight) {
//        this.first = first;
//        this.second = second;
//        this.weight = weight;
//    }
//
//    public Pessoa getFirst() {
//        return first;
//    }
//
//    public Pessoa getSecond() {
//        return second;
//    }
//
//    public Float getWeight() {
//        return weight;
//    }
//
//    public void setWeight(Float weight) {
//        this.weight = weight;
//    }
//
//    @Override
//    public String toString() {
//        return "Relation{" +
//                "first=" + first +
//                ", second=" + second +
//                ", weight=" + weight +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Relation relation = (Relation) o;
//
//        if (!first.equals(relation.first)) return false;
//        return second.equals(relation.second);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = first.hashCode();
//        result = 31 * result + second.hashCode();
//        return result;
//    }
//}
