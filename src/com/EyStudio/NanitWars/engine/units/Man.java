package com.EyStudio.NanitWars.engine.units;

import com.EyStudio.NanitWars.engine.IPlayer;
import com.EyStudio.NanitWars.engine.IUnitManager;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.skills.AttackSkill;
import com.EyStudio.NanitWars.engine.skills.CancelSkill;
import com.EyStudio.NanitWars.engine.skills.GotoSkill;
import java.io.IOException;
import java.io.ObjectInput;

/**
 * Created by daneel on 16.04.17.
 */
public class Man extends Unit {
    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public String getName() {
        return "Man";
    }

    @Override
    public int getGroupPriority() {
        return 0;
    }

    @Override
    public int getRadius() {
        return 2;
    }

    public Man(IUnitManager manager, IPlayer owner){
        super(manager, owner);
        speed = 20;
        skills.addSkill(new GotoSkill(this), "default");
        skills.addSkill(new CancelSkill(this), "default");
        skills.addSkill(new AttackSkill(this), "default");
    }
    
    public Man(ObjectInput in, IUnitManager unitManager) throws IOException, ClassNotFoundException {
        super(in, unitManager);
    }
}
