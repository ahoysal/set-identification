import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class getcolor {


    public static Object[][] colorList = {

            {255,0,0, "red"},
            {0,0,255, "blue"},
            {0,255,0, "green"},
            {0,160,0, "darkgreen"},
            {120,100,200, "purple"},
            {0,0,0, "black"},
            {255,255,255, "white"}

    };

    public static Object[] color(short r, short g, short b){


        ArrayList<Object[]> colors = new ArrayList<Object[]>();

        for(int a = 0; a < colorList.length; a++){
            Object[] yo = colorList[a];

            //float[] hsb = new float[3];
            //Color.RGBtoHSB(r, g, b, hsb);
            float[] color = new float[3];

            java.awt.Color.RGBtoHSB(r,g,b,color);

            int col = java.awt.Color.HSBtoRGB(color[0], 1f, 0.5f);

            r = (short)Color.getRed(col);
            g = (short)Color.getGreen(col);
            b = (short)Color.getBlue(col);


            colors.add(new Object[]{
                    Math.abs(r - (int)yo[0]) + Math.abs(g - (int)yo[1]) + Math.abs(b - (int)yo[2]),
                    yo[3]
            });

        }

        colors.sort(Comparator.comparingInt((Object[] a) -> (int) a[0]));

        return new Object[]{(String)colors.get(0)[1], r, g, b};



    }







}
