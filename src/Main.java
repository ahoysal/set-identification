import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Desktop;

public class Main {
    public static void main(String[] args) {
        imageTest();
    }

    public static void imageTest() {
        //identify("inputs/input1.jpeg", "outputs/output1.png");
        //identify("inputs/input2.jpeg", "outputs/output2.png");
        //identify("inputs/input3.png", "outputs/output3.png");
        //identify("inputs/input4.jpeg", "outputs/output4.png", true);
        //identify("inputs/input5.jpeg", "outputs/output5.png");
        identify("inputs/input6.jpeg", "outputs/output6.png", true);

        System.out.println("finished with 0 errors!");
    }

    private static long identify(String inputPath, String outputPath) {
        long start = System.currentTimeMillis();

        // get and prepare image
        Image input = new Image(inputPath);

        int[][] grid = highlight.prepareImage(input.getGrid());

        // find, print, and draw cards to grid
        ArrayList<Card> cards = Card.getCards(Image.convert(grid));
        for (Card card : cards) {
            System.out.println(card);
            card.draw(grid);
        }

        int xl = grid[0].length; // for larger grid with matches
        int extendAmount = 600;
        int[][] extended = new int[grid.length][grid[0].length + extendAmount];

        for(int y = 0; y < grid.length; y++){
            for(int x = 0; x < grid[0].length; x++){
                extended[y][x] = grid[y][x];
            }
        }


        // find and print matches found
        ArrayList<Card[]> matches = Card.matches(cards);

        int cardW = extendAmount / 3;
        int cardH = matches.size() * extendAmount / 5 > grid.length ?  grid.length / matches.size() : extendAmount / 5;

        int setcardYP = cardH;
        int setcardYC = 0;


        for (Card[] m : matches) {
            for(int i = 0; i < m.length; i++){
                drawCard(m[i], extended, xl + cardW*i, setcardYC, cardW, cardH);
            }
            setcardYC += setcardYP;
            System.out.println(Arrays.deepToString(m));
        }

        // create and set a new output image (so we can run more tests on the input image)
        Image output = new Image(outputPath, extended[0].length, extended.length);
        output.setGrid(extended);

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



    public static void drawCard(Card card, int[][] grid, int xp, int yp, int sx, int sy){
        short[][][] square  = card.cardArea.pixelSquare;


        int[][] squareConverted = Image.convert(square);

        int cardw = sx;
        int cardh = sy;

        Scaler scaleCard = new Scaler(cardw, cardh);

        int[][] squareScaled = scaleCard.processImage(squareConverted);

        for(int y = 0; y < cardh; y++){
            for(int x = 0; x < cardw; x++){

                grid[y+yp][x+xp] = squareScaled[y][x];

            }
        }
    }

}