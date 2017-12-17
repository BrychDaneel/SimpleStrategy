package com.EyStudio.NanitWars.engine.units;

import com.EyStudio.NanitWars.engine.IPlayer;
import com.EyStudio.NanitWars.engine.IUnitManager;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.skills.BuildBarackSkill;
import com.EyStudio.NanitWars.engine.skills.CancelSkill;
import com.EyStudio.NanitWars.engine.skills.GotoSkill;
import java.io.IOException;
import java.io.ObjectInput;

/**
 * Created by daneel on 16.04.17.
 */
public class Worker extends Unit {
    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public String getName() {
        return "Worker";
    }

    @Override
    public int getGroupPriority() {
        return 0;
    }

    @Override
    public int getRadius() {
        return 2;
    }

    public Worker(IUnitManager manager, IPlayer owner){
        super(manager, owner);
        speed = 10;
        skills.addSkill(new GotoSkill(this), "default");
        skills.addSkill(new CancelSkill(this), "default");
        skills.addSkill(new BuildBarackSkill(this), "default");
    }
    
    public Worker(ObjectInput in, IUnitManager unitManager) throws IOException, ClassNotFoundException {
        super(in, unitManager);
    }
}
