public class highlight {
    
    final static int totalSize = 762048;

    public static int[][] prepareImage(int[][] grid) {
        double divide = Math.sqrt(totalSize / (double) (grid.length * grid[0].length));
        Scaler scaler = new Scaler((int) (divide * grid[0].length), (int) (divide * grid.length));

        grid = scaler.processImage(grid);

        short[][][] sg = Image.convert(grid);

        short[][] blank = new short[grid.length][grid[0].length];
                
        for (int[] loc : ritikareadetection.floodDetection(sg[0], sg[1], sg[2], blank, blank, new int[]{0, 0}, Color.int2sarr(grid[0][0]), new int[]{1, 1, 1}, 1, new int[]{234, 234, 234}, false).pixelLocations) {
            grid[loc[1]][loc[0]] = Color.rgb2int(100, 100, 100);
        }

        grid = Blurer.applyBlur(grid, 1);

        return grid;
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

}

