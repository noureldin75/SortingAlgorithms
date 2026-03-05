package com.example.sorting.util;

import java.util.Random;

public class ArrayGenerator {
    public enum Arraytype{
        RANDOM,SORTED,REVERSED
    }

    public static int [] generateArray(int size,Arraytype type){

        int [] array=new int[size];
        Random random = new Random();

        int maxBound=size*10;

        switch (type){
            case RANDOM :
                for (int i =0 ;i<size;i++){
                    array[i]=random.nextInt(maxBound);
                }
                break;
            case SORTED:
                for (int i =0;i<size;i++){
                    array[i]=(i+1)*10;
                }
                break;
            case REVERSED:
                for (int i=0;i<size;i++){
                    array[i]=(size-i)*10;
                }
                break;
            default:
                break;
        }
        return array;

    }
}
