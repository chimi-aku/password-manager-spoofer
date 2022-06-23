package com.example.spoofer.skeletonization;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ZhangSuen {

    final static int[][] nbrs = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1},
            {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}};

    final static int[][][] nbrGroups = {{{0, 2, 4}, {2, 4, 6}}, {{0, 2, 6},
            {0, 4, 6}}};

    static List<Point> toWhite = new ArrayList<Point>();
    static int[][] grid;


    public static int [][] thinImage(int [][] imgArray) {
        boolean firstStep = false;
        boolean hasChanged;

        int numberOfRows = imgArray.length;
        int numberOfColumns = imgArray[0].length;
        grid = new int[numberOfRows][numberOfColumns];

        // Fillig up grid array
        for (int r = 0; r < imgArray.length; r++)
            for (int c = 0; c < imgArray[0].length; c++) {
                grid[r][c] = imgArray[r][c];
            }

        do {
            hasChanged = false;
            firstStep = !firstStep;

            for (int r = 1; r < grid.length - 1; r++) {
                for (int c = 1; c < grid[0].length - 1; c++) {

                    if (grid[r][c] != 1)
                        continue;

                    int nn = numNeighbors(r, c);
                    if (nn < 2 || nn > 6)
                        continue;

                    if (numTransitions(r, c) != 1)
                        continue;

//                    if (!atLeastOneIsWhite(r, c, firstStep ? 0 : 1))
//                        continue;
                    if(!(grid[r - 1][c] == 0 || grid[r][c + 1] == 0 || grid[r + 1][c] == 0))
                        continue;

                    if(!(grid[r ][c - 1] == 0 || grid[r][c + 1] == 0 || grid[r + 1][c] == 0))
                        continue;

                    toWhite.add(new Point(c, r));
                    hasChanged = true;
                }
            }

            for (Point p : toWhite)
                grid[p.y][p.x] = 0;
            toWhite.clear();

        } while (firstStep || hasChanged);

        return grid;
    }

    static int numNeighbors(int r, int c) {
        int count = 0;
        for (int i = 0; i < nbrs.length - 1; i++)
            if (grid[r + nbrs[i][1]][c + nbrs[i][0]] == 1)
                count++;
        return count;
    }

    static int numTransitions(int r, int c) {
        int count = 0;
        for (int i = 0; i < nbrs.length - 1; i++)
            if (grid[r + nbrs[i][1]][c + nbrs[i][0]] == 0) {
                if (grid[r + nbrs[i + 1][1]][c + nbrs[i + 1][0]] == 1)
                    count++;
            }
        return count;
    }

    static boolean atLeastOneIsWhite(int r, int c, int step) {
        int count = 0;
        int[][] group = nbrGroups[step];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < group[i].length; j++) {
                int[] nbr = nbrs[group[i][j]];
                if (grid[r + nbr[1]][c + nbr[0]] == 0) {
                    count++;
                    break;
                }
            }
        return count > 1;
    }

}
