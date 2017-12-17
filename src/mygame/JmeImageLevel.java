/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.SimpleLevel;
import com.jme3.texture.Image;
import java.nio.ByteBuffer;

/**
 *
 * @author daneel
 */
public class JmeImageLevel extends SimpleLevel{
    
    public JmeImageLevel(Image image, String name, int levelID){
        super(name, levelID);
        
        int w = image.getWidth();
        int h = image.getHeight();
        
        fill = new boolean[w][h];
        ByteBuffer buffer = image.getData(0);
        for (int i=0; i<h; i++)
            for (int ii=0; ii<w; ii++){
                int index = (i*w + ii) * 3;
                int r = buffer.get(index); 
                int g = buffer.get(index + 1);
                int b = buffer.get(index + 2);
                //int a = buffer.get(index + 3);
                fill[ii][h - i - 1] = (r==0 && g==0 && b==0);
                //fill[i][ii] = false;
            }
    }
    
}
