package com.example.spoofer.minutiae;

public class MinutiaeExtraction {


    public static ImageMinutiae calculateAllCN(int[][] imgArray) {

        int numberOfRows = imgArray.length;
        int numberOfColumns = imgArray[0].length;
        int[][] CNArray = new int[numberOfRows][numberOfColumns];
        int[] numbersOfCN = new int[10];

        for(int row = 1; row < numberOfRows - 1; row++)
        {
            for(int col = 1; col < numberOfColumns - 1; col++)
            {
                if(imgArray[row][col] != 0) {
                    //Fillig up window to anylize
                    int[] pArr = new int[10];
                    pArr[0] = imgArray[row][col];
                    pArr[1] = imgArray[row][col + 1];
                    pArr[2] = imgArray[row - 1][col + 1];
                    pArr[3] = imgArray[row - 1][col];
                    pArr[4] = imgArray[row - 1][col - 1];
                    pArr[5] = imgArray[row][col - 1];
                    pArr[6] = imgArray[row + 1][col - 1];
                    pArr[7] = imgArray[row + 1][col];
                    pArr[8] = imgArray[row + 1][col + 1];
                    pArr[9] = pArr[1];

                    int CN = calculateCN(pArr);
                    CNArray[row][col] = CN;
                    if(CN >= 1 && CN <= 4)
                        numbersOfCN[CN]++;

                }


                //System.out.print(CNArray[row][col]);
            }
            //System.out.println();
        }

        return new ImageMinutiae(CNArray, numbersOfCN);

    }

    public static String compare(ImageMinutiae original, ImageMinutiae in) {

        String res = "";

        if(original.getNumbersOfCN()[3] == in.getNumbersOfCN()[3] && original.getNumbersOfCN()[4] == in.getNumbersOfCN()[4])
            return "EXACT";

        if(Math.abs(original.getNumbersOfCN()[3] - in.getNumbersOfCN()[3]) < 120 || Math.abs(original.getNumbersOfCN()[4] - in.getNumbersOfCN()[4]) < 20)
            return "TRUE";

        return "FALSE";

    }

    private static int calculateCN(int [] pArr) {

        int CN = 0;

        for(int i = 1; i <= 8; i++){
            CN += Math.abs(pArr[i] - pArr[i + 1]);
        }

        return CN / 2;

    }


    public static ImageMinutiae getCN(int skeleton[][])//after thinning
    {
        int[] numbersOfCN = new int[10];
        int di[]=new int[]{0,-1,-1,-1,0,1,1,1};
        int dj[]=new int[]{1,1,0,-1,-1,-1,0,1};

        int i,j,k;


        int trimL=skeleton[0].length-1;
        int trimT=skeleton.length-1;
        for(i=0;i<skeleton.length;i++)
            for(j=0;j<skeleton[0].length;j++)
                if(skeleton[i][j]>0)
                {
                    trimT=Math.min(trimT, i);
                    trimL=Math.min(trimL, j);
                }

        int CN[][]=new int[skeleton.length-trimT][skeleton[0].length-trimL];

        for(i=trimT;i<skeleton.length-1;i++)
            for(j=trimL;j<skeleton[0].length-1;j++)
            {
                if(skeleton[i][j] != 0) {
                    CN[i-trimT][j-trimL]=0;
                    for(k=1;k<8;k++)
                        CN[i-trimT][j-trimL]+=Math.abs(skeleton[i+di[k-1]][j+dj[k-1]]-skeleton[i+di[k]][j+dj[k]]);
                    numbersOfCN[CN[i-trimT][j-trimL]]++;
                }

            }

        return new ImageMinutiae(CN, numbersOfCN);

    }


}
