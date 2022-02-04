import java.util.ArrayList;

public class ritikareadetection{

    //Say there are two pixels, one is at the top right of the other, should it be counted as "connected"?
    public static boolean adjacents = true;
    //Color of the area we should detect
    public static short[] color = {255,255,255};
    //The range of values
    public static int[] colorVariance = {5,5,5};

    //{Green divided by Red, Blue divided by Green}
    //This is useful if you wanna select colors based on a ratio
    //For example, any shade of white has a ratio of 1:1:1

    public static double[] colorRatio = new double[]{1,1};;
    public static double[] colorRatioVariance = new double[]{0.14,0.14};
    public static int[] colorRatioMaximum = new int[]{160,160,160};


    public static boolean inverse = false;

    public static boolean ignoreBlack = true;

    //Imagine there's a red circle on a canvas
    //However, there's a random 1 pixel line splitting it in half!
    //If you were to run the algorithm on it
    //It'd count as two different areas, but we want to get the entire circle
    //If you increase neighborSize to 2, it'll bypass the line
    //If it's a 2 pixel wide line, you can set it to 3
    //neighborSize makes the program exponentially more laggy though
    public static int neighborSize = 1;


    public static ArrayList<PixelArea> processImage(short[][] red, short[][] green, short[][] blue) {
        //We store pixels of one's we have found
        short[][] foundpixels = new short[red.length][red[0].length];
        short[][] badpixels = new short[red.length][red[0].length];
        //We store seperate areas of pixels
        ArrayList<PixelArea> areas = new ArrayList<PixelArea>();

        //Loop through all the pixels...
        for(int y = 0; y < red.length; y++){

            ColLoop: for(int x = 0; x < red[0].length; x++){
                //If the pixel we are currently on, is contained in an area, we continue
                //We dont wanna execute our area algorithm on it.
                if(foundpixels[y][x] == 1) continue;
                if(badpixels[y][x] == 2) continue;

                //Store the RGB vals of the pixel
                short[] pixel = {red[y][x], green[y][x], blue[y][x]};





                PixelArea area;

                if( (pixel[0] + pixel[1] + pixel[2] == 0) && ignoreBlack){
                    badpixels[y][x] = 2;
                    continue ColLoop;
                }

                //[Math.abs(color[0] - pixel[0]), Math.abs(color[1] - pixel[1]), Math.abs(color[1] - pixel[1])]
                boolean diff = colorRatio == null ?  difference(color,pixel,colorVariance) : difference(pixel,colorRatio,colorRatioVariance,colorRatioMaximum);



                if( diff == inverse ){

                    badpixels[y][x] = 2;
                    continue ColLoop;

                }

                //(x,y) format of location
                int[] loc = {x,y};
                //run algorithm
                area = floodDetection(red, green, blue, foundpixels, badpixels, loc, color, colorVariance, neighborSize, adjacents);
                //we get one single area, the area that the pixel was inside of
                //now all pixels inside of that area were put in foundpixels
                areas.add(area);
                //add it

            }

        }

        return areas;

    }


    public static PixelArea floodDetection(short[][] red, short[][] green, short[][] blue, short[][] copy, short[][] badpixels, int[] start, short[] color, int[] colorVariance, int neighborSize, boolean adjacents){
        //note it's not really the flood fill algorithm
        //flood is a good name for what i'm doing thuogh

        //the best way to describe this algorithm is
        //imagine a rat maze
        //(the walls are pixels we don't want)

        //now at the middle of the maze (int[] start)
        //poor water there
        //the area the water covers is the area that the algorithm finds
        //once all the area is found, we stop.

        ArrayList<int[]> flooders = new ArrayList<int[]>();
        ArrayList<int[]> newflooders = new ArrayList<int[]>();
        ArrayList<int[]> pixelsflooded = new ArrayList<int[]>();
        short[][][] pixelSquare;

        flooders.add(start);
        pixelsflooded.add(start);

        int minX = start[0];
        int minY = start[1];
        int maxX = start[0];
        int maxY = start[1];

        while(true){

            for(int i = 0; i < flooders.size(); i++){

                int[] loc = flooders.get(i);

                ArrayList<int[]> neighbors = getNeighbors(loc, neighborSize, adjacents);

                NeighborsLoop: for(int i2 = 0; i2 < neighbors.size(); i2++){

                    int[] loc2 = neighbors.get(i2);
                    if(inbound(red[0].length, red.length, loc2[0], loc2[1]) == false) continue;
                    if(copy[loc2[1]][loc2[0]] != 0) continue;
                    if(badpixels[loc2[1]][loc2[0]] == 2) continue;

                    short[] pixel2 = {red[loc2[1]][loc2[0]], green[loc2[1]][loc2[0]], blue[loc2[1]][loc2[0]] };

                    if(pixel2[0] + pixel2[1] + pixel2[2] == 0 && ignoreBlack) continue NeighborsLoop;
                    if(badpixels[loc2[1]][loc2[0]] == 0){
                        boolean colordifference = colorRatio == null ?  difference(color,pixel2,colorVariance) : difference(pixel2,colorRatio,colorRatioVariance,colorRatioMaximum);

                        if( colordifference == inverse ){
                            badpixels[loc2[1]][loc2[0]] = 2;
                            continue NeighborsLoop;
                        }
                    }

                    if(loc2[0] < minX) minX = loc2[0];
                    if(loc2[1] < minY) minY = loc2[1];
                    if(loc2[0] > maxX) maxX = loc2[0];
                    if(loc2[1] > maxY) maxY = loc2[1];

                    copy[loc2[1]][loc2[0]] = 1;

                    newflooders.add(loc2);
                    pixelsflooded.add(loc2);

                }
            }

            if(newflooders.size() == 0) break;

            flooders = (ArrayList<int[]>) newflooders.clone();
            newflooders = new ArrayList<int[]>();

        }

        pixelSquare = new short[3][maxY - minY + 1][maxX - minX + 1];
        ArrayList<int[]> pixelSquareLocations = new ArrayList<int[]>();

        int[][] minMaxes = new int[maxY - minY + 1][2];

        for(int i = 0; i < minMaxes.length; i++){
            minMaxes[i] = new int[]{-1, -1};
        }

        for(int i = 0; i < pixelsflooded.size(); i++){

            int[] loc = pixelsflooded.get(i).clone();

            //loc[0] -= minX;
            loc[1] -= minY;

            int[] current = minMaxes[loc[1]];

            if(current[0] == -1) minMaxes[loc[1]] = new int[]{loc[0], loc[0]};
            if(loc[0] > current[1]) current[1] = loc[0];
            if(loc[0] < current[0]) current[0] = loc[0];

        }

        for(int i = 0; i < minMaxes.length; i++){

            int[] current;

            if( i > 0 && minMaxes[i][0] == -1){
                minMaxes[i] = new int[]{minMaxes[i-1][0], minMaxes[i-1][1]};
            }

            current = minMaxes[i];

            for(int i2 = current[0]; i2 <= current[1]; i2++){

                int y = i + minY;
                int x = i2;
                int sum = red[y][x]+green[y][x]+blue[y][x];

                copy[y][x] = 1;


                if( !(ignoreBlack && sum == 0) ) pixelSquareLocations.add(new int[]{x, y});
                pixelSquare[0][y - minY][x - minX] = red[y][x];
                pixelSquare[1][y - minY][x - minX] = green[y][x];
                pixelSquare[2][y - minY][x - minX] = blue[y][x];

            }

        }

        PixelArea finalarea = new PixelArea();

        finalarea.maxX = maxX;
        finalarea.maxY = maxY;

        finalarea.minX = minX;
        finalarea.minY = minY;

        finalarea.pixelSquare = pixelSquare;
        finalarea.pixelLocations = pixelsflooded;
        finalarea.pixelSquareLocations = pixelSquareLocations;



        return finalarea;

    }


    public static ArrayList<int[]> getNeighbors(int[] loc, int size, boolean adjacent){

        ArrayList<int[]> neighbors = new ArrayList<int[]>();

        for(int yp = -size; yp <= size; yp++){
            for(int xp = -size; xp <= size; xp++){

                if(!adjacent && Math.abs(yp + xp) == size*2) continue;
                if(yp == 0 && xp == 0) continue;
                int[] neighbor = {loc[0] + xp, loc[1] + yp};

                neighbors.add(neighbor);

            }
        }

        return neighbors;

    }

    public static boolean difference(short[] color, short[] color2, int[] range){

        //System.out.println("YUH!");

        int[] sum = new int[color.length];

        for(int i = 0; i < color.length; i++) if(Math.abs(color[i] - color2[i]) > range[i]) return false;

        return true;



    }

    public static boolean difference(short[] color, double[] colorRatio, double[] colorRatioVariance, int[] colorMaximum){

        //System.out.println("YUH");

        double[] col = {(double)color[1] / color[0], (double) color[2]/color[1]};

        if(Math.abs(colorRatio[0] - col[0]) > colorRatioVariance[0]
                ||Math.abs(colorRatio[1] - col[1]) > colorRatioVariance[1] ) return false;

        if(color[0] < colorMaximum[0] || color[1] < colorMaximum[1] || color[2] < colorMaximum[2]) return false;

        return true;




    }

    public static boolean inbound(int xl, int yl, int x, int y){
        return (x > -1 && y > -1 && x < xl && y < yl);
    }

}

class PixelArea{

    int minX;
    int minY;
    int maxX;
    int maxY;
    short[][][] pixelSquare;
    ArrayList<int[]> pixelLocations;
    ArrayList<int[]> pixelSquareLocations;

}



