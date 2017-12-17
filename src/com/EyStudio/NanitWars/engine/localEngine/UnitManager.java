package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.IEngine;
import com.EyStudio.NanitWars.engine.IUnitManager;
import com.EyStudio.NanitWars.engine.IUnitTask;
import com.EyStudio.NanitWars.engine.Unit;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by daneel on 16.04.17.
 */
public class UnitManager implements IUnitManager, Iterable<Unit>{

    IEngine engine;
    java.util.Map<Integer, Integer> nextID = new HashMap<>();
    java.util.Map<Integer, Unit> unitMap = new HashMap<>();

    @Override
    public int register(Unit unit) {
        int plID = unit.getOwner().getPlayerID();
        int id = nextID.getOrDefault(plID, 1);
        nextID.put(plID,id + 1);
        id = plID * 10000 + id;
        unitMap.put(id, unit);
        return id;
    }

    @Override
    public void unregister(Unit unit) {
        unitMap.remove(unit.getUnitID());
    }

    @Override
    public Unit getUnit(int unitID) {
        return unitMap.get(unitID);
    }

    @Override
    public IEngine getEngine() {
        return engine;
    }

    public UnitManager(IEngine engine){
        this.engine = engine;
    }
    
    @Override
    public Iterator<Unit> iterator(){
        return unitMap.values().iterator();
    }
    
    public void serialize(ObjectOutput out) throws IOException{
        out.writeInt(unitMap.size());
        for (Unit unit : unitMap.values()){
            out.writeObject(unit.getClass());
            unit.serialize(out);
        }
    }
    
    public UnitManager(ObjectInput in,  IEngine engine)  throws IOException, ClassNotFoundException { 
        this.engine = engine;
        
        int mapSize = in.readInt();
        unitMap = new HashMap<>();
        for (int i = 0; i < mapSize; i++){
            try{
                Class type = (Class) in.readObject();
                Constructor<?> constructor = type.getConstructor(ObjectInput.class, IUnitManager.class); 
                Unit unit = (Unit)constructor.newInstance(in, this);
                unitMap.put(unit.getUnitID(), unit);
            }
            catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | InstantiationException e){
                throw new IOException("Error while deserializing units manager.", e);
            }
        }
    }
}
