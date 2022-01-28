import java.util.ArrayList;

public class ritikareadetection{

    //Say there are two pixels, one is at the top right of the other, should it be counted as "connected"?
    public boolean adjacents = true;
    //Color of the area we should detect
    public short[] color = {255,255,255};
    //The range of values
    public int[] colorVariance = {5,5,5};

    //Imagine there's a red circle on a canvas
    //However, there's a random 1 pixel line splitting it in half!
    //If you were to run the algorithm on it
    //It'd count as two different areas, but we want to get the entire circle
    //If you increase neighborSize to 2, it'll bypass the line
    //If it's a 2 pixel wide line, you can set it to 3
    //neighborSize makes the program exponentially more laggy though
    public int neighborSize = 1;


    public ArrayList<PixelArea> processImage(short[][] red, short[][] green, short[][] blue) {
        //We store pixels of one's we have found
        short[][] foundpixels = new short[red.length][red[0].length];
        //We store seperate areas of pixels
        ArrayList<PixelArea> areas = new ArrayList<PixelArea>();

        //Loop through all the pixels...
        for(int y = 0; y < red.length; y++){

            ColLoop: for(int x = 0; x < red[0].length; x++){
                //If the pixel we are currently on, is contained in an area, we continue
                //We dont wanna execute our area algorithm on it.
                if(foundpixels[y][x] != 0) continue;
                //Store the RGB vals of the pixel
                short[] pixel = {red[y][x], green[y][x], blue[y][x]};

                PixelArea area;

                //[Math.abs(color[0] - pixel[0]), Math.abs(color[1] - pixel[1]), Math.abs(color[1] - pixel[1])]
                int[] colordifference = difference(color, pixel);


                //See if any of the differences is beyond the colorVariance
                for(int i = 0; i < colordifference.length; i++){
                    //If so, we move on from this pixel, we should not run our area alg on it
                    if(colordifference[i] > colorVariance[i]) continue ColLoop;
                }
                //(x,y) format of location
                int[] loc = {x,y};
                //run algorithm
                area = floodDetection(red, green, blue, foundpixels, loc, color, colorVariance, neighborSize, adjacents);
                //we get one single area, the area that the pixel was inside of
                //now all pixels inside of that area were put in foundpixels
                areas.add(area);
                //add it

            }

        }

        return areas;

    }


    public PixelArea floodDetection(short[][] red, short[][] green, short[][] blue, short[][] copy, int[] start, short[] color, int[] colorVariance, int neighborSize, boolean adjacents){
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

                    short[] pixel2 = {red[loc2[1]][loc2[0]], green[loc2[1]][loc2[0]], blue[loc2[1]][loc2[0]] };
                    int[] colordifference = difference(color, pixel2);

                    for(int i3 = 0; i3 < colordifference.length; i3++){
                        if(colordifference[i3] > colorVariance[i3]) continue NeighborsLoop;
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

        pixelSquare = new short[maxY - minY + 1][maxX - minX + 1][3];

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

                copy[y][x] = 1;
                pixelSquare[y - minY][x - minX] = new short[]{red[y][x], green[y][x], blue[y][x]};

            }

        }

        PixelArea finalarea = new PixelArea();

        finalarea.maxX = maxX;
        finalarea.maxY = maxY;

        finalarea.minX = minX;
        finalarea.minY = minY;

        finalarea.pixelSquare = pixelSquare;
        finalarea.pixelLocations = pixelsflooded;

        return finalarea;

    }


    public ArrayList<int[]> getNeighbors(int[] loc, int size, boolean adjacent){

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

    public int[] difference(short[] color, short[] color2){

        int[] sum = new int[color.length];



        for(int i = 0; i < color.length; i++) sum[i] = Math.abs(color[i] - color2[i]);

        return sum;



    }

    public boolean inbound(int xl, int yl, int x, int y){
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

}



