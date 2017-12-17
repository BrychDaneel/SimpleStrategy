package com.EyStudio.NanitWars.engine;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by daneel on 15.04.17.
 */
public interface ISkill {
    boolean canCast(Object target);
    void cast(Object target);
    String getName();
    String getDescription();
    int getRange();
    int getCoolDown();
    SkillTargetType getTargetType();
    void serialize(ObjectOutput out) throws IOException;
}
