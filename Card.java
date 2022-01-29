import java.util.HashMap;
import java.util.Map;

public class Card {
    // values from lightest and darkest color on set card using gimp
    private static final int[] REDS = new int[]{0xe7b6af, 0xe7170a};
    private static final int[] GREENS = new int[]{0xbfd7bf, 0x2d752b};
    private static final int[] BLUES = new int[]{0xc3c3dd, 0x0f0f41, 0x686988};

    // values you DONT want it to be near; you want to disregard these
    private static final int[] AWAYS = new int[]{0xd4d6d1, 0x381100};

    // if further than threshold distance away, disregard as noise
    private static final double threshold = 0.02;

    Color.PrimaryColor color;

    public Card(int[][] grid) {
        HashMap<Color.PrimaryColor, Integer> count = new HashMap<>();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                int pixel = grid[r][c];

                if (findMinDist(pixel, AWAYS) < threshold) {
                    grid[r][c] = 0x00;
                    continue;
                }

                Color.PrimaryColor maxPrime = Color.maxPrimary(pixel);
                count.put(maxPrime, count.getOrDefault(maxPrime, 0) + 1);

                grid[r][c] = maxPrime.getValue();
            }
        }
        
        Map.Entry<Color.PrimaryColor, Integer> max = null;
        for (Map.Entry<Color.PrimaryColor, Integer> kvp : count.entrySet()) {
            if (max == null || max.getValue() < kvp.getValue()) max = kvp;
        }

        color = max.getKey();
    }

    /*
    int color;

    public Card(int[][] grid, boolean hi) {
        int redCount = 0, greenCount = 0, blueCount = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                int pixel = grid[r][c];
                double redDist = findMinDist(pixel, REDS);
                double greenDist = findMinDist(pixel, GREENS);
                double blueDist = findMinDist(pixel, BLUES);
                double awayDist = findMinDist(pixel, AWAYS);

                double minDist = Math.min(awayDist, Math.min(redDist, Math.min(greenDist, blueDist)));
                if (minDist > threshold) continue;
                else if (minDist == redDist) redCount++;
                else if (minDist == greenDist) greenCount++;
                else if (minDist == blueDist) blueCount++;

                
                //#region debugging
                if (minDist == redDist) grid[r][c] = REDS[1];
                else if (minDist == greenDist) grid[r][c] = GREENS[1];
                else if (minDist == blueDist) grid[r][c] = BLUES[1];
                else if (minDist == awayDist) grid[r][c] = AWAYS[0];
                //#endregion
                

            }
        }
        int maxCount = Math.max(redCount, Math.max(greenCount, blueCount));

        if (maxCount == redCount) color = REDS[1];
        else if (maxCount == greenCount) color = GREENS[1];
        else if (maxCount == blueCount) color = BLUES[1];
    }

    */

    private double findMinDist(int pixel, int[] colors) {
        double minAwayDist = 1.1;
        for (int col : colors) {
            minAwayDist = Math.min(minAwayDist, Color.getDistance2(col, pixel));
        }
        return minAwayDist;
    }

}