public class Scaler {
    int nw, nh;

    public Scaler(int nw, int nh) {
        this.nw = nw;
        this.nh = nh;
    }

    public void processImage(int[][] grid) {
        int[][] ngrid = new int[nh][nw];

        double hScale = grid.length / (double) nh, wScale = grid[0].length / (double) nw;
        // double hScale = nh / (double) grid.length, wScale = nw / (double) grid[0].length;

        for (int r = 0; r < nh; r++) {
            for (int c = 0; c < nw; c++) {
                ngrid[r][c] = sample(grid, hScale, wScale, r, c);
            }
        }
    }

    private int sample(int[][] grid, double hScale, double wScale, int nr, int nc) {
        int count = 0;
        int tr = 0;
        for (int r = (int) (nr * hScale); r < Math.min((int) (nr * hScale) + (hScale), grid.length); r++) {
            for (int c = (int) (nc * wScale); c < Math.min((int) (nc * wScale) + (wScale), grid[r].length); c++) {
                tr = average(tr, grid[r][c], count);
                count++;
            }
        }
        return tr;

    }

    private int average(int o, int n, int curcount) {
        int tr = 0;
        for (int s = 0; s <= 24; s += 8) {
            int ov = (o >> s) & 0xFF, nv = (n >> s) & 0xFF;
            int k = (int) (((ov * curcount) + nv) / (1 + (double) curcount));
            tr += k << s;
        }
        return tr;
    }
}