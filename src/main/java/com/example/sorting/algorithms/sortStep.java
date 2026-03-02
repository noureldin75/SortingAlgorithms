package com.example.sorting.algorithms;

public class sortStep {
    private int [] currentarray;
    private int currentidx;
    private int swappedidx;

    public sortStep(int [] currentarray,int currentidx,int swappedidx){
        currentarray=this.currentarray.clone();
        currentidx=this.currentidx;
        swappedidx=this.swappedidx;
    }

    public int getCurrentidx() {
        return currentidx;
    }

    public int getSwappedidx() {
        return swappedidx;
    }

    public int[] getCurrentarray() {
        return currentarray;
    }
}
