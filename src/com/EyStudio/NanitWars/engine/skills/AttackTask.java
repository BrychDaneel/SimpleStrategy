/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.Damage;
import com.EyStudio.NanitWars.engine.IEventProxy;
import com.EyStudio.NanitWars.engine.IUnitTask;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.Vector2D;
import com.EyStudio.NanitWars.engine.events.AttackEvent;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayDeque;

/**
 *
 * @author daneel
 */
public class AttackTask implements IUnitTask{

    private final int targetID;
    private final Damage damage;
    private final Unit owner;
    private final int range;

    @Override
    public boolean perform(Unit source) {

        Unit target = owner.getManager().getUnit(targetID);
        int extRange = range + target.getRadius();
        int distSquared = owner.getPosition().distanceSquared(target.getPosition());
        if (distSquared > extRange * extRange){
            owner.getTaskQueue().addFirst(new GotoTask(owner, target.getPosition(), extRange));
            return false;
        }
        
        target.takeDamage(owner, damage);
        
        IEventProxy eventProxy = owner.getManager().getEngine().getEventProxy();
        eventProxy.raiseEvent(new AttackEvent(owner, target, damage));
        
        return true;
    }
    
    public AttackTask(Unit owner, Unit target, Damage damage,  int range){
        this.owner = owner;
        this.targetID = target.getUnitID();
        this.damage = damage;
        this.range = range;
    }
    
    @Override
    public void serialize(ObjectOutput out) throws IOException{
        out.writeInt(targetID);
        out.writeInt(range);
        out.writeObject(damage);
    }
    
    public AttackTask(ObjectInput in,  Unit owner)  throws IOException, ClassNotFoundException { 
        this.owner = owner;
        targetID = in.readInt();
        range = in.readInt();
        damage = (Damage) in.readObject();
      
    }
}
