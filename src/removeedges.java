public class removeedges {

    public static short[][][] removeEdges(short[][] red, short[][] blue, short[][] green){

        short[][] cred = new short[red.length][red[0].length];
        short[][] cgreen = new short[red.length][red[0].length];
        short[][] cblue = new short[red.length][red[0].length];

        for(int y = 0; y < red.length; y++){
            L: for(int x= 0; x < red[0].length; x++){

                int[] loc = {x,y};
                int[] loca1 = {loc[0], loc[1]+1};
                int[] loca2 = {loc[0]+1, loc[1]};
                int[] loca3 = {loc[0], loc[1]-1};
                int[] loca4 = {loc[0]-1, loc[1]};
                int[][] locs = {loca1, loca2, loca3, loca4};

                for(int i = 0; i < locs.length; i++){

                    if(!inBound(red[0].length, red.length, locs[i][0], locs[i][1])){
                        continue L;

                    }
                    int xn = locs[i][0];
                    int yn = locs[i][1];
                    if(red[yn][xn] + green[yn][xn] + blue[yn][xn] == 0){
                        continue L;
                    }


                }

                cred[y][x] = red[y][x];
                cgreen[y][x] = green[y][x];
                cblue[y][x] = blue[y][x];





            }
        }

        return new short[][][]{cred, cgreen, cblue};

    }

    public static boolean inBound(int xl, int yl, int x, int y){

        return (x < xl && x >= 0) && (y < yl && y >= 0);

    }


}
