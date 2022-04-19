package com.lukullu.undersquare.menu;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.KeyHandler;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.widgets.TextWidget;
import com.lukullu.undersquare.widgets.Widget;
import com.lukullu.undersquare.widgets.button.ButtonWidget;

import java.awt.*;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenX;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class PauseMenu extends ProgramState implements ProcessingClass{

    public ProgramState pausedState;

    public Widget resumeButton;
    public Widget exitButton;
    public Widget titleText;
    public Widget retryButton;

    public boolean escapeReset = false;

    public PauseMenu(ProgramState _pausedState){
        pausedState = _pausedState;
    }

    @Override
    public void init(){
        UnderSquare.INSTANCE.cursor(ARROW);
        translate(pausedState.cam.rel.x,pausedState.cam.rel.y);
        initWidgets();

        //background
        fill(Color.BLACK.getRGB(),50);
        rect(0,0,getWidth(),getHeight());

    }

    public void initWidgets(){

        textSize(24);
        titleText = new TextWidget(
                new Vector2(
                        getWidth()/2 - (scaleToScreenX(40) + textWidth("PAUSE"))/2f,
                        getHeight()/2 - scaleToScreenY(100)
                ),
                new Vector2(
                        scaleToScreenX(40) + textWidth("PAUSE"),
                        scaleToScreenY(60)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "PAUSE",
                24,
                CENTER
        );

        retryButton = new ButtonWidget(
                new Vector2(
                        getWidth()/2 - scaleToScreenX(100),
                        getHeight()/2 + scaleToScreenY(40)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Retry Game",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.changeState(pausedState);}
        );

        resumeButton = new ButtonWidget(
                new Vector2(
                        getWidth()/2 - scaleToScreenX(100),
                        getHeight()/2 - scaleToScreenY(20)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Resume Game",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.changeStateWithoutInitWithUpdate(pausedState);}
        );

        exitButton = new ButtonWidget(
                new Vector2(
                        getWidth()/2 - scaleToScreenX(100),
                        getHeight()/2 + scaleToScreenY(100)
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

        resumeButton.update();
        exitButton.update();
        retryButton.update();

        if(KeyHandler.escape && KeyHandler.escapeR){UnderSquare.changeStateWithoutInit(pausedState);}
    }

    @Override
    public void paint(){

        //elements
        resumeButton.paint();
        exitButton.paint();
        titleText.paint();
        retryButton.paint();
    }

}
