package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


/**
 * Created by daneel on 17.04.17.
 */
public class AttackSkill implements ISkill {

    Unit owner;

    @Override
    public boolean canCast(Object target) {
        if (!(target instanceof Unit))
            return false;
        return true;
    }

    @Override
    public void cast(Object target) {
        if (!canCast(target))
            throw new IllegalArgumentException(target + "is bad target for skill " + getName());
        
        Unit unit = (Unit)target;
        
        Damage damage = new Damage(DamageType.Default, 20);
        owner.getTaskQueue().addLast(new AttackTask(owner, unit, damage, getRange()));
    }

    @Override
    public String getName() {
        return "Attack";
    }

    @Override
    public String getDescription() {
        return "Выдает люлей";
    }

    @Override
    public int getRange() {
        return 5;
    }

    @Override
    public int getCoolDown() {
        return 0;
    }

    @Override
    public SkillTargetType getTargetType() {
        return SkillTargetType.Unit;
    }

    public AttackSkill(Unit owner){
        this.owner = owner;
    }

    @Override
    public void serialize(ObjectOutput out) throws IOException{
    }
    
    public AttackSkill(ObjectInput in,  Unit owner){
        this.owner = owner;
    }
    
}
