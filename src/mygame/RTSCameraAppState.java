/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import javax.swing.text.html.parser.DTDConstants;

/**
 *
 * @author daneel
 */
public class RTSCameraAppState extends AbstractAppState implements AnalogListener, ActionListener{
    
    private Camera cam;
    private InputManager inputManager;
    private Vector3f camLocation = new Vector3f(250, 50f, 250);
    private Vector3f lookAtDirection = new Vector3f(0, -0.6f, -0.4f);
    public float moveSpeed = 100;
    
    boolean moveLeft, moveRight, moveUp, moveDown;
    
    
    public enum InputMapping{
        MoveLeft, MoveRight, MoveUp, MoveDown;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        this.cam = app.getCamera();
        cam.lookAtDirection(lookAtDirection, Vector3f.UNIT_Y);
        cam.setLocation(camLocation);
        this.inputManager = app.getInputManager();
        addInputMappings();
    } 
    
    private void addInputMappings(){
        inputManager.addMapping(InputMapping.MoveLeft.name(),
                                new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(InputMapping.MoveRight.name(), 
                                new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(InputMapping.MoveUp.name(), 
                                new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(InputMapping.MoveDown.name(), 
                                new KeyTrigger(KeyInput.KEY_DOWN));
               
        for (InputMapping i : InputMapping.values())
            inputManager.addListener(this, i.name());
    }
    

    @Override
    public void cleanup(){
        for (InputMapping i : InputMapping.values())
            inputManager.deleteMapping(i.name());
    }
    
    @Override
    public void onAction(String Name, boolean isPressed, float tfp){
        InputMapping input = InputMapping.valueOf(Name);
        switch (input){
            case MoveLeft:
                moveLeft = isPressed;
                break;
            case MoveRight:
                moveRight = isPressed;
                break;
            case MoveUp:
                moveUp = isPressed;
                break;
            case MoveDown:
                moveDown = isPressed;
                break;
        }
    }
    
    @Override
    public void update(float tfp){
        super.update(tfp);
        camLocation = cam.getLocation();
        Vector3f tempVector = new Vector3f();
        if (moveLeft)
            tempVector.addLocal(-1f, 0f, 0f);
        if (moveRight)
            tempVector.addLocal(1f, 0f, 0f);
        if (moveUp)
            tempVector.addLocal(0f, 0f, -1f);
        if (moveDown)
            tempVector.addLocal(0f, 0f, 1f);
        
        
        Vector2f mousePos2D = inputManager.getCursorPosition();
        float x = mousePos2D.getX();
        float y = mousePos2D.getY();
        float w = cam.getWidth();
        float h = cam.getHeight();     
        if  (x < w * 0.1)
            tempVector.addLocal(-1f, 0f, 0f);
        if (x > w * 0.9)
            tempVector.addLocal(1f, 0f, 0f);
        if (y < h * 0.1)
            tempVector.addLocal(0f, 0f, 1f);
        if (y > h * 0.9)
            tempVector.addLocal(0f, 0f, -1f);
        
        tempVector.normalize().mult(moveSpeed * tfp);
        camLocation.addLocal(tempVector);
        cam.setLocation(camLocation);
    }
    
    @Override
    public void onAnalog(String name, float value, float tpf) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
