import java.util.HashMap;
import java.util.Map;

public class Card {
    // values you DONT want it to be near; you want to disregard these
    private static final int[] AWAYS = new int[]{0xd4d6d1, 0x381100};

    // if close to AWAYS values by threshold, disregard as noise
    // higher theshold = more noise resistant, more likley to remove useful color info
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

        color = max != null ? max.getKey() : Color.PrimaryColor.RED;
    }

    private double findMinDist(int pixel, int[] colors) {
        double minAwayDist = 1.1;
        for (int col : colors) {
            minAwayDist = Math.min(minAwayDist, Color.getDistance2(col, pixel));
        }
        return minAwayDist;
    }

}