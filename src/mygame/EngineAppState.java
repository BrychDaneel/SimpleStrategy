/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.ILevel;
import com.EyStudio.NanitWars.engine.IObserver;
import com.EyStudio.NanitWars.engine.IPlayer;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.Vector2D;
import com.EyStudio.NanitWars.engine.events.GameEvent;
import com.EyStudio.NanitWars.engine.events.TickEndEvent;
import com.EyStudio.NanitWars.engine.events.TickEvent;
import com.EyStudio.NanitWars.engine.events.TickStartEvent;
import com.EyStudio.NanitWars.engine.events.UnitDieEvent;
import com.EyStudio.NanitWars.engine.events.UnitSpawnEvent;
import com.EyStudio.NanitWars.engine.localEngine.ArrayLevel;
import com.EyStudio.NanitWars.engine.localEngine.Engine;
import com.EyStudio.NanitWars.engine.localEngine.ImageLevel;
import com.EyStudio.NanitWars.engine.localEngine.Map;
import com.EyStudio.NanitWars.engine.localEngine.Player;
import com.EyStudio.NanitWars.engine.localEngine.PlayerManager;
import com.EyStudio.NanitWars.engine.localEngine.Resource;
import com.EyStudio.NanitWars.engine.localEngine.ResourceSet;
import com.EyStudio.NanitWars.engine.units.Barack;
import com.EyStudio.NanitWars.engine.units.Man;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daneel
 */
public class EngineAppState extends AbstractAppState implements IObserver{

    private Engine engine;
    private Node rootNode;
    private AssetManager assetManager;
    private SelectAppState selectAppState;
    TerrainQuad heightMap;
    float secFerTick;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        assetManager = app.getAssetManager();
        
        Image img =  app.getAssetManager().loadTexture("Textures/mapmodel.png").getImage();

        ILevel level = new JmeImageLevel(img, "ground", 0);
        Map map = new Map("map1", 512, 512);
        map.addLevel(level);

        PlayerManager pm = new PlayerManager();
        engine = new Engine(map, pm);
        //engine = load(app);
        IPlayer pl =  new Player(pm, 0, "Якуш", new ResourceSet(new Resource[]{new Resource("Gold")}));
                
        Unit man1 = new Man(engine.getUnitManager(), pl);
        Unit man2 = new Man(engine.getUnitManager(), pl);
        Unit barack = new Barack(engine.getUnitManager(), pl);
        
        engine.getEventProxy().registerObserver(this, TickEndEvent.class);
        engine.getEventProxy().registerObserver(this, UnitSpawnEvent.class);
        
        TickGeneratorAppState tickGeneratorAppState = new TickGeneratorAppState(engine, 100);
        engine.setTickGenerator(tickGeneratorAppState);
        
        app.getStateManager().attach(tickGeneratorAppState);
       
        engine.getEventProxy().registerObserver(selectAppState, UnitDieEvent.class);
        
        engine.start();
        
        man1.spawn(new Vector2D(120,170), new Vector2D(120,170));
        man2.spawn(new Vector2D(140,170), new Vector2D(140,170));
        barack.spawn(new Vector2D(100,170), new Vector2D(100,170));
        for (Unit unit : engine.getUnitManager()){
          //  if (unit.isSpawned())
                unit.spawn(unit.getPosition(), unit.getPosition());
        }
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
        secFerTick += tpf; 
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
        engine.stop();
    }

    private void onUnitSpawn(UnitSpawnEvent event){
        if (rootNode == null)
            return;
        
        Unit unit = event.getSource();
        
        String unitName = unit.getName();
        Spatial unitModel = assetManager.loadModel("Models/Units/" + unitName + ".j3o");
        rootNode.attachChild(unitModel);
        
        SelectControl selectControl = new SelectControl();
        selectControl.setMarker(assetManager.loadModel("Models/marker.j3o"));
        selectControl.setRadius(unit.getRadius());
        unitModel.addControl(selectControl);
        
        EngineUnitControl engineUnitControl = new EngineUnitControl();
        engineUnitControl.setTickLength(((float)engine.getTickGenerator().getFrequency()*3)/1000);
        unitModel.addControl(engineUnitControl);
        engineUnitControl.bind(engine, unit.getUnitID());
        
        GroundControl groundControl = new GroundControl();
        groundControl.heightMap = heightMap;
        unitModel.addControl(groundControl);
        
        unitModel.setLocalTranslation(Vector3f.NAN);
        Vector2D pos = event.getDestination();
        unitModel.setLocalTranslation(pos.getX(), 0, pos.getY());
        
        selectAppState.addSelecteble(unitModel, unit);
    }
    
    private void onTickEnd(TickEndEvent event){
        int tick = event.getTick();
        if (tick % 10 == 0){
            System.out.println("[ENGINE] Processed tick: " + tick +"("+Math.round(secFerTick/10*1000)+" msecPerTick)");   
            secFerTick = 0;
        }
    }
    
    @Override
    public void onRaiseEvent(GameEvent event) {
        if (event instanceof UnitSpawnEvent)
            onUnitSpawn((UnitSpawnEvent)event);
        if (event instanceof TickEndEvent)
            onTickEnd((TickEndEvent) event);
    }

    public Engine getEngine() {
        return engine;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    public TerrainQuad getHeightMap() {
        return heightMap;
    }

    public void setHeightMap(TerrainQuad heightMap) {
        this.heightMap = heightMap;
    }

    public SelectAppState getSelectAppState() {
        return selectAppState;
    }

    public void setSelectAppState(SelectAppState selectAppState) {
        this.selectAppState = selectAppState;
    }
    
    public void save(){
        
            
        try (FileOutputStream file = new FileOutputStream("save.sav")){
            try (ObjectOutputStream out = new ObjectOutputStream(file)){
                engine.serialize(out);
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
            //Logger.getLogger(EngineAppState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Engine load(Application app){
        
        Engine newEngine = null;
        try (InputStream file = new FileInputStream("save.sav")){
            try (ObjectInputStream in = new ObjectInputStream(file)){
                PlayerManager pm = new PlayerManager();
                IPlayer pl =  new Player(pm, 0, "Якуш", new ResourceSet(new Resource[]{new Resource("Gold")}));
                pm.register(pl);
                newEngine = new Engine(in, pm);
                TickGeneratorAppState tickGeneratorAppState = new TickGeneratorAppState(newEngine, 100);
                newEngine.setTickGenerator(tickGeneratorAppState);
                app.getStateManager().attach(tickGeneratorAppState);
            }
        } catch (Exception ex) {
            Logger.getLogger(EngineAppState.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newEngine;
    }
    
}
