/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.IEngine;
import com.EyStudio.NanitWars.engine.IObserver;
import com.EyStudio.NanitWars.engine.NormalVector2D;
import com.EyStudio.NanitWars.engine.Vector2D;
import com.EyStudio.NanitWars.engine.events.AttackEvent;
import com.EyStudio.NanitWars.engine.events.GameEvent;
import com.EyStudio.NanitWars.engine.events.UnitDieEvent;
import com.EyStudio.NanitWars.engine.events.UnitEvent;
import com.EyStudio.NanitWars.engine.events.UnitMoveEvent;
import com.EyStudio.NanitWars.engine.events.UnitRotateEvent;
import com.EyStudio.NanitWars.engine.events.UnitTransformEvent;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author daneel
 */
public class EngineUnitControl extends AbstractControl implements IObserver, AnimEventListener{
    //Any local variables should be encapsulated by getters/setters so they
    //appear in the SDK properties window and can be edited.
    //Right-click a local variable to encapsulate it with getters and setters.

    int unitID;
    float tickLength;
    float tickProgress = 0f;
    
    Vector3f oldPosition;
    Vector3f newPosition;
    Quaternion oldRotation;
    Quaternion newRotation;
    AnimChannel animChannelTop; 
    AnimChannel animChannelBase;  
    
    @Override
    protected void controlUpdate(float tpf) {
        //TODO: add code that controls Spatial,
        //e.g. spatial.rotate(tpf,tpf,tpf);
        
        if (tickProgress <= tickLength)
            tickProgress += tpf;

        translateProgress(tickProgress / tickLength);
        rotateProgress(tickProgress / tickLength);

    }
    
    private void translateProgress(float progress){
        if (newPosition == null)
            return;
        
        if (progress >= 1f || spatial.getLocalTranslation().distance(newPosition) < 0.1){
            spatial.setLocalTranslation(newPosition);

            if (animChannelBase != null && !"IdleBase".equals(animChannelBase.getAnimationName()))
                animChannelBase.setAnim("IdleBase", 0.1f);
            if (animChannelTop != null && !"IdleTop".equals(animChannelBase.getAnimationName())
                                        &&  !"SliceVertical".equals(animChannelTop.getAnimationName()))
                animChannelTop.setAnim("IdleTop", 0.1f);
            
            newPosition = null;
        }
        else{
            Vector3f interpolated = new Vector3f(oldPosition);
            interpolated.interpolateLocal(newPosition, progress);
            spatial.setLocalTranslation(interpolated);
            
            if (animChannelBase != null && !"RunBase".equals(animChannelBase.getAnimationName()))
                animChannelBase.setAnim("RunBase", 0.3f);
            if (animChannelTop != null && !"RunTop".equals(animChannelTop.getAnimationName())
                                        &&  !"SliceVertical".equals(animChannelTop.getAnimationName()))
                animChannelTop.setAnim("RunTop", 0.3f);
        }
    }

    private void rotateProgress(float progress){
        if (newRotation == null)
            return;
        
        if (progress >= 1f){
            spatial.setLocalRotation(newRotation);
            newRotation = null;
        }
        else{
            Quaternion interpolated = new Quaternion(oldRotation);
            interpolated.nlerp(newRotation, progress);
            spatial.setLocalRotation(interpolated);
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        EngineUnitControl control = new EngineUnitControl();
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
    
    public void bind(IEngine engine, int unitID){
        engine.getEventProxy().registerObserver(this, UnitEvent.class);
        this.unitID = unitID;
    }

    private void onUnitMove(UnitMoveEvent event){       
        tickProgress = 0;
        Vector2D pos = event.getNewPosition();
        oldPosition = spatial.getLocalTranslation();
        newPosition = new Vector3f(pos.getX(), 0, pos.getY());
    }
    
    private void onUnitRotate(UnitRotateEvent event){
        //tickProgress = 0;
        double x = event.getNewRotation().getX();
        double y = event.getNewRotation().getY();
        float a = (float)Math.atan2(-y, x);
        oldRotation = spatial.getLocalRotation();
        newRotation = new Quaternion().fromAngleAxis(a, Vector3f.UNIT_Y);
    }
           
    private void onUnitAttack(AttackEvent event){
        animChannelTop.setAnim("SliceVertical");
        animChannelTop.setLoopMode(LoopMode.DontLoop);
    }
         
    public void onUnitDie(UnitDieEvent event){
        ((Node)spatial).removeFromParent();
        
    }
    
    @Override
    public void onRaiseEvent(GameEvent event) {
        if (((UnitEvent)event).getSource().getUnitID() != unitID)
            return;
        
        if (event instanceof UnitMoveEvent)
            onUnitMove((UnitMoveEvent)event);
        
        if (event instanceof UnitRotateEvent)
            onUnitRotate((UnitRotateEvent)event);
        
        if (event instanceof AttackEvent)
            onUnitAttack((AttackEvent)event);
        
        if (event instanceof UnitDieEvent)
            onUnitDie((UnitDieEvent)event);
    }

    public float getTickLength() {
        return tickLength;
    }

    public void setTickLength(float tickLength) {
        this.tickLength = tickLength;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial); 
        AnimControl animControl = ((Node)spatial).getChild("Model").getControl(AnimControl.class);
        if (animControl != null){
            animControl.addListener(this);
            animChannelBase = animControl.createChannel();
            animChannelTop = animControl.createChannel();
            animChannelBase.setAnim("IdleBase");
            animChannelTop.setAnim("IdleTop");
            animChannelBase.setLoopMode(LoopMode.Loop);
            animChannelTop.setLoopMode(LoopMode.Loop);
            
        }
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if ("SliceVertical".equals(animName)){
            channel.setAnim("IdleTop", 0.1f);
            animChannelTop.setLoopMode(LoopMode.Loop);
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
    
    
    
}
