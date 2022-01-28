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
}
