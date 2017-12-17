package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.*;
import com.EyStudio.NanitWars.engine.units.Barack;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Queue;

/**
 * Created by daneel on 17.04.17.
 */
public class BuildBarackSkill implements ISkill {

    Unit owner;
    ICollisionManager collisionManager;

    @Override
    public boolean canCast(Object target) {
        if (!(target instanceof Vector2D))
            return false;
        Vector2D pos = (Vector2D) target;
        ILevel level = owner.getManager().getEngine().getMap().getLevel(Barack.DEFAULT_LEVEL);
        if (collisionManager.isAnyCollide(new UnitModel(pos, Barack.DEFAULT_RADIUS), 0, level));
        return true;
    }

    @Override
    public void cast(Object target) {
        if (!canCast(target))
            throw new IllegalArgumentException(target + "is bad target for skill " + getName());
        
        Vector2D pos = (Vector2D)target;
        int range = getRange();
        if (owner.getPosition().distanceSquared(pos) > range * range)
            owner.getTaskQueue().addLast(new GotoTask(owner, pos, getRange()));
        owner.getTaskQueue().addLast(new BuildBarackTask(owner, pos));
    }

    @Override
    public String getName() {
        return "BuildBarack";
    }

    @Override
    public String getDescription() {
        return "Строит барак";
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    public int getCoolDown() {
        return 0;
    }

    @Override
    public SkillTargetType getTargetType() {
        return SkillTargetType.Point;
    }

    public BuildBarackSkill(Unit owner){
        this.owner = owner;
        collisionManager = owner.getManager().getEngine().getCollisionManager();
    }
    
    @Override
    public void serialize(ObjectOutput out) throws IOException{
    }
    
    public BuildBarackSkill(ObjectInput in,  Unit owner){
        this.owner = owner;
        collisionManager = owner.getManager().getEngine().getCollisionManager();
    }
}
