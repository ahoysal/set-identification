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
            Card card = new Card(pa.pixelSquare);

            /*
            for (int r = 0; r < pa.pixelSquare.length; r++) {
                for (int c = 0; c < pa.pixelSquare[r].length; c++) {
                    grid[r + pa.minY][c + pa.minX] = pa.pixelSquare[r][c];
                }
            }
            */
            
            
            //#region render box
            Renderer.drawRect(grid, pa.minX, pa.minY, pa.maxX, pa.maxY, card.color.getValue());
            //Renderer.drawCross(grid, pa.minX, pa.minY, pa.maxX, pa.maxY, card.color.getValue());
            //#endregion
        }

        Image output = new Image("output.png", grid[0].length, grid.length);
        output.setGrid(grid);
        System.out.println("finished with 0 errors!");
    }
}