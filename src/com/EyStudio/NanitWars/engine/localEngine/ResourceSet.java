package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.IResource;
import com.EyStudio.NanitWars.engine.IResourceSet;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by daneel on 16.04.17.
 */
public class ResourceSet implements IResourceSet {
    HashMap<String, IResource> resources = new HashMap<>();

    @Override
    public IResource getResource(String name) {
        return resources.get(name);
    }

    public ResourceSet(IResource[] resources){
        for (IResource res : resources)
            this.resources.put(res.getName(), res);
    }
}
