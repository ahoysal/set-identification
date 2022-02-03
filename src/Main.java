import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Desktop;

public class Main {
    public static void main(String[] args) {
        imageTest();
    }

    public static void imageTest() {

        identify("inputs/input1.jpeg", "outputs/output1.png");
        //identify("inputs/input2.jpeg", "outputs/output2.png");
        //identify("inputs/input3.png", "outputs/output3.png");
        identify("inputs/input4.jpeg", "outputs/output4.png", true);
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
    private static void identify(String inputPath, String outputPath, boolean open) {
        identify(inputPath, outputPath);
        if(open){
            if(!Desktop.isDesktopSupported()){
                System.out.println("can't open file, desktop aint supported");
            }
            File file = new File(outputPath);
            Desktop desktop = Desktop.getDesktop();

            if(file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else System.out.println("file don't exist");
        }
    }
}