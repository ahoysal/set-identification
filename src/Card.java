import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Card {
    // values you DONT want it to be near; you want to disregard these
    private static final int[] AWAYS = new int[]{0xd4d6d1, 0x381100};

    public static enum Shape {
        DIAMOND (0.096),
        SQUIGGLE (0.125),
        CIRCLE (100);

        private double threshold;
        private Shape(double t) {
            threshold = t;
        }

        public double getThreshold() {
            return threshold;
        }
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
        EMPTY (8),
        SHADED (200),
        FILLED (1000);

        private double threshold;
        private Fill(double threshold) {
            this.threshold = threshold;
        }

        public double getThreshold() {
            return threshold;
        }
    }

    public static enum Number {
        ONE (1),
        TWO (2),
        THREE (3);

        private int value;
        private Number(int n) {
            value = n; 
        }

        public static Number getCorresponding(int n) {
            for (Number k : Number.values()) {
                if (k.value == n) return k;
            }
            return THREE;
        }
    }
    
    // if close to AWAYS values by threshold, disregard as noise
    // higher theshold = more noise resistant, more likley to remove useful color info
    private static final double threshold = 0.05;

    PrimaryColor color;
    Number number;
    Fill fill;
    Shape shape;
    PixelArea pa;

    private ArrayList<PixelArea> internal;
    private PixelArea cardArea;
    private short[][][] baseGrid;


    public Card(PixelArea cardArea, short[][][] baseGrid) {
        this.baseGrid = baseGrid;
        this.cardArea = cardArea;
        ritikareadetection.inverse = true;
        internal = ritikareadetection.processImage(cardArea.pixelSquare[0], cardArea.pixelSquare[1], cardArea.pixelSquare[2]);

        color = getColor(Image.convert(cardArea.pixelSquare));
        shape = getShape();
        number = getNumber();

        fill = getFill();

        ritikareadetection.inverse = false;
    }

    public Card(PrimaryColor c, Number n, Fill f, Shape s) {
        color = c;
        shape = s;
        fill = f;
        number = n;
    }

    public String toString() {
        return "{" + color.name() + " " + number.name() + " " + fill.name() + " " + shape.name() + "}";
    }

    public PrimaryColor getColor(int[][] grid) {
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
            }
        }
        
        Map.Entry<PrimaryColor, Integer> max = null;
        for (Map.Entry<PrimaryColor, Integer> kvp : count.entrySet()) {
            if (max == null || max.getValue() < kvp.getValue()) max = kvp;
        }

        return max != null ? max.getKey() : PrimaryColor.RED;
    }

    public Fill getFill() {
        int[] avg = highlight.getAvg(cardArea, baseGrid[0], baseGrid[1], baseGrid[2]);


        if (internal.size() == 0) return Fill.EMPTY;
        PixelArea area1 = internal.get(0);
        removeEdges(area1);

        double dist = highlight.getAvgDistance(area1, baseGrid[0], baseGrid[1], baseGrid[2], avg, 6);

        return getFillThreshold(dist);
    }

    public Number getNumber() {
        return Number.getCorresponding(internal.size());
    }

    public Shape getShape() {
        int totalPixels = 0;
        int areas = 0;
        double ratio;

        for(int i2 = 0; i2 < internal.size(); i2++){
            int size2 = internal.get(i2).pixelSquareLocations.size();

            if(size2 < 200){
                internal.remove(i2);
                i2--;
                continue;
            }

            totalPixels += size2;
            areas++;

            internal.get(i2).minX += cardArea.minX;
            internal.get(i2).minY += cardArea.minY;

        }

        ratio = (double)totalPixels / (cardArea.pixelSquareLocations.size() * areas);

        return getShapeThreshold(ratio);
        
    }

    public void draw(int[][] grid) {
        baseGrid = Image.convert(grid);
        Renderer.drawText(grid, toString(), cardArea.minX, cardArea.minY, Color.rgb2int(0, 0, 0)); //(cardArea.minX + cardArea.maxX) / 2, (cardArea.minY + cardArea.maxY) / 2, Color.rgb2int(0, 0, 0));
    }

    private static Fill getFillThreshold(double r){

        for(Fill k : Fill.values()){
            if(r < k.getThreshold()) return k;
        }

        return Fill.values()[Fill.values().length-1];
    }

    private static Shape getShapeThreshold(double r){

        for(Shape k : Shape.values()){
            if(r < k.getThreshold()) return k;
        }

        System.err.println("Hello");
        return Shape.values()[Shape.values().length-1];
    }

    private static void removeEdges(PixelArea area) {
        for(int r = 0; r < 6; r++){

            area.pixelSquare = removeedges.removeEdges(area.pixelSquare[0],area.pixelSquare[1],area.pixelSquare[2] );

        }
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

    public static ArrayList<Card> getCards(short[][][] image) {
        ArrayList<Card> cards = new ArrayList<>();

        ArrayList<PixelArea> potential = ritikareadetection.processImage(image[0], image[1], image[2]);

        for (int i = 0; i < potential.size(); i++) {
            PixelArea current = potential.get(i);
            if(current.pixelSquareLocations.size() < 500){
                potential.remove(i);
                i--;
                continue;
            }

            cards.add(new Card(current, image));
        }

        return cards;
    }

    private static int getMatchTable(Card c1, Card c2) {
        return ((c1.color == c2.color ? 1 : 0)) | 
        ((c1.shape == c2.shape ? 1 : 0) << 1) | 
        ((c1.fill == c2.fill ? 1 : 0) << 2) | 
        ((c1.number == c2.number ? 1 : 0) << 3);
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
                Card jc = cards.get(j);

                int ijMatchTable = getMatchTable(ic, jc);
                for (int k = j + 1; k < cards.size(); k++) {
                    Card kc = cards.get(k);

                    int ikMatchTable = getMatchTable(ic, kc);
                    int jkMatchTable = getMatchTable(jc, kc);

                    if (ijMatchTable != ikMatchTable || ijMatchTable != jkMatchTable) continue;
                    
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