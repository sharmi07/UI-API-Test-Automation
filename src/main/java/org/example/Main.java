package org.example;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Integer> list = Arrays.asList(2,6,8,1);
       // System.out.println(list.stream().reduce((a,b) -> max(a,b)).get());
       // System.out.println(list.stream().map(e -> e*e).filter(e -> e>25).mapToInt(e -> e).average().getAsDouble());

                /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
                int[] arr= {1,-2,4,-5,1};
                int i=0, index=0, sum =0, count = 0;
                while(i<arr.length){
                   // index = i;
                sum = 0;
                    for (int j = i; j < arr.length; j++) {
                        sum = sum + arr[j];
                        System.out.println(sum);
                        if(sum<0)
                            count++;
                    }
                    i++;
                }
                System.out.println(count);




    }
}