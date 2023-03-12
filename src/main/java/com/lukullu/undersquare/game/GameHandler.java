package com.lukullu.undersquare.game;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.IO;
import com.lukullu.undersquare.common.KeyHandler;
import com.lukullu.undersquare.common.ProgramState;
import com.lukullu.undersquare.common.data.LevelMap;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Reversed;
import com.lukullu.undersquare.game.camera.Camera;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.enemy.Enemy;
import com.lukullu.undersquare.game.entity.player.Player;
import com.lukullu.undersquare.game.geometry.LevelGeometry;
import com.lukullu.undersquare.menu.DeathMenu;
import com.lukullu.undersquare.menu.PauseMenu;

import java.awt.Color;
import java.util.ArrayList;

import static com.lukullu.undersquare.common.msc.Translation.*;
import static com.lukullu.undersquare.common.Constants.*;

public class GameHandler extends ProgramState implements ProcessingClass {
	
	public LevelGeometry[][] mapGeometry;
	public ArrayList<Entity> entities = new ArrayList<>();
	public LevelMap levelMap;
	public ArrayList<Entity> entitiesToDie = new ArrayList<>();
	public boolean didIDie = false;

	public ArrayList<Entity> paintLayer1 = new ArrayList<>();
	public ArrayList<Entity> paintLayer2 = new ArrayList<>();
	public ArrayList<Entity> paintLayer3 = new ArrayList<>();

	public Player player;

	public GameHandler(LevelMap _levelMap)
	{
		levelMap = _levelMap; 
		levelMap.collisionData = IO.convertMapDataToCollisionData(levelMap.map);
	}

	public void init() {

		UnderSquare.INSTANCE.noCursor();
		levelMap.collisionData = IO.convertMapDataToCollisionData(levelMap.map);
		cam = new Camera(new Vector2(scaleToScreenX((int)cameraPosition.x),scaleToScreenY((int)cameraPosition.y)),new Vector2(scaleToScreenX((int)cameraDimensions.x),scaleToScreenY((int)cameraDimensions.y)));
		mapGeometry = IO.createMapElements(levelMap.map,IO.convertMapDataToCollisionData(levelMap.map), levelMap);

		player = findPlayer();

	}
	
	public Player findPlayer()
	{
		for(Entity entity : entities)
		{
			if(entity instanceof Player){ return (Player)entity; }
		}

		return null;
	}
	
	public void update() {
		
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update();
		}
		
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).entityCollide();
		}
		
		killEntities();

		if(KeyHandler.escape && KeyHandler.escapeR){ UnderSquare.changeState(getPauseMenu());}

		if(didIDie){UnderSquare.changeState(new DeathMenu(this));}
	}

	@Override
	public void updateOnResume(){


		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update();
		}

		for(int i = 0; i < entities.size(); i++){
			entities.get(i).entityCollide();
		}

		killEntities();


	}

	protected ProgramState getPauseMenu() { return new PauseMenu(this); }

	public void killEntities() {

		for(int i = 0; i < entitiesToDie.size(); i++){
			entities.remove(entitiesToDie.get(i));
		}
		
	}

	public void updateGeometry(Vector2 pos)
	{
		if(mapGeometry[(int)pos.y][(int)pos.x] != null)
        {
            mapGeometry[(int)pos.y][(int)pos.x] = null;
        }
        else
        {
            mapGeometry[(int)pos.y][(int)pos.x] = new LevelGeometry(new Vector2((int)pos.x * Constants.mapGridSize, (int)pos.y * Constants.mapGridSize),new Vector2(Constants.mapGridSize,Constants.mapGridSize), Color.BLACK, true);
        }

		levelMap.collisionData[(int)pos.y][(int)pos.x] = !levelMap.collisionData[(int)pos.y][(int)pos.x];

	}
	
	public void paint() {

		background(backgroundColor.getRGB());

		for(int i = 0; i < mapGeometry.length; i++){
			for(int j = 0; j < mapGeometry[0].length; j++){
				if(mapGeometry[i][j] != null) mapGeometry[i][j].paint();
			}
		}

		for (Entity entity : Reversed.reversed(entities)) {
			entity.paintAfterImages();
			entity.paint(entity.pos, 255, true);

			if (entity instanceof Player) {entity.paintHealthBar();}
			if (entity instanceof Enemy) {entity.paintHealthBar();}
		}
		
		cam.paintHUD();
	}
	
}
