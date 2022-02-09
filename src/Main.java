import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Desktop;

public class Main {
    public static void main(String[] args) {
        Image input = new Image("inputs/input6.jpeg");

        int[][] q = input.getGrid();
        short[][][] grid = Image.convert(q);

        ArrayList<Card> cards = Card.getCards(grid);
        for (Card card : cards) {
            System.out.println(card);
            card.draw(q);
        }

        ArrayList<Card[]> matches = Card.matches(cards);
        for (Card[] m : matches) {
            System.out.println(Arrays.deepToString(m));
        }


        Image output = new Image("outputs/output6.png", grid[0].length, grid.length);

        output.setGrid(q);
        
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

    private static void identify(String inputPath, String outputPath) {
        long start = System.currentTimeMillis();
        Image input = new Image(inputPath);

        short[][][] grid = Image.convert(input.getGrid());
        
        short[][][] newgrid = highlight.processImage(grid[0], grid[1], grid[2]);

        Image output = new Image(outputPath, grid[0].length, grid.length);

        System.out.println("yo: " + (System.currentTimeMillis() - start));

        output.setGrid(Image.convert(newgrid));

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