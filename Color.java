public class Color {
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
}
