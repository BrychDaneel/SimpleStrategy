/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.SimpleLevel;

/**
 *
 * @author daneel
 */
public class ArrayLevel extends SimpleLevel{

    public ArrayLevel(boolean[][] array, String name, int levelID){
        super(name, levelID);
        
        this.name = name;
        this.levelID = levelID;
        
        this.fill = array.clone();
    }
}
