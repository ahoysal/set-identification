import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private String filename;
    private File f;
    private BufferedImage img;

    public Image(String filename) {
        this.filename = filename;
        f = new File(filename);
        img = read();
    }

    public Image(String filename, int w, int h) {
        this.filename = filename;
        f = new File(filename);
        makeEmpty(w, h);
        img = read();
    }

    public int[][] getGrid() {
        int[][] arr = new int[img.getHeight()][img.getWidth()];
        for (int x = 0; x < arr[0].length; x++) {
            for (int y = 0; y < arr.length; y++) {
                arr[y][x] = img.getRGB(x, y);
            }
        }
        return arr;
    }

    public static short[][][] convert(int[][] g) {
        short[][][] tr = new short[][][]{new short[g.length][g[0].length], new short[g.length][g[0].length], new short[g.length][g[0].length]};
        for (int r = 0; r < g.length; r++) {
            for (int c = 0; c < g[r].length; c++) {
                tr[0][r][c] = (short) Color.getRed(g[r][c]);
                tr[1][r][c] = (short) Color.getGreen(g[r][c]);
                tr[2][r][c] = (short) Color.getBlue(g[r][c]);
            }
        }
        return tr;
    }

    public static int[][] convert(short[][][] g) {
        int[][] grid = new int[g[0].length][g[0][0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                grid[r][c] = Color.rgb2int(g[0][r][c], g[1][r][c], g[2][r][c]);
            }
        }
        return grid;
    }

    public void setGrid(int[][] grid) {
        if (grid.length != img.getHeight() || grid[0].length != img.getWidth()) makeEmpty(grid[0].length, grid.length);
        img.setRGB(0, 0, img.getWidth(), img.getHeight(), flatten(grid), 0, grid[0].length);
        try {
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.err.printf("file \"%s\" isn't openable\n", filename);
            e.printStackTrace();
        }
    }

    private BufferedImage read() {
        try {
            return ImageIO.read(f);
        } catch (IOException e) {
            System.err.printf("file \"%s\" isn't openable\n", filename);
            e.printStackTrace();
        }
        return null;
    }

    private void makeEmpty(int w, int h) {
        try {
            ImageIO.write(new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB), "png", f);
        } catch (IOException e) {
            System.err.printf("file \"%s\" isn't openable\n", filename);
            e.printStackTrace();
        }
        img = read();
    }

    private int[] flatten(int[][] grid) {
        int[] tr = new int[grid.length * grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                tr[r * grid[r].length + c] = grid[r][c];
            }
        }
        return tr;
    }
}
