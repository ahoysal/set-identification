
import java.util.Locale;


public class drawtext {

    public String text = "working";
    public int x = 0;
    public int y = 0;

    public short r = 255;
    public short g = 255;
    public short b = 255;
    public double opacity = 0.5;

    int width = 15;

    public int processImage(short[][] red, short[][] green, short[][] blue) {

        // Do stuff with color channels here

        text = text.toLowerCase();

        int x = this.x;
        int y = this.y;

        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        letters dumb = new letters();

        for(int a = 0; a < text.length(); a++){

            if(x > red[0].length) break;
            if(y > red.length) break;

            short[][] redt = new short[width][width];
            short[][] greent = new short[width][width];
            short[][] bluet = new short[width][width];

            if(text.charAt(a) == ' '){

                drawSquare(x, y, redt, greent, bluet, red, green, blue);
                x += width;
                continue;
            }

            int[][] pos = dumb.letters[chars.indexOf(text.charAt(a))];

            int maxX = 0;
            int maxY = 0;

            for(int i = 0; i < pos.length; i++){

                int[] loc = pos[i];

                if(loc[0] > maxX) maxX = loc[0];
                if(loc[1] > maxY) maxY = loc[1];

            }

            redt = new short[maxY+2][maxX+2];
            greent = new short[maxY+2][maxX+2];
            bluet = new short[maxY+2][maxX+2];

            for(int i = 0; i < pos.length; i++){



                redt[pos[i][1]][pos[i][0]] = r;
                greent[pos[i][1]][pos[i][0]] = g;
                bluet[pos[i][1]][pos[i][0]] = b;
            }

            drawSquare(x, y, redt, greent, bluet, red, green, blue);

            x += maxX + 2;


        }

        return x;
    }


    public void drawSquare(int xa, int ya, short[][] redp, short[][] greenp, short[][] bluep, short[][] red, short[][] green, short[][] blue){


        for(int y = 0; y < redp.length; y++){
            for(int x = 0; x < redp[0].length; x++){

                if(y + ya >= red.length || y + ya < 0) continue;
                if(x + xa >= red[0].length || x + xa < 0) continue;

                red[y + ya][x + xa] = (short)( ((double)redp[y][x])*opacity + ((double)red[y+ya][x+xa])*(1-opacity));
                green[y + ya][x + xa] = (short)( ((double)greenp[y][x])*opacity + ((double)green[y+ya][x+xa])*(1-opacity));
                blue[y + ya][x + xa] = (short)( ((double)bluep[y][x])*opacity + ((double)blue[y+ya][x+xa])*(1-opacity));


            }
        }




    }


}

