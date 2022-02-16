
import java.util.ArrayList;


public class highlight {
    
    final static int totalSize = 762048;

    public static int[][] prepareImage(int[][] grid) {
        double divide = Math.sqrt(totalSize / (double) (grid.length * grid[0].length));
        Scaler scaler = new Scaler((int) (divide * grid[0].length), (int) (divide * grid.length));
        
        return scaler.processImage(grid);
    }

    public static int[] getAvgSquare(PixelArea area, short[][] red, short[][] green, short[][] blue){
        int[] avgCol = {0,0,0};
        int counted = 0;

        for(int y = 0; y < area.pixelSquare[0].length; y++){
            for(int x = 0; x < area.pixelSquare[0][0].length; x++){

                short r = area.pixelSquare[0][y][x];
                short g = area.pixelSquare[1][y][x];
                short b = area.pixelSquare[2][y][x];

                if (r + g + b == 0) continue;
                counted++;

                avgCol[0] += r;
                avgCol[1] += g;
                avgCol[2] += b;
            }
        }

        avgCol[0] /= counted;
        avgCol[1] /= counted;
        avgCol[2] /= counted;

        return avgCol;
    }

    public static int[] getAvg(PixelArea area, short[][] red, short[][] green, short[][] blue){
        int[] avgCol = {0,0,0};
        int sizePix  = area.pixelLocations.size();
        int counted = 0;

        for(int i = 0; i < sizePix; i++) {

            int[] loc = area.pixelLocations.get(i);
            int y = loc[1];
            int x = loc[0];
            short r = red[y][x];
            short g = green[y][x];
            short b = blue[y][x];

            if (r + g + b == 0) continue;
            counted++;

            avgCol[0] += r;
            avgCol[1] += g;
            avgCol[2] += b;

        }

        avgCol[0] /= counted;
        avgCol[1] /= counted;
        avgCol[2] /= counted;

        return avgCol;
    }

    public static double getAvgDistance(PixelArea area, short[][] red, short[][] green, short[][] blue, int[] col, int clamp){
        double avg = 0;
        //int sizePix;
        int count = 0;
        //sizePix = area.pixelLocations.size();

        for(int y = 0; y < area.pixelSquare[0].length; y++) {
            for (int x = 0; x < area.pixelSquare[0][0].length; x++) {
                int r = area.pixelSquare[0][y][x];
                int g = area.pixelSquare[1][y][x];
                int b = area.pixelSquare[2][y][x];



                int dr = Math.abs(col[0] - r);
                int dg = Math.abs(col[1] - g);
                int db = Math.abs(col[2] - b);

                if(dr < clamp) dr = 0;
                if(dg < clamp) dg = 0;
                if(db < clamp) db = 0;

                //System.out.println(dr + " " + dg + " " + db);

                int da = dr+dg+db;

                //System.out.println(da);


                //red[y + area.minY][x + area.minX] = (short)(dr);
                //green[y + area.minY][x + area.minX] = (short)(dg);
                //blue[y + area.minY][x + area.minX] = (short)(db);

                if(r+g+b == 0) continue;
                avg += da;
                //System.out.println(avg);
                count++;
            }
        }


        return avg/count;
    }

    public void fillArea(short[][] red, short[][] green, short[][] blue, ArrayList<int[]> pixels, short[] color){

        for(int i = 0; i < pixels.size(); i++){

            int[] loc = pixels.get(i);

            red[loc[1]][loc[0]] = color[0];
            green[loc[1]][loc[0]] = color[1];
            blue[loc[1]][loc[0]] = color[2];

        }


    }

    public static String getShape(double r, double[] n, String[] s){

        for(int a = 0; a < n.length; a++){
            if(r < n[a]) return s[a];
        }

        return s[s.length-1];
    }

}

