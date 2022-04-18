package com.lukullu.undersquare.menu;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.KeyHandler;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.networking.HostGameHandler;
import com.lukullu.undersquare.widgets.TextWidget;
import com.lukullu.undersquare.widgets.Widget;
import com.lukullu.undersquare.widgets.button.ButtonWidget;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import static com.lukullu.undersquare.common.Constants.DEFAULT_TEXT_SIZE;
import static com.lukullu.undersquare.common.Constants.ROUNDEDCORNERS;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenX;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class HostPauseMenu extends ProgramState implements ProcessingClass{

    public HostGameHandler pausedState;

    public Widget resumeButton;
    public Widget exitButton;
    public Widget titleText;
    public Widget retryButton;
    public Widget tokenDisplayField;

    public HostPauseMenu(HostGameHandler _pausedState){
        pausedState = _pausedState;
    }

    @Override
    public void init(){
        UnderSquare.INSTANCE.cursor(ARROW);
        //translate(pausedState.cam.rel.x,pausedState.cam.rel.y);
        initWidgets();

        this.cam = pausedState.cam;
        //background
        fill(Color.BLACK.getRGB(),100);
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
                () -> { UnderSquare.changeStateWithoutInit(pausedState);}
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
                "Disconnect",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.changeState(new MainMenu());} //TODO: disconnect button
        );

        tokenDisplayField = new ButtonWidget(
                new Vector2(
                        getWidth()/2 - scaleToScreenX(150),
                        getHeight()/2 + scaleToScreenY(160)
                ),
                new Vector2(
                        scaleToScreenX(300),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                pausedState.roomToken,
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> {
                    try {
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(pausedState.roomToken), null);
                        pausedState.client.send("test", "Pfosten sind und bleiben Pfosten und zwar wir xD");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void update(){

        pausedState.update();
        resumeButton.update();
        exitButton.update();
        retryButton.update();
        tokenDisplayField.update();

        if(KeyHandler.escape){UnderSquare.changeStateWithoutInit(pausedState);}
    }

    @Override
    public void paint(){

        //elements
        pausedState.paint();
        tokenDisplayField.paint(new Vector2(cam.rel.x,cam.rel.y));
        resumeButton.paint(new Vector2(cam.rel.x,cam.rel.y));
        exitButton.paint(new Vector2(cam.rel.x,cam.rel.y));
        titleText.paint(new Vector2(cam.rel.x,cam.rel.y));
        retryButton.paint(new Vector2(cam.rel.x,cam.rel.y));
    }

}
