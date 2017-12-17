package com.EyStudio.NanitWars.engine;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by daneel on 15.04.17.
 */
public class SkillList {

    Map<String, ArrayList<ISkill>> groups = new HashMap<>();
    private Unit owner;

    public void addSkill(ISkill skill, String group){
        if (!groups.containsKey(group))
            groups.put(group, new ArrayList<ISkill>());
        ArrayList<ISkill> skillList = groups.get(group);
        skillList.add(skill);
    }

    public ArrayList<ISkill> getSkills(String group){
        return groups.get(group);
    }

    public ISkill getSkill(String name){
        for (ArrayList<ISkill> group : groups.values())
            for (ISkill skill : group)
                if (skill.getName().equals(name))
                    return skill;
        return null;
    }

    public boolean hasSkill(String name) {
        return getSkill(name) != null;
    }

    public SkillList(Unit owner){
        this.owner = owner;
    }
    
        
    public void serialize(ObjectOutput out) throws IOException {
        out.writeInt(groups.size());
        for (Entry<String,ArrayList<ISkill>> entry : groups.entrySet()){
            out.writeObject(entry.getKey());
            out.writeInt(entry.getValue().size());
            for (ISkill skill : entry.getValue()){
                out.writeObject(skill.getClass());
                skill.serialize(out);
                out.writeObject("DNNOOOO1234");
            }
        }
        out.writeObject("DNNOOOOEND");
    }     

    public SkillList(ObjectInput in, Unit owner) throws IOException, ClassNotFoundException{
        
        this.owner = owner;
        
        int size = in.readInt();
        
        for (int i = 0; i<size; i++){
            String groupName = (String)in.readObject();
            ArrayList<ISkill> array = new ArrayList<>();
            groups.put(groupName, array);
            int groupSize = in.readInt();
            try{
                for (int ii=0; ii<groupSize; ii++){
                    Class type = (Class) in.readObject();
                    Constructor<?> constructor = type.getConstructor(ObjectInput.class, Unit.class); 
                    array.add((ISkill)constructor.newInstance(in, owner));
                    
                    String s;
                    s = (String)in.readObject();
                    s = s; 
                }
            }
            catch(NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | InstantiationException e){
                throw new IOException("Error while deserializing skills list.", e);
            }
        }
        String s;
        s = (String)in.readObject();
        s = s; 
    }

}
