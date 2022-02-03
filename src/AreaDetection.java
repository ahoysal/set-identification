import java.util.ArrayList;

/**
 * @author Algorithim by Ritik
 * Sligtly modified to take int arrays by Aagrim
 */
public class AreaDetection {
/*
    //Say there are two pixels, one is at the top right of the other, should it be counted as "connected"?
    public static boolean adjacents = true;
    //Color of the area we should detect
    public static int color = 0xFFFFFFFF;
    //The range of values
    public static double colorVariance = 0.06;

    //Imagine there's a red circle on a canvas
    //However, there's a random 1 pixel line splitting it in half!
    //If you were to run the algorithm on it
    //It'd count as two different areas, but we want to get the entire circle
    //If you increase neighborSize to 2, it'll bypass the line
    //If it's a 2 pixel wide line, you can set it to 3
    //neighborSize makes the program exponentially more laggy though
    public static int neighborSize = 1;


    public static ArrayList<PixelArea> processImage(int[][] grid) {
        //We store pixels of one's we have found
        int[][] foundpixels = new int[grid.length][grid[0].length];
        //We store seperate areas of pixels
        ArrayList<PixelArea> areas = new ArrayList<PixelArea>();

        //Loop through all the pixels...
        for(int y = 0; y < grid.length; y++){

            ColLoop: for(int x = 0; x < grid[0].length; x++){
                //If the pixel we are currently on, is contained in an area, we continue
                //We dont wanna execute our area algorithm on it.
                if(foundpixels[y][x] != 0) continue;
                //Store the RGB vals of the pixel
                int pixel = grid[y][x];

                PixelArea area;

                //[Math.abs(color[0] - pixel[0]), Math.abs(color[1] - pixel[1]), Math.abs(color[1] - pixel[1])]
                
                //See if any of the differences is beyond the colorVariance
                //If so, we move on from this pixel, we should not run our area alg on it
                if (Color.getDistance2(color, pixel) > colorVariance) continue ColLoop;

                //(x,y) format of location
                int[] loc = {x,y};
                //run algorithm
                area = floodDetection(grid, foundpixels, loc, color, colorVariance, neighborSize, adjacents);
                //we get one single area, the area that the pixel was inside of
                //now all pixels inside of that area were put in foundpixels
                areas.add(area);
                //add it

            }

        }

        return areas;

    }


    public static PixelArea floodDetection(int[][] grid, int[][] copy, int[] start, int color, double colorVariance, int neighborSize, boolean adjacents) {
        //note it's not really the flood fill algorithm
        //flood is a good name for what i'm doing thuogh

        //the best way to describe this algorithm is
        //imagine a rat maze
        //(the walls are pixels we don't want)

        //now at the middle of the maze (int[] start)
        //pour water there
        //the area the water covers is the area that the algorithm finds
        //once all the area is found, we stop.

        ArrayList<int[]> flooders = new ArrayList<int[]>();
        ArrayList<int[]> newflooders = new ArrayList<int[]>();
        ArrayList<int[]> pixelsflooded = new ArrayList<int[]>();
        int[][] pixelSquare;

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
                    if(inbound(grid[0].length, grid.length, loc2[0], loc2[1]) == false) continue;
                    if(copy[loc2[1]][loc2[0]] != 0) continue;

                    int pixel2 = grid[loc2[1]][loc2[0]];
                    if (Color.getDistance2(color, pixel2) > colorVariance) continue NeighborsLoop;

                    // set min and maxes accordingly
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

        pixelSquare = new int[maxY - minY + 1][maxX - minX + 1];

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
                pixelSquare[y - minY][x - minX] = grid[y][x];

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

    public static boolean inbound(int xl, int yl, int x, int y){
        return (x > -1 && y > -1 && x < xl && y < yl);
    }
*/
}



