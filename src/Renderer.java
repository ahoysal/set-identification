public class Renderer {
    public static void drawLine(int[][] grid, int x0, int y0, int x1, int y1, int color) {
        int dx = Math.abs(x1 - x0);
        int dy = -Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;
        while (true) {
            grid[y0][x0] = color;
            if (y0 == y1 && x0 == x1) break;
            int err2 = err * 2;
            if (err2 >= dy) {
                if (x0 == x1) break;
                err += dy;
                x0 += sx;
            }
            if (err2 <= dx) {
                if (y0 == y1) break;
                err += dx;
                y0 += sy;
            }
        }
    }

    public static void drawRect(int[][] grid, int x0, int y0, int x1, int y1, int color) {
        Renderer.drawLine(grid, x0, y1, x1, y1, color); // top line
        Renderer.drawLine(grid, x1, y1, x1, y0, color); // right line
        Renderer.drawLine(grid, x1, y0, x0, y0, color); // bottom line
        Renderer.drawLine(grid, x0, y0, x0, y1, color); // bottom line
    }

    public static void drawCross(int[][] grid, int x0, int y0, int x1, int y1, int color) {
        Renderer.drawLine(grid, x0, y0, x1, y1, color);
        Renderer.drawLine(grid, x0, y1, x1, y0, color);
    }
}
