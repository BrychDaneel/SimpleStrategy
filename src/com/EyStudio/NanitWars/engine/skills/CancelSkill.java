package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Queue;

/**
 * Created by daneel on 17.04.17.
 */
public class CancelSkill implements ISkill {

    Unit owner;

    @Override
    public boolean canCast(Object target) {
        return target == null;
    }

    @Override
    public void cast(Object target) {
        if (target != null)
            throw new IllegalArgumentException(target + "is bad target for skill " + getName());

        Queue<IUnitTask> queue = owner.getTaskQueue();
        queue.clear();
    }

    @Override
    public String getName() {
        return "Cancel";
    }

    @Override
    public String getDescription() {
        return "Отменяет все задания";
    }

    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public int getCoolDown() {
        return 0;
    }

    @Override
    public SkillTargetType getTargetType() {
        return SkillTargetType.None;
    }

    public CancelSkill(Unit owner){
        this.owner = owner;
    }
    
    @Override
    public void serialize(ObjectOutput out) throws IOException{
    }
    
    public CancelSkill(ObjectInput in,  Unit owner){
        this.owner = owner;
    }
}
