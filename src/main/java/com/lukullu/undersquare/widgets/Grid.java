package com.lukullu.undersquare.widgets;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.KeyHandler;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.LevelMap;

import java.io.File;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.msc.Translation.*;

public class Grid implements ProcessingClass {

	public Vector2 dim;
	public int size;

	public LevelMap map;
	public File file;

	public int offsetX;
	public int offsetY;
	
	public Grid(Vector2 _dim, int _size, LevelMap _map, File _file){
		
		init(_dim, _size, _map, _file);
		
	}
	
	public void init(Vector2 _dim, int _size, LevelMap _map, File _file){
		
		dim = _dim; size = _size; map = _map; file = _file;

		if(map == null){
			map = new LevelMap();
			map.mapData = gridFill(' ');
			map.name = "test";      //TODO: textfield for map name
			map.author = "Lukullu"; //TODO: textfield for author name
			map.gridSize = 80;      //TODO: button for choosing gridSize
			map.enemyFillData = new int[0];
			map.itemBoxFillData = new int[0];
			map.senderReceiverPairingIDs = new int[0];
			map.packHierarchy = 0;

		}

		offsetX = Math.round((getWidth()/2f -  dim.x/2f));
		offsetY = Math.round((getHeight()/2f - dim.y/2f));
		
	}
	
	public char[][] gridFill(char input){

		char[][] output = new char[defaultGridSize][defaultGridSize];

		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(i == 0 || j == 0 || i == size-1 || j == size-1) output[j][i] = '#';
				else output[j][i] = input;
			}
		}
		return output;
	}
	
	public void update(){
		
		updateClickedCell();
		
	}
	
	public void paint(){
		
		paintGrid();
		
	}
	
	public void paintGrid(){
		
		for(int i = 0; i < size; i++){
			
			for(int j = 0; j < size; j++){
				
				switch((""+map.mapData[i][j]).toLowerCase().charAt(0)){
					case '#':
						fill(wallGridColor.getRGB());
						break;
					case ' ':
						fill(UI_CONTRAST_COLOR.getRGB());
						break;
					case 'p':
						fill(playerGridColor.getRGB());
						break;
					case 'e':
						fill(enemyGridColor.getRGB());
						break;
					case 'i':
						fill(itemBoxGridColor.getRGB());
						break;
					default:
						fill(errorGridColor.getRGB());
						break;
				}

				if(i == 0 || i == size-1 || j == 0 || j == size-1){ stroke(wallGridColor.getRGB());} else stroke(UI_LINE_COLOR.getRGB());
				rect(offsetX + j * (dim.x / (float) size) , offsetY + i * (dim.x / (float) size) , dim.x / (float) size, dim.y / (float) size);
				
			}
			
		}

		stroke(wallGridColor.getRGB());
		line(offsetX + (dim.x / (float) size), offsetY, offsetX + (dim.x / (float) size), offsetY + (size-1) * (dim.y / (float) size));
		line(offsetX, offsetY + (dim.y / (float) size), offsetX + (size-1) * (dim.x / (float) size), offsetY + (dim.y / (float) size));
		
	}
	
	
	public void updateClickedCell(){
		
		if(getMousePressed()){
			if(getMouseButton() == LEFT) {
				try {
					map.mapData
							[(int)Math.floor((getMouseY() - offsetY) / (dim.y / (float)size))]
							[(int)Math.floor((getMouseX() - offsetX) / (dim.x / (float)size))]
							= ("" + KeyHandler.lastPressedKey).toLowerCase().charAt(0);
				} catch (Exception e) {}
			}else if(getMouseButton() == RIGHT){
				try {
					UnderSquare.getLevelEditor().openMenu(new Vector2(
							(int)Math.floor((getMouseY() - offsetY) / (dim.y / (float)size)),
							(int)Math.floor((getMouseX() - offsetX) / (dim.x / (float)size))
					));
				}catch (Exception e){}
			}
		}
	}
}
