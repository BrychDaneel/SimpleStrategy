package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Queue;

/**
 * Created by daneel on 17.04.17.
 */
public class GotoSkill implements ISkill {

    Unit owner;

    @Override
    public boolean canCast(Object target) {
        return target instanceof Vector2D;
    }

    @Override
    public void cast(Object target) {
        try {
            Vector2D destination = (Vector2D) target;
            Queue<IUnitTask> queue = owner.getTaskQueue();
            queue.add(new GotoTask(owner, destination));
        } catch (ClassCastException e){
            System.out.println(target + "is bad target for skill " + getName());
        }
        catch(NullPointerException e){
            System.out.println("Null is bad target for skill " + getName());
        }
    }

    @Override
    public String getName() {
        return "Goto";
    }

    @Override
    public String getDescription() {
        return "Перейти на выбранную позицию";
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
        return SkillTargetType.Point;
    }

    public GotoSkill(Unit owner){
        this.owner = owner;
    }
    
    @Override
    public void serialize(ObjectOutput out) throws IOException{
    }
    
    public GotoSkill(ObjectInput in,  Unit owner){
        this.owner = owner;
    }
}
