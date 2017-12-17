/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.UnitGroup;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author daneel
 */
public class SelectControl extends AbstractControl {
    //Any local variables should be encapsulated by getters/setters so they
    //appear in the SDK properties window and can be edited.
    //Right-click a local variable to encapsulate it with getters and setters.

    private boolean selected;
    Spatial marker;
    Spatial localMarker;
    int radius = 1;
    
    @Override
    protected void controlUpdate(float tpf) {
        //TODO: add code that controls Spatial,
        //e.g. spatial.rotate(tpf,tpf,tpf);
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        SelectControl control = new SelectControl();
        //TODO: copy parameters to new Control
        return control;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        //TODO: load properties of this Control, e.g.
        //this.value = in.readFloat("name", defaultValue);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        //TODO: save properties of this Control, e.g.
        //out.write(this.value, "name", defaultValue);
    }

    public boolean isSelected() {
        return selected;
    }

    public Spatial getMarker() {
        return marker;
    }

    public void setMarker(Spatial marker) {
        boolean wasSelected = this.selected;
        setSelected(false);
        
        this.marker = marker;
        localMarker = marker.clone();
        localMarker.getLocalScale().multLocal(radius);
        localMarker.setLocalScale(radius);
        
        setSelected(wasSelected);
    }

    
    public void setSelected(boolean selected) {
        if (this.selected == selected)
            return;
        this.selected = selected;
        
        if  (selected){
            if (localMarker != null)
                ((Node)spatial).attachChild(localMarker);
        }
        else
            if (localMarker != null)
                localMarker.removeFromParent();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        localMarker.getLocalScale().multLocal(radius/this.radius);
        this.radius = radius;
    }
    
    
}
