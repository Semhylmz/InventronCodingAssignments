package com.versioncontrol;

import java.util.Arrays;

public class main {

    public static void main(String[] args) {
        Compare comp = new Compare();
        System.out.println(comp.compare("212.21.12.13", "212.21.12.13"));
    }
}

class Compare {
    String compare(String val1, String val2) {
        String result = "";

        int result1 = Arrays.stream(Arrays.stream(val1.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray()).sum();

        int result2 = Arrays.stream(Arrays.stream(val2.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray()).sum();

        if (result1 == result2) result = "Result 0: Versions are the same";
        else if (result1 < result2) result = "Result  -1: First input is old version";
        else result = "Result  1: First input is new version";

        return result;
    }
}