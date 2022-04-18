package com.lukullu.undersquare;

import com.kilix.p2p.client.KilixP2PClient;
import com.kilix.processing.ExtendedPApplet;
import com.lukullu.undersquare.common.KeyHandler;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.editor.LevelEditor;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.menu.ClientPauseMenu;
import com.lukullu.undersquare.menu.HostPauseMenu;
import com.lukullu.undersquare.menu.MainMenu;
import com.lukullu.undersquare.networking.ClientGameHandler;
import com.lukullu.undersquare.networking.HostGameHandler;
import processing.event.MouseEvent;

import static com.lukullu.undersquare.common.Constants.*;

public class UnderSquare extends ExtendedPApplet {
	
	public static ProgramState state;
	public static UnderSquare INSTANCE;
	public static float deltaTime = 0;
	public static float lastFrameTime = 0;

	public static float timeSinceLastClick = 0;
	
	public UnderSquare() { INSTANCE = this; }
	
	public void setup() {
		//universalSetup
		frameRate(60);
		state = new MainMenu();
		state.init();

	}
	
	public void draw() {

		calcDeltaTime();

		state.update();
		Debug.displayTemp(String.format("fps: %03d | dt: %dms", Math.round(frameRate), Math.round(deltaTime * 1000)));
		state.paint();

		timeSinceLastClick+=deltaTime;
		if(mousePressed){ timeSinceLastClick = 0;}
	}
	
	public void calcDeltaTime() {
		float t = millis();
		deltaTime = (t / 1000) - lastFrameTime;
		lastFrameTime = (t / 1000);
	}
	
	public void keyReleased() { KeyHandler.updateReleased(); }
	public void keyPressed() { KeyHandler.updatePressed(); }

	public void mouseWheel(MouseEvent event) {
		if (state instanceof LevelEditor) {
			LevelEditor editor = (LevelEditor) state;
			editor.fileList.scrollPosition += event.getCount() * scrollScale;
		}else
		if (state instanceof MainMenu) {
			MainMenu menu = (MainMenu) state;
			menu.fileList.scrollPosition += event.getCount() * scrollScale;
		}
	}
	
	public static GameHandler getGameHandler() {
		if(state instanceof HostGameHandler hgh) return hgh;
		else if(state instanceof ClientGameHandler cgh) return cgh;
		else if (state instanceof GameHandler gh) return gh;
		else if(state instanceof ClientPauseMenu cpm){ return cpm.pausedState;}
		else if(state instanceof HostPauseMenu hpm){ return hpm.pausedState;}
		return null;
	}
	
	public static LevelEditor getLevelEditor() {
		if (state instanceof LevelEditor le) return le;
		return null;
	}

	public static MainMenu getMainMenu() {
		if (state instanceof MainMenu mm) return mm;
		return null;
	}

	public static void changeState(ProgramState _state){
		state = _state;
		state.init();
	}

	public static void changeStateWithoutInit(ProgramState _state){
		state = _state;
	}

	public static void changeStateWithoutInitWithUpdate(ProgramState _state){
		state = _state;
		state.update();
	}
	
}
