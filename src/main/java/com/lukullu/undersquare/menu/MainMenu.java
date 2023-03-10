package com.lukullu.undersquare.menu;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.IO;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.LevelMap;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.editor.LevelEditor;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.networking.ClientGameHandler;
import com.lukullu.undersquare.networking.HostGameHandler;
import com.lukullu.undersquare.widgets.ScrollWidget;
import com.lukullu.undersquare.widgets.TextWidget;
import com.lukullu.undersquare.widgets.Widget;
import com.lukullu.undersquare.widgets.button.ButtonWidget;
import com.lukullu.undersquare.widgets.button.LoadMapButton;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenX;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class MainMenu extends ProgramState implements ProcessingClass {

    public LevelMap mapToBeLoaded;

    public Widget tempTitleScreen;
    public Widget loadButton;
    public Widget hostButton;
    public Widget editorButton;
    public Widget exitButton;
    public Widget scrollListFiller;
    public Widget TEMPJoinButton;
    public ScrollWidget fileList = new ScrollWidget(ZERO_VECTOR_2, ZERO_VECTOR_2,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,"Maps:");

    @Override
    public void init(){

        UnderSquare.INSTANCE.cursor(ARROW);
        initWidgets();
        displayFiles(IO.collectFiles());
    }

    public void initWidgets() {

        loadButton = new ButtonWidget(
                new Vector2(
                        scaleToScreenX(30),
                        scaleToScreenY(1020)
                ),
                new Vector2(
                        scaleToScreenX(400),
                        scaleToScreenY(40)),
                ROUNDEDCORNERS,ROUNDEDCORNERS,0,0,
                "Play",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.changeState(new GameHandler(mapToBeLoaded));}
        );

        /*
        hostButton = new ButtonWidget(
                new Vector2(
                        scaleToScreenX(1060),
                        scaleToScreenY(1010)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Host Game",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> {
                    try{ UnderSquare.changeState(
                        new HostGameHandler(UnderSquare.getMainMenu().mapToBeLoaded));
                    }catch (Exception e){}}
        );*/

        editorButton = new ButtonWidget(
                new Vector2(
                        scaleToScreenX(1690),
                        scaleToScreenY(1010)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Level Editor",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.changeState(new LevelEditor());}
        );

        fileList = new ScrollWidget(
                new Vector2(
                        scaleToScreenX(30),
                        scaleToScreenY(30)
                ),
                new Vector2(
                        scaleToScreenX(400),
                        scaleToScreenY(930)),
                ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
                "Level Select:");

        scrollListFiller = new Widget(
                new Vector2(
                        scaleToScreenX(30),
                        scaleToScreenY(960)
                ),
                new Vector2(
                        scaleToScreenX(400),
                        scaleToScreenY(60)),
                0,0,0,0,
                UI_CONTRAST_COLOR
        );

        /* 
        tempTitleScreen = new TextWidget(
                new Vector2(
                        scaleToScreenX(600),
                        scaleToScreenY(100)),
                new Vector2(
                        scaleToScreenX(1000),
                        scaleToScreenY(140)),
                ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
                "UnderSquare", 110
                , LEFT);
        */

        exitButton = new ButtonWidget(
                new Vector2(
                        scaleToScreenX(1480),
                        scaleToScreenY(1010)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Exit Game",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> { UnderSquare.INSTANCE.exit();}
        );

        /*TEMPJoinButton = new ButtonWidget(
                new Vector2(
                        scaleToScreenX(1270),
                        scaleToScreenY(1010)
                ),
                new Vector2(
                        scaleToScreenX(200),
                        scaleToScreenY(40)
                ),
                ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS, ROUNDEDCORNERS,
                "Join Game",
                DEFAULT_TEXT_SIZE,
                CENTER,
                () -> {
                    try{
                        UnderSquare.changeState(new ClientGameHandler(UnderSquare.getMainMenu().mapToBeLoaded));
                    }catch (Exception e){}}
        );*/
    }

    public void displayFiles(Map<String, String> files){

        files.forEach((mapName,file) -> { fileList.addWidget(
                new ButtonWidget(
                        new Vector2(scaleToScreenX(20),0),
                        new Vector2(scaleToScreenX(360),scaleToScreenY(40)),
                        ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,
                        mapName, DEFAULT_TEXT_SIZE,
                        CENTER,
                        () -> {
                            try {
                                UnderSquare.state.setLevel( GSON.fromJson(new FileReader(new File(file)),LevelMap.class),new File(file));
                            } catch (FileNotFoundException e) {}
                        }
                )
        );});

    }

    @Override
    public void update(){

        background(UI_BACKGROUND_COLOR.getRGB());

        loadButton.update();
        editorButton.update();
        fileList.update();
        exitButton.update();
        //TEMPJoinButton.update();
        //hostButton.update();

    }
    @Override
    public void paint() {

        fileList.paint();
        scrollListFiller.paint();
        loadButton.paint();
        editorButton.paint();
        //tempTitleScreen.paint();
        exitButton.paint();
        //TEMPJoinButton.paint();
        //hostButton.paint();

    }

    @Override
    public void setLevel(LevelMap map, File file){

        mapToBeLoaded = map;

    }

}
