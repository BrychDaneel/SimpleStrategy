package com.EyStudio.NanitWars.engine;

import com.EyStudio.NanitWars.engine.events.*;
import com.EyStudio.NanitWars.engine.localEngine.UnitManager;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import javafx.scene.transform.Rotate;

/**
 * Created by daneel on 15.04.17.
 */
public abstract class Unit implements IObserver{
    public static final int DEFAULT_LEVEL = 0;
    
    protected boolean spawned;
    protected Vector2D position;
    protected double speed;
    protected int healthLimit = 100;
    protected int health = healthLimit;
    protected int unitID;
    protected ILevel level;
    protected int ownerID;
    protected Set<String> categories;
    protected NormalVector2D rotation = new NormalVector2D(1d, 0d);
    protected IUnitManager manager;
    protected IEventProxy eventProxy;
    protected SkillList skills = new SkillList(this);
    protected Deque<IUnitTask> taskQueue = new ArrayDeque<>();
    protected Set<IUnitTask> taskSet = new HashSet<>();

    public abstract boolean isStatic();

    public abstract String getName();

    public abstract int getGroupPriority();

    public abstract int getRadius();

    public NormalVector2D getRotation() {
        return rotation;
    }

    public void rotate(NormalVector2D rotation) {
        NormalVector2D oldRotation = this.rotation;
        this.rotation = rotation;
        eventProxy.raiseEvent(new UnitRotateEvent(this, oldRotation, rotation));
    }

    public Vector2D getPosition(){
        return position;
    }

    public void move(Vector2D position){
        Vector2D oldPosition = position;
        this.position = position;
        eventProxy.raiseEvent(new UnitMoveEvent(this, oldPosition, position));
    }

    public double getSpeed(){
        return speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public ILevel getLevel() {
        return level;
    }

    public void setLevel(ILevel level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (health <= 0)
            kill();
        if (this.health > healthLimit)
            this.health = healthLimit;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public IUnitManager getManager() {
        return manager;
    }

    public SkillList getSkills() {
        return skills;
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public int getUnitID() {
        return unitID;
    }

    public IPlayer getOwner() {
        return manager.getEngine().getPlayerManager().getPlayer(ownerID);
    }

    public Deque<IUnitTask> getTaskQueue() {
        return taskQueue;
    }

    public Set<IUnitTask> getTaskSet() {
        return taskSet;
    }
    
    public boolean isSpawned(){
        return spawned;
    }

    public void takeDamage(Unit source, Damage damage){
        if (damage.type == DamageType.Default) {
            int newHealth = getHealth() - damage.size;
            setHealth(newHealth);
        }
    }

    public void changeLevel(ILevel newLevel){
        ILevel oldLevel = level;
        level = newLevel;
        eventProxy.raiseEvent(new UnitChangeLevelEvent(this, oldLevel, newLevel));
    }

    public void spawn(Vector2D sourcePoint, Vector2D destinationPoint){
        position = destinationPoint;
        spawned = true;
        eventProxy.raiseEvent(new UnitSpawnEvent(this, sourcePoint, destinationPoint));
    }

    public void despawn(Vector2D destinationPoint){
        spawned = false;
    }

    public void kill(){
        eventProxy.raiseEvent(new UnitDieEvent(this));
        manager.unregister(this);
    }

    protected void onTick(TickStartEvent event){
        boolean completed;

        while (!taskQueue.isEmpty()){
            IUnitTask task = taskQueue.getFirst();
            completed =  task.perform(this);
            if (completed)
                taskQueue.removeFirst();
            else
                break;
        }

        Iterator<IUnitTask> taskIterator = taskSet.iterator();
        while (taskIterator.hasNext()){
            IUnitTask task = taskIterator.next();
            completed  = task.perform(this);
            if (completed)
                taskIterator.remove();
        }
    }

    @Override
    public void onRaiseEvent(GameEvent event) {
        if (event instanceof TickStartEvent)
            onTick((TickStartEvent) event);
    }

    public Unit(IUnitManager manager, IPlayer owner){
        this.manager = manager;
        this.ownerID = owner.getPlayerID();
        unitID = manager.register(this);
        eventProxy = manager.getEngine().getEventProxy();
        eventProxy.registerObserver(this, TickStartEvent.class);
        level = manager.getEngine().getMap().getLevel(DEFAULT_LEVEL);
    }
   
    public void serialize(ObjectOutput out) throws IOException {
        out.writeInt(unitID);
        out.writeInt(ownerID);
        out.writeInt(level.getLevelID());
        out.writeInt(position.getX());
        out.writeInt(position.getY());
        out.writeDouble(rotation.getX());
        out.writeDouble(rotation.getY());
        out.writeDouble(speed);
        out.writeInt(healthLimit);
        out.writeInt(health);
        out.writeObject(categories);
        skills.serialize(out);
        out.writeInt(taskQueue.size());
        out.writeInt(taskSet.size());
        
        for (IUnitTask task : taskQueue){
            out.writeObject(task.getClass());
            task.serialize(out);
        }
        
        for (IUnitTask task : taskSet){
            out.writeObject(task.getClass());
            task.serialize(out);
        }
    }

    public Unit(ObjectInput in, IUnitManager unitManager) throws IOException, ClassNotFoundException {
        this.manager = unitManager;
        IEngine engine = unitManager.getEngine();
        
        unitID = in.readInt();
        
        this.ownerID = in.readInt();
        
        int levelID = in.readInt();
        level = engine.getMap().getLevel(levelID);
        
        int px = in.readInt();
        int py = in.readInt();
        position = new Vector2D(px, py);
        
        double rx = in.readDouble();
        double ry = in.readDouble();
        rotation = new NormalVector2D(rx, ry);
        
        speed = in.readDouble();
        
        healthLimit = in.readInt();
        
        health = in.readInt();
        
        categories = (HashSet<String>) in.readObject();
        
        skills = new SkillList(in, this);

        int taskQueueSize = in.readInt();
        int taskSetSize = in.readInt(); 
        
        try{
            //taskQueue = new ArrayDeque<>();
            for (int i=0; i<taskQueueSize; i++){
                Class type = (Class) in.readObject();
                Constructor<?> constructor = type.getConstructor(ObjectInput.class, Unit.class); 
                taskQueue.addLast((IUnitTask)constructor.newInstance(in, this));
            }

            //taskSet = new HashSet<>();
            for (int i=0; i<taskSetSize; i++){
                Class type = (Class) in.readObject();
                Constructor<?> constructor = type.getConstructor(ObjectInput.class, Unit.class); 
                taskQueue.addLast((IUnitTask)constructor.newInstance(in, this));
            }
        }
        catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | InstantiationException e){
            throw new IOException("Error while deserializing unit.", e);
        }
   
        eventProxy = engine.getEventProxy();
        eventProxy.registerObserver(this, TickStartEvent.class);
        
    }
    
    
}
