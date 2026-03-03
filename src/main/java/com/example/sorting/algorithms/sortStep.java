package com.example.sorting.algorithms;

public class sortStep {
    private int [] arrayState;
    private int currentidx;
    private int compareidx;
    private int  leftidx;
    private int rightidx;

    private sortStep(Builder builder) {
        this.arrayState = builder.arrayState;
        this.currentidx = builder.currentidx;
        this.compareidx = builder.compareidx;
        this.leftidx = builder.leftidx;
        this.rightidx = builder.rightidx;
    }


    public static class Builder {
        private int [] arrayState;
        private int currentidx=-1;
        private int compareidx=-1;
        private int  leftidx=-1;
        private int rightidx=-1;

        public Builder setArrayState(int[] arrayState) {
            this.arrayState = arrayState.clone();
            return this;
        }

        public Builder setCurrentidx(int currentidx) {
            this.currentidx = currentidx;
            return this;
        }

        public Builder setCompareidx(int compareidx) {
            this.compareidx = compareidx;
            return this;
        }

        public Builder bounds(int left, int right) {
            this.leftidx = left;
            this.rightidx = right;
            return this;
        }

        public sortStep build() {
            return new sortStep(this);
        }
    }
}
