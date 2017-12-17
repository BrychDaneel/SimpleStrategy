/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.Damage;
import com.EyStudio.NanitWars.engine.Unit;

/**
 *
 * @author daneel
 */
public class AttackEvent extends UnitEvent{
    Unit target;
    Damage damage;
    
    public AttackEvent(Unit source, Unit target, Damage damage){
        super(source);
        this.target = target;
        this.damage = damage;
    }
}
