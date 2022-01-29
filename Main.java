import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        imageTest();
    }

    public static void imageTest() {
        Image input = new Image("input.jpeg");

        int[][] grid = input.getGrid();
        
        ArrayList<PixelArea> pas = AreaDetection.processImage(grid);
        System.out.println(pas.size());
        for (PixelArea pa : pas) {
            int lineColor = Color.rgb2int((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            Renderer.drawLine(grid, pa.minX, pa.maxY, pa.maxX, pa.maxY, lineColor); // top line
            Renderer.drawLine(grid, pa.maxX, pa.maxY, pa.maxX, pa.minY, lineColor); // right line
            Renderer.drawLine(grid, pa.maxX, pa.minY, pa.minX, pa.minY, lineColor); // bottom line
            Renderer.drawLine(grid, pa.minX, pa.minY, pa.minX, pa.maxY, lineColor); // bottom line
            
            //for (int[] loc : pa.pixelLocations) {
            //    grid[loc[1]][loc[0]] = lineColor;
            //}
        }

        Image output = new Image("output.png", grid[0].length, grid.length);
        output.setGrid(grid);
        System.out.println("finished with 0 errors!");
    }
}