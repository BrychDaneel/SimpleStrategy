package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.*;
import com.EyStudio.NanitWars.engine.units.Worker;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by daneel on 17.04.17.
 */
public class MakeWorkerSkill implements ISkill {

    Unit owner;
    IUnitManager unitManager;
    IPlayer player;
    ICollisionManager collisionManager;

    @Override
    public boolean canCast(Object target) {
        return target == null;
    }

    @Override
    public void cast(Object target) {
        if (target != null){
            System.out.println(target + "is bad target for skill " + getName());
            return;
        }

        Unit worker = new Worker(unitManager, player);
        
        Vector2D pos = collisionManager.findFreeSpace(owner.getPosition(), worker.getRadius(), owner.getLevel());
        if (pos == null){
            System.out.println("Theare is not free space to cast " + getName());
            return;            
        }
        worker.spawn(owner.getPosition(), pos);
    }

    @Override
    public String getName() {
        return "MakeWorker";
    }

    @Override
    public String getDescription() {
        return "Создает рабочего";
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

    public MakeWorkerSkill(Unit owner){
        this.owner = owner;
        unitManager = owner.getManager();
        player = owner.getOwner();
        collisionManager = unitManager.getEngine().getCollisionManager();
    }
    
    @Override
    public void serialize(ObjectOutput out) throws IOException{
    }
    
    public MakeWorkerSkill(ObjectInput in,  Unit owner){
        this.owner = owner;
        unitManager = owner.getManager();
        player = owner.getOwner();
        collisionManager = unitManager.getEngine().getCollisionManager();
    }
}
