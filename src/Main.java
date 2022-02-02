import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        imageTest();
    }

    public static void imageTest() {

        identify("inputs/input1.jpeg", "outputs/output1.png");
        identify("inputs/input2.jpeg", "outputs/output2.png");
        identify("inputs/input3.png", "outputs/output3.png");
        identify("inputs/input4.jpeg", "outputs/output4.png");
        identify("inputs/input5.jpeg", "outputs/output5.png");

        System.out.println("finished with 0 errors!");
    }

    private static void identify(String inputPath, String outputPath) {

        Image input = new Image(inputPath);

        int[][] grid = input.getGrid();
        
        ArrayList<PixelArea> pas = AreaDetection.processImage(grid);
        System.out.println(pas.size());
        for (PixelArea pa : pas) {
            Card card = new Card(pa.pixelSquare);

            
            for (int r = 0; r < pa.pixelSquare.length; r++) {
                for (int c = 0; c < pa.pixelSquare[r].length; c++) {
                    grid[r + pa.minY][c + pa.minX] = pa.pixelSquare[r][c];
                }
            }
            
            
            
            //#region render box
            Renderer.drawRect(grid, pa.minX, pa.minY, pa.maxX, pa.maxY, card.color.getValue());
            Renderer.drawCross(grid, pa.minX, pa.minY, pa.maxX, pa.maxY, card.color.getValue());
            //#endregion
        }

        Image output = new Image(outputPath, grid[0].length, grid.length);
        output.setGrid(grid);
    }
}