package com.EyStudio.NanitWars.engine;

import java.io.IOException;
import java.io.ObjectOutput;

/**
 * Created by daneel on 15.04.17.
 */
public interface IUnitTask {
    boolean perform(Unit source);
    void serialize(ObjectOutput out) throws IOException;
}
