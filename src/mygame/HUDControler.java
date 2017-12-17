/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.ISkill;
import com.EyStudio.NanitWars.engine.SkillTargetType;
import com.EyStudio.NanitWars.engine.UnitGroup;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;

/**
 *
 * @author daneel
 */
public class HUDControler implements ScreenController{

    Nifty nifty;
    Screen screen;
    Element[][] buttons = new Element[4][4];
    ISkill[][] skills = new ISkill[4][4];
    CastAppState castAppState;
    EngineAppState engineAppState;
    UnitGroup unitGroup;
        
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {        
        Element skillPanel = screen.findElementById("SkillPanel");
        for (int i=0; i<4; i++){
            PanelBuilder panelBuilder = new PanelBuilder(""){{
                height("25%");
                childLayoutHorizontal();
            }};
            Element panel = panelBuilder.build(nifty, screen, skillPanel);
                    
            for (int ii=0; ii<4; ii++){
                String buttonName = "Button" + (i*4+ii);
                ButtonBuilder buttonBuilder = new ButtonBuilder(buttonName){{
                    width("25%");
                    height("100%");
                    label("Dno");
                    visible(false);
                }}; 
                buttons[i][ii] = buttonBuilder.build(nifty, screen, panel);
            }
        }
        
        Element statePanel = screen.findElementById("TopPanel");
        ButtonBuilder buttonBuilder = new ButtonBuilder("ButtonSave"){{
                    width("10%");
                    height("100%");
                    label("Save");
                }}; 
        buttonBuilder.build(nifty, screen, statePanel);
        buttonBuilder = new ButtonBuilder("ButtonLoad"){{
                    width("10%");
                    height("100%");
                    label("Load");
                }}; 
        buttonBuilder.build(nifty, screen, statePanel);
    }

    @Override
    public void onEndScreen() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void updateSelected(UnitGroup unitGroup){
        this.unitGroup = unitGroup;
        String name = unitGroup.getUnit(0).getName();
        NiftyImage image = nifty.getRenderEngine().createImage(screen, "Textures/Faces/"+name+".jpg", false);
        Element niftyElement = screen.findElementById("FaceImage");
        niftyElement.getRenderer(ImageRenderer.class).setImage(image);
        
        for (int i=0; i<4; i++)
            for (int ii=0; ii<4; ii++)
                buttons[i][ii].setVisible(false);
        
        ArrayList<ISkill> defaultSkills = unitGroup.getSkillList(0).getSkills("default");
        for (int index = 0; index < defaultSkills.size(); index++){
            int ix = index / 4;
            int iy = index % 4;
            skills[ix][iy] = defaultSkills.get(index);
            buttons[ix][iy].getNiftyControl(Button.class).setText(skills[ix][iy].getName());
            buttons[ix][iy].setVisible(true);
        }
    }
    
    @NiftyEventSubscriber(pattern="Button.*")
    public void onButtonDown(final String topic, final ButtonClickedEvent event){
        if ("ButtonSave".equals(topic)){
            engineAppState.save();
            return;
        }
        if ("ButtonLoad".equals(topic)){
            //engineAppState.load();
            return;
        }
        
        int number = Integer.parseInt(topic.substring("Button".length()));
        int skillX = number / 4;
        int skillY = number % 4;
        ISkill skill = skills[skillX][skillY];
        if (castAppState != null && (skill.getTargetType() == SkillTargetType.Point || skill.getTargetType() == SkillTargetType.Unit)){
            castAppState.setSkill(skill);
            castAppState.setUnitGroup(unitGroup);
        }
        if (skill.getTargetType() == SkillTargetType.None)
            unitGroup.cast(skill.getName(), null);
    }

}
