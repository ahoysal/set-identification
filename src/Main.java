import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Desktop;

public class Main {

    /*

        TODO: 
    */

    public static void main(String[] args) {
        imageTest();
    }

    public static void imageTest() {
        identify("inputs/input1.jpeg", "outputs/output1.png");
        //identify("inputs/input2.jpeg", "outputs/output2.png");
        //identify("inputs/input3.png", "outputs/output3.png");
        identify("inputs/input4.jpeg", "outputs/output4.png", true);
        //identify("inputs/input5.jpeg", "outputs/output5.png");
        //identify("inputs/input6.jpeg", "outputs/output6.png", true);

        System.out.println("finished with 0 errors!");
    }

    private static long identify(String inputPath, String outputPath) {
        long start = System.currentTimeMillis();

        // get and prepare image
        Image input = new Image(inputPath);
        int[][] grid = input.getGrid();

        // find, print, and draw cards to grid
        ArrayList<Card> cards = Card.getCards(Image.convert(grid));
        for (Card card : cards) {
            System.out.println(card);
            card.draw(grid);
        }

        // find and print matches found
        ArrayList<Card[]> matches = Card.matches(cards);
        for (Card[] m : matches) {
            System.out.println(Arrays.deepToString(m));
        }

        // create and set a new output image (so we can run more tests on the input image)
        Image output = new Image(outputPath, grid[0].length, grid.length);
        output.setGrid(grid);

        return System.currentTimeMillis() - start; // return total time
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