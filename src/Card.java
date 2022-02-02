import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Card {
    // values you DONT want it to be near; you want to disregard these
    private static final int[] AWAYS = new int[]{0xd4d6d1, 0x381100};

    public static enum Shape {
        SQUIGGLE,
        DIAMOND,
        CIRCLE;
    }

    public static enum PrimaryColor {
        RED (0xff0000),
        GREEN (0x00ff00),
        BLUE (0x0000ff);

        private int value;
        private PrimaryColor(int rgb) {
            this.value = rgb;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum Fill {
        FILLED (1),
        SHADED (0.2),
        EMPTY (0.1);

        private double threshold;
        private Fill(double threshold) {
            this.threshold = threshold;
        }

        public double getThreshold() {
            return threshold;
        }
    }

    public static enum Number {
        ONE,
        TWO,
        THREE;
    }
    
    // if close to AWAYS values by threshold, disregard as noise
    // higher theshold = more noise resistant, more likley to remove useful color info
    private static final double threshold = 0.02;

    PrimaryColor color;
    Shape shape;
    Fill fill;
    Number number;
    PixelArea pa;


    public Card(int[][] grid) {
        color = getColor(grid);
    }

    public static PrimaryColor getColor(int[][] grid) {
        HashMap<PrimaryColor, Integer> count = new HashMap<>();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                int pixel = grid[r][c];

                if (findMinDist(pixel, AWAYS) < threshold) {
                    grid[r][c] = 0x00;
                    continue;
                }

                PrimaryColor maxPrime = Color.maxPrimary(pixel);
                count.put(maxPrime, count.getOrDefault(maxPrime, 0) + 1);

                grid[r][c] = maxPrime.getValue();
            }
        }
        
        Map.Entry<PrimaryColor, Integer> max = null;
        for (Map.Entry<PrimaryColor, Integer> kvp : count.entrySet()) {
            if (max == null || max.getValue() < kvp.getValue()) max = kvp;
        }

        return max != null ? max.getKey() : PrimaryColor.RED;
    }

/*
    private static final double fillThreshold = 0.15;
    public static Fill getFill(int[][] grid) {
        // get center of grid
        int cr = grid.length / 2, cc = grid[0].length / 2;
        // flood fill, then get contrast
        ArrayList<int[]> activeLocations = new ArrayList<int[]>(){{
            add(new int[]{cr, cc});
        }};

        int baseColor = grid[cr][cc];
        int accumulated = 0;
        Integer count = 0;

        ArrayList<int[]> newLocs = new ArrayList<>();
        while (activeLocations.size() != 0) {
            for (int[] loc : activeLocations) {
                for (int[] newLoc : getNeighborhood(grid, loc[0], loc[1])) {
                    if (Color.getDistance2(baseColor, grid[newLoc[0]][newLoc[1]]) < fillThreshold) {
                        Color.averageColors(accumulated, grid[newLoc[0]][newLoc[1]], count);
                        newLocs.add(newLoc);
                    }
                }
            }

            activeLocations.clear();
            activeLocations.addAll(newLocs);
            newLocs.clear();
        }


    }
    */

    public static boolean[] getMatchTable(Card c1, Card c2) {
        return new boolean[]{
            c1.color == c2.color,
            c1.shape == c2.shape,
            c1.fill == c2.fill,
            c1.number == c2.number
        };
    }

    private static double findMinDist(int pixel, int[] colors) {
        double minAwayDist = 1.1;
        for (int col : colors) {
            minAwayDist = Math.min(minAwayDist, Color.getDistance2(col, pixel));
        }
        return minAwayDist;
    }

    public static ArrayList<Card[]> matches(ArrayList<Card> cards) {
        ArrayList<Card[]> tr = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card ic = cards.get(i);
            for (int j = i + 1; j < cards.size(); j++) {
                boolean[] ijMatchTable = getMatchTable(ic, cards.get(j));
                for (int k = j + 1; k < cards.size(); k++) {
                    boolean[] ikMatchTable = getMatchTable(ic, cards.get(k));

                    for (int l = 0; l < ikMatchTable.length; l++) if (ijMatchTable[l] != ikMatchTable[l]) continue;

                    tr.add(new Card[]{ic, cards.get(j), cards.get(k)});

                }
            }
        }
        return tr;
    }

    public static ArrayList<int[]> getNeighborhood(int[][] grid, int r, int c) {
        ArrayList<int[]> tr = new ArrayList<>();
        for (int row = r - 1; row <= grid.length + 1; row++) {
            for (int col = c - 1; col <= grid[0].length + 1; col++) {
                if (inBound(grid, row, col)) tr.add(new int[]{row, col});
            }
        }
        return tr;
    }

    public static boolean inBound(int[][] grid, int r, int c){
        return -1 < r && r < grid.length && -1 < c && c < grid[0].length;
    }
}