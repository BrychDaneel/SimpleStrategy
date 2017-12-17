/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.Unit;

/**
 *
 * @author daneel
 */
public class UnitDieEvent extends UnitEvent{

    public UnitDieEvent(Unit source) {
        super(source);
    }
    
}
