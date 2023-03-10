package com.lukullu.undersquare.menu;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.player.Player;
import com.lukullu.undersquare.widgets.TextWidget;
import com.lukullu.undersquare.widgets.Widget;
import com.lukullu.undersquare.widgets.button.ButtonWidget;

import java.awt.*;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenX;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class DeathMenu extends ProgramState implements ProcessingClass {

    GameHandler previousState;

    public Widget retryButton;
    public Widget exitButton;
    public Widget deathMessage;
    public Widget pointReport;

    public DeathMenu(GameHandler _previousState){
        previousState = _previousState;
        previousState.didIDie = false;
    }

    @Override
    public void init(){
        UnderSquare.INSTANCE.cursor(ARROW);
        translate(previousState.cam.rel.x,previousState.cam.rel.y);
        initWidgets();
    }

    public void initWidgets(){

        int deathMessageIndex = (int)random(RANDOM_DEATH_MESSAGES.length);
        textSize(24);
        deathMessage = new TextWidget(
                new Vector2(
                        getWidth()/2 - (scaleToScreenX(100)), //+ textWidth(RANDOM_DEATH_MESSAGES[deathMessageIndex]))/2f,
                        getHeight()/2 - scaleToScreenY(140)
                ),
                new Vector2(
                        scaleToScreenX(200),// + textWidth(RANDOM_DEATH_MESSAGES[deathMessageIndex]),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                RANDOM_DEATH_MESSAGES[deathMessageIndex],
                DEFAULT_TEXT_SIZE,
                CENTER
        );

        // find player entity
        int points = 0;
        for (Entity entity : previousState.entities) {
                if (entity instanceof Player) points = entity.points; 
        }

        pointReport = new TextWidget(
                new Vector2(
                        getWidth()/2 - (scaleToScreenX(100)), //textWidth("You earned " + points +" Points"))/2f,
                        getHeight()/2 - scaleToScreenY(80)
                ),
                new Vector2(
                        scaleToScreenX(200), //textWidth("You earned " + points +" Points"),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                points +" Points Earned",
                DEFAULT_TEXT_SIZE,
                CENTER
        );

        retryButton = new ButtonWidget(
                new Vector2(
                        getWidth()/2 - scaleToScreenX(100),
                        getHeight()/2 - scaleToScreenY(20)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Retry Game",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.changeState(previousState);}
        );

        exitButton = new ButtonWidget(
                new Vector2(
                        getWidth()/2 - scaleToScreenX(100),
                        getHeight()/2 + scaleToScreenY(40)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Return to Menu",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.changeState(new MainMenu());}
        );
    }

    @Override
    public void update(){

        retryButton.update();
        exitButton.update();
        deathMessage.update();
        pointReport.update();
    }

    @Override
    public void paint(){

        //background
        fill(Color.BLACK.getRGB(),10);
        rect(0,0,getWidth(),getHeight());

        retryButton.paint();
        exitButton.paint();
        deathMessage.paint();
        pointReport.paint();
    }

}

