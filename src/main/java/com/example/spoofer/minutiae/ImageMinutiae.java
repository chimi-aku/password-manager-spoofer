package com.example.spoofer.minutiae;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageMinutiae {

    private int[][] CNArray;
    private int[] numbersOfCN;


    public ImageMinutiae(int[][] CNArray, int[] numbersOfCN) {
        this.CNArray = CNArray;
        this.numbersOfCN = numbersOfCN;
    }
}
