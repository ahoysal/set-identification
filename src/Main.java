import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        imageTest();
    }

    public static void imageTest() {

        identify("inputs/input1.jpeg", "outputs/output1.png");
        //identify("inputs/input2.jpeg", "outputs/output2.png");
        //identify("inputs/input3.png", "outputs/output3.png");
        identify("inputs/input4.jpeg", "outputs/output4.png");
        //identify("inputs/input5.jpeg", "outputs/output5.png");

        System.out.println("finished with 0 errors!");
    }

    private static void identify(String inputPath, String outputPath) {
        long start = System.currentTimeMillis();
        Image input = new Image(inputPath);

        short[][][] grid = input.convert(input.getGrid());
        
        short[][][] newgrid = highlight.processImage(grid[0], grid[1], grid[2]);

        Image output = new Image(outputPath, grid[0].length, grid.length);

        System.out.println("yo: " + (System.currentTimeMillis() - start));

        output.setGrid(input.convert(newgrid));

    }
}