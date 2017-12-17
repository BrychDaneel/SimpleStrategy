package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.ILevel;
import com.EyStudio.NanitWars.engine.SimpleLevel;

import java.awt.*;
import java.awt.image.PixelGrabber;

/**
 * Created by daneel on 16.04.17.
 */
public class ImageLevel extends SimpleLevel{
    
    public ImageLevel(Image image, String name, int levelID){
        super(name, levelID);
        
        this.name = name;
        this.levelID = levelID;

        int width = image.getWidth(null);
        int height = image.getHeight(null);
        int[] pixels = new int[width * height];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e){
            System.out.println("Interrupted while parse image");
        }

        fill = new boolean[width][height];
        for (int i=0; i<width; i++)
            for (int j=0; j<height; j++) {
                int color = pixels[i + j * width];
                int r = (color & 0xFF0000) >> 16;
                int g = (color & 0x00FF00) >> 8;
                int b = color & 0x0000FF;
                fill[i][j] = r==0 && g==0 && b==0;
            }
    }
}
