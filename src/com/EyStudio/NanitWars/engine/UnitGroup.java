package com.EyStudio.NanitWars.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by daneel on 15.04.17.
 */

public class UnitGroup implements Iterable<Unit>{
    ArrayList<Unit> units = new ArrayList<>();

    public void add(Unit unit){
        units.add(unit);
    }

    public void cast(String skillName, Object target){
        for (Unit unit : units){
            ISkill skill = unit.getSkills().getSkill(skillName);
            if (skill != null && skill.canCast(target))
                skill.cast(target);
        }
    }

    boolean canCast(String skillName, Object target){
        for (Unit unit : units){
            ISkill skill = unit.getSkills().getSkill(skillName);
            if (skill != null && skill.canCast(target))
                return true;
        }
        return false;
    }

    private void sortUnit(){
        units.sort(new Comparator<Unit>() {
            @Override
            public int compare(Unit unit, Unit other) {
                return unit.getGroupPriority() - other.getGroupPriority();
            }
        });

    }

    private ArrayList<ArrayList<Unit>> getUnitGroups(){

        sortUnit();

        ArrayList<ArrayList<Unit>> result = new ArrayList<>();
        int i = 0;
        while (i < units.size()){
            ArrayList<Unit> list = new ArrayList<>();
            result.add(list);

            list.add(units.get(i++));
            while (i + 1 < units.size() &&
                    units.get(i).getName().equals(units.get(i + 1).getName()))
                list.add(units.get(++i));
            i++;
        }

        return result;
    }

    public SkillList getSkillList(int page){
        return getUnitGroups().get(page).get(0).getSkills();
    }

    public Unit getUnit(int number){
        sortUnit();
        return units.get(number);
    }


    public void clear(){
        units.clear();
    }

    @Override
    public Iterator<Unit> iterator() {
        return units.iterator();
    }
    
    
}
