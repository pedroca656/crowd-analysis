//package com.pucrs.analysis;
//
//import com.pucrs.parsing.Person;
//
//public class Relation {
//    private Person first, second;
//    private Float weight;
//
//    public Relation(Person first, Person second, Float weight) {
//        this.first = first;
//        this.second = second;
//        this.weight = weight;
//    }
//
//    public Person getFirst() {
//        return first;
//    }
//
//    public Person getSecond() {
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
