public class Main {
    public static void main(String[] args) {
        imageTest();
    }

    public static void imageTest() {
        Image img = new Image("john.png", 16, 30); // 16 w, 30 h
        int[][] grid = img.getGrid();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int color = Color.rgb2int((int) (0xFF * (c/(double) grid[0].length)), 0, (int) (0xFF * (r/(double) grid.length)));//(int) (0xFF * (r/(double) grid.length)), 100);
                grid[r][c] = color;
            }
        }
        img.setGrid(grid);
        System.out.println("done!");
    }
}