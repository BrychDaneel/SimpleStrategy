/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.ISkill;
import com.EyStudio.NanitWars.engine.SkillTargetType;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.UnitGroup;
import com.EyStudio.NanitWars.engine.Vector2D;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 *
 * @author daneel
 */
public class CastAppState extends AbstractAppState implements ActionListener{
    
    InputManager inputManager;
    Application app;
    Spatial map;
    ISkill skill;
    UnitGroup unitGroup; 
    
    public enum InputMapping{
        CastSkill;
    }
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
        inputManager = app.getInputManager();
        this.app = app;
        addInputMappings();
    }
    
        private void addInputMappings(){
        inputManager.addMapping(InputMapping.CastSkill.name(), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        for (InputMapping i : InputMapping.values())
            inputManager.addListener(this, i.name());
    }
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        for (InputMapping i : InputMapping.values())
            inputManager.deleteMapping(i.name());
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!name.equals(InputMapping.CastSkill.name()))
            return;
        if (!isPressed)
            return;
        if (skill == null)
            return;
        if (unitGroup == null)
            return;
            
        Camera camera = app.getCamera();
        Vector2f mousePos2D = inputManager.getCursorPosition();
        Vector3f mousePos3D = camera.getWorldCoordinates(mousePos2D, 0f);
        //Vector3f clickDir = mousePos3D.add(camera.getWorldCoordinates(mousePos2D, 1f)).normalizeLocal();
        Vector3f clickDir = camera.getWorldCoordinates(mousePos2D, 1f).subtractLocal(mousePos3D).normalizeLocal();

        Ray ray = new Ray(mousePos3D, clickDir);
        
        if (skill.getTargetType() == SkillTargetType.Point){
            CollisionResults collisionResults = new CollisionResults();
            map.collideWith(ray, collisionResults);

            CollisionResult result = collisionResults.getClosestCollision();
            if (result != null){
                Vector3f contactPoint = result.getContactPoint();
                int x = Math.round(contactPoint.x);
                int y = Math.round(contactPoint.z);
                unitGroup.cast(skill.getName(), new Vector2D(x,y));
            }
        }
        
        if (skill.getTargetType() == SkillTargetType.Unit){
            SelectAppState selectAppState = app.getStateManager().getState(SelectAppState.class);
            Unit unit = selectAppState.getUnitUnderCursor();
            if (unit != null)
                unitGroup.cast(skill.getName(), unit);
        }
        
    }

    public void setSkill(ISkill skill) {
        this.skill = skill;
    }

    public void setUnitGroup(UnitGroup unitGroup) {
        this.unitGroup = unitGroup;
    }
}
