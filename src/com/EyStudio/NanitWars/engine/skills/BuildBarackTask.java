package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.IUnitTask;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.Vector2D;
import com.EyStudio.NanitWars.engine.units.Barack;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


/**
 * Created by daneel on 17.04.17.
 */
public class BuildBarackTask implements IUnitTask {

    Unit owner;
    Vector2D destination;


    @Override
    public boolean perform(Unit Source) {
        (new Barack(owner.getManager(), owner.getOwner())).spawn(owner.getPosition(), destination);
        return true;
    }

    
    public BuildBarackTask(Unit owner, Vector2D destination){
        this.owner = owner;
        this.destination = destination;
    }
    

    @Override
    public void serialize(ObjectOutput out) throws IOException{
    }
    
    public BuildBarackTask(ObjectInput in,  Unit owner)  throws IOException, ClassNotFoundException { 
        this.owner = owner;
    }
}
