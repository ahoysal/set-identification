
public class Convolution{
    double[][] blurKernel =
            {       {1.0/9, 1.0/9, 1.0/9},
                    {1.0/9, 1.0/9, 1.0/9},
                    {1.0/9, 1.0/9, 1.0/9}   };

    double[][] outlineKernel =
            {
                    {-1, -1, -1},
                    {-1, 6, -1},
                    {-1, -1, -1}   };

    double[][] embossKernel =
            {
                    {-2, -1, 0},
                    {-1, 1, 1},
                    {0, 1, 2}   };

    double[][] sharpenKernel = {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
    };
    /*
    public DImage processImage(short[][] red, short[][] green, short[][] blue) {





    }
    */


    public short[][][] drawOverlay(short[][] red, short[][] green, short[][] blue, double[][] filter) {


        int yl = red.length;
        int xl = red[0].length;

        short[][][] l = {red, green, blue};
        short[][][] lo = {new short[yl][xl],new short[yl][xl],new short[yl][xl]};

        for(int y = 0; y < red.length; y++){
            for(int x = 0; x < red[0].length; x++){
                for(int i = 0; i < lo.length; i++){
                    lo[i][y][x] = l[i][y][x];
                }
            }
        }


        for(int y = filter.length; y < red.length - filter.length; y++){
            for(int x = filter.length; x < red[0].length - filter.length; x++){

                double[] sum = {0,0,0};
                int half = filter.length/2;

                for(int yp = -half; yp <= half; yp++){
                    for(int xp = -half; xp <= half; xp++){
                        for(int i = 0; i < l.length; i++){
                            //System.out.println((double)l[i][y + yp][x + xp] * filter[yp + half][xp + half]);
                            sum[i] += (double)l[i][y + yp][x + xp] * filter[yp + half][xp + half];
                        }

                    }
                }

                for(int i = 0; i < sum.length; i++){
                    sum[i] = Math.max(0, sum[i] > 255 ? 255 : sum[i] );


                    lo[i][y][x] = (short) sum[i];
                }


            }
        }

        return new short[][][]{lo[0], lo[1], lo[2]};

    }






    public double[][] genblurKernel(int y){
        double[][] kernel = new double[y][y];
        //Set all the indexes to 1.0 / (y*y)
        for(int a = 0; a < y; a++){
            for(int b = 0; b < y; b++){
                kernel[a][b] = (double)1.0 / (y * y);
            }
        }

        return kernel;
    }

    public double[][] genoutlineKernel(int y){
        double[][] kernel = new double[y][y];
        //Just make all the indexes -1
        for(int a = 0; a < y; a++){
            for(int b = 0; b < y; b++){
                kernel[a][b] = (double)-1.0;
            }
        }
        //Set middle index
        kernel[y/2][y/2] = y*y-1;

        return kernel;
    }

    public short[][][] fasterblur(short[][] red, short[][] green, short[][] blue, int size){

        int yl = red.length;
        int xl = red[0].length;

        short[][][] l = {red, green, blue};
        int[][][] lo = {new int[yl][xl],new int[yl][xl],new int[yl][xl]};
        //lo, is an array where each index is the sum of the pixels <size> above and below it.


        for(int x = 0; x < xl; x++){
            int[] sums = {0,0,0};
            for(int y = 0; y < yl; y++){

                //Color channels stored in array
                for(int i = 0; i < l.length; i++){
                    sums[i] += l[i][y][x];
                }

                //we constantly add the value of the index, to sum
                //we subtract the value <size> squares above us
                //we set lo to sum value.

                if(y >= size){
                    for(int i =0; i < l.length; i++){

                        sums[i] -= l[i][y - size][x];
                        lo[i][y - size/2][x] = sums[i];

                    }
                }


            }

        }

        for(int y = size/2+1; y < yl - size/2; y++){
            int[] sums2 = {0,0,0};
            for(int x = 0; x < xl; x++){

                for(int i = 0; i < l.length; i++){
                    sums2[i] += lo[i][y][x];
                }

                if(x >= size){
                    for(int i =0; i < l.length; i++){

                        sums2[i] -= lo[i][y][x - size];
                        l[i][y][x - size/2] = (short) (sums2[i] / (size * size));


                    }
                }


            }
        }

        return new short[][][]{l[0], l[1], l[2]};

    }
}
