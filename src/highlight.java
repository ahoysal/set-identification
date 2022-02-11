
import java.util.ArrayList;


public class highlight{
    
    final static int totalSize = 762048;

    public static int[][] prepareImage(int[][] grid) {
        double divide = Math.sqrt(totalSize / (double) (grid.length * grid[0].length));

        System.out.println((int) (divide * grid[0].length));
        System.out.println((int) (divide * grid.length));
        Scaler scaler = new Scaler((int) (divide * grid[0].length), (int) (divide * grid.length));
        
        return scaler.processImage(grid);
    }

    public static short[][][] processImage(short[][] red, short[][] green, short[][] blue) {

        double[] shapes = {0.096, 0.128, 100};
        String[] shapesStr = {"diamond", "squiggly", "circle"};

        double[] states = {8, 200, 1000};
        String[] statesStr = {"hollow", "shaded", "filled"};
        /*
        Convolution filter = new Convolution();
        short[][][] op = filter.drawOverlay(red, green, blue, filter.blurKernel);

        red = op[0];
        green = op[1];
        blue = op[2];
        */

        ritikareadetection.colorRatio = new double[]{1,1};
        ritikareadetection.colorRatioVariance = new double[]{0.14,0.14};
        ritikareadetection.colorRatioMaximum = new int[]{160,160,160};
        ritikareadetection.neighborSize = 1;
        ritikareadetection.inverse = false;


        ArrayList<PixelArea> foundAreas = ritikareadetection.processImage(red, green, blue);

        drawtext text = new drawtext();


        for(int i = 0; i < foundAreas.size(); i++){

            ritikareadetection.neighborSize = 1;

            short[][][] area = foundAreas.get(i).pixelSquare;
            int size = foundAreas.get(i).pixelSquareLocations.size();



            //A lot of useless areas are picked up on, remove those
            if(size < 500){
                foundAreas.remove(i);
                i--;
                continue;
            }


            ritikareadetection.inverse = true;
            ArrayList<PixelArea> newAreas = ritikareadetection.processImage(area[0], area[1], area[2]);


            int totalPixels = 0;
            int areas = 0;
            double ratio;

            //SHAPE ritikareadetectionION
            for(int i2 = 0; i2 < newAreas.size(); i2++){
                int size2 = newAreas.get(i2).pixelSquareLocations.size();

                if(size2 < 200){
                    newAreas.remove(i2);
                    i2--;
                    continue;
                }

                totalPixels += size2;
                areas++;

                newAreas.get(i2).minX += foundAreas.get(i).minX;
                newAreas.get(i2).minY += foundAreas.get(i).minY;

            }

            if(newAreas.size() == 0) continue;

            ratio = (double)totalPixels / (size * areas);


            PixelArea area1 = newAreas.get(0);

            int[] avgCol = getAvgSquare(area1, red, green, blue);

            for(int r = 0; r < 6; r++){

                short[][][] newedged = removeedges.removeEdges(area1.pixelSquare[0],area1.pixelSquare[1],area1.pixelSquare[2] );

                area1.pixelSquare[0] = newedged[0];
                area1.pixelSquare[1] = newedged[1];
                area1.pixelSquare[2] = newedged[2];

            }


            int[] avg = getAvg(foundAreas.get(i), red, green, blue);

            double dist = getAvgDistance(area1, red, green, blue, avg, 6);

            Object[] color = getcolor.color((short)avgCol[0],(short)avgCol[1],(short)avgCol[2]);



            text.text = "amount " + areas;
            text.x = (foundAreas.get(i).minX + foundAreas.get(i).maxX)/2 - 70;
            text.y = (foundAreas.get(i).minY + foundAreas.get(i).maxY)/2 - 40;
            text.r = 255;
            text.g = 255;
            text.b = 255;
            text.opacity = 0.8;
            text.processImage(red, green, blue);


            text.y += 20;
            text.text = "shape " + getShape(ratio, shapes, shapesStr);
            text.processImage(red, green, blue);

            text.y += 20;
            text.text = "color " + color[0];
            text.processImage(red, green, blue);

            text.y += 20;
            text.text = "statea " + getShape(dist, states, statesStr) + " " + (int)dist;
            text.processImage(red, green, blue);

        }

        if(foundAreas.size() == 0){
            text.text = "cant find cards ";
            text.x = red[0].length / 2;
            text.y = red.length / 2;
            text.opacity = 0.6;
            text.processImage(red, green, blue);
        }


        return new short[][][]{red, green, blue};

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

