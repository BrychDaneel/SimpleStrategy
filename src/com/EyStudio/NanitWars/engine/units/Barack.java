package com.EyStudio.NanitWars.engine.units;

import com.EyStudio.NanitWars.engine.IPlayer;
import com.EyStudio.NanitWars.engine.IUnitManager;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.skills.MakeManSkill;
import com.EyStudio.NanitWars.engine.skills.MakeWorkerSkill;
import java.io.IOException;
import java.io.ObjectInput;

/**
 * Created by daneel on 16.04.17.
 */
public class Barack extends Unit {
    
    public static final int DEFAULT_RADIUS = 10;
    
    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public String getName() {
        return "Barack";
    }

    @Override
    public int getGroupPriority() {
        return 1;
    }

    @Override
    public int getRadius() {
        return 10;
    }

    public Barack(IUnitManager manager, IPlayer owner){
        super(manager, owner);
        speed = 10;
        skills.addSkill(new MakeManSkill(this), "default");
        skills.addSkill(new MakeWorkerSkill(this), "default");
    }
    
    public Barack(ObjectInput in, IUnitManager unitManager) throws IOException, ClassNotFoundException {
        super(in, unitManager);
    }
}
