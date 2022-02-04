public class Color {

    private final static double purpleThreshold = 0.075;

    public static int rgb2int(int r, int g, int b) {
        return (0xFF << 24) + (r << 16) + (g << 8) + (b);
    }

    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    public static int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    public static int setRed(int rgb, int r) {
        return (rgb & 0xFF00FFFF) + (r << 16);
    }

    public static int setGreen(int rgb, int g) {
        return (rgb & 0xFFFF00FF) + (g << 8);
    }

    public static int setBlue(int rgb, int b) {
        return (rgb & 0xFFFFFF00) + b;
    }

    public static double getDistance2(int v1, int v2) {
        int dr = getRed(v1) - getRed(v2), dg = getGreen(v1) - getGreen(v2), db = getBlue(v1) - getBlue(v2);
        return (dr * dr + dg * dg + db * db) / 195075.0; // 195075 == 255^2 + 255^2 + 255^2
    }

    public static Card.PrimaryColor maxPrimary(int v) {
        int max = Math.max(getRed(v), Math.max(getGreen(v), getBlue(v)));
        if (max == getRed(v)) {
            if (getRed(v) / (double) getBlue(v) < 1 + purpleThreshold) return Card.PrimaryColor.BLUE; // if purple
            return Card.PrimaryColor.RED;
        }
        if (max == getGreen(v)) return Card.PrimaryColor.GREEN;
        return Card.PrimaryColor.BLUE;
    }


    public static int averageColors(int accumulated, int toAdd, Integer count) {
        double total = (double) (count + 1);
        int tr = Color.rgb2int(
            (int) ((Color.getRed(accumulated) * count + Color.getRed(toAdd)) / total),
            (int) ((Color.getGreen(accumulated) * count + Color.getGreen(toAdd)) / total),
            (int) ((Color.getBlue(accumulated) * count + Color.getBlue(toAdd)) / total)
        );

        count++;

        return tr;
    }
}
