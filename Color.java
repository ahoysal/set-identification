public class Color {

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

    public static PrimaryColor maxPrimary(int v) {
        int max = Math.max(getRed(v), Math.max(getGreen(v), getBlue(v)));
        if (max == getRed(v)) {
            if (getRed(v) / (double) getBlue(v) < 1 + purpleThreshold) return PrimaryColor.BLUE; // if purple
            return PrimaryColor.RED;
        }
        if (max == getGreen(v)) return PrimaryColor.GREEN;
        return PrimaryColor.BLUE;
    }
}
