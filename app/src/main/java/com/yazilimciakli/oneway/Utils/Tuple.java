package com.yazilimciakli.oneway.Utils;


public class Tuple<A, B> {

    public A item1;
    public B item2;

    public Tuple(A item1, B item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "item1=" + item1 +
                ", item2=" + item2 +
                '}';
    }
}
