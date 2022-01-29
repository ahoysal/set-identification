

public class Card {
    // values from lightest and darkest color on set card using gimp
    private static final int[] REDS = new int[]{0xe7b6af, 0xe7170a};
    private static final int[] GREENS = new int[]{0xbfd7bf, 0x2d752b};
    private static final int[] BLUES = new int[]{0xc3c3dd, 0x0f0f41};

    // values you DONT want it to be near; you want to disregard these
    private static final int[] AWAYS = new int[]{0xffffff, 0x381100};

    // if further than threshold distance away, disregard as noise
    private static final double threshold = 0.5;

    int color;

    public Card(int[][] grid) {
        int redCount = 0, greenCount = 0, blueCount = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                int pixel = grid[r][c];
                double redDist = findMinDist(pixel, REDS);
                double greenDist = findMinDist(pixel, GREENS);
                double blueDist = findMinDist(pixel, BLUES);
                double minAwayDist = findMinDist(pixel, AWAYS);

                double minDist = Math.min(minAwayDist, Math.min(redDist, Math.min(greenDist, blueDist)));
                if (minDist > threshold) continue;
                else if (minDist == redDist) redCount++;
                else if (minDist == greenDist) greenCount++;
                else if (minDist == blueDist) blueCount++;
            }
        }
        int maxCount = Math.max(redCount, Math.max(greenCount, blueCount));
        /*
        System.out.println(redCount);
        System.out.println(greenCount);
        System.out.println(blueCount);
        */
        if (maxCount == redCount) color = REDS[1];
        else if (maxCount == greenCount) color = GREENS[1];
        else if (maxCount == blueCount) color = BLUES[1];
    }

    private double findMinDist(int pixel, int[] colors) {
        double minAwayDist = 1.1;
        for (int col : colors) {
            minAwayDist = Math.min(minAwayDist, Color.getDistance2(col, pixel));
        }
        return minAwayDist;
    }

}