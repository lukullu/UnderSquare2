package com.lukullu.undersquare.common.collision;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.data.Direction;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.data.Vector2.unitVector2;
import static java.lang.Math.*;

public class Collision implements ProcessingClass {
	
	
	public static boolean pointRectangleCollision(Vector2 _pointPos, Vector2 _rectanglePos, Vector2 _rectangleDim){
		
		return (_pointPos.x > _rectanglePos.x && _pointPos.x < _rectanglePos.x + _rectangleDim.x)
				&& (_pointPos.y > _rectanglePos.y && _pointPos.y < _rectanglePos.y + _rectangleDim.y);
		
	}
	
	public static Vector2 getGridPosition(Entity e){
		
		return new Vector2(Math.floor((e.pos.x+e.dim.x/2)/mapGridSize), Math.floor((e.pos.y+e.dim.y/2)/mapGridSize));
		
	}
	
	public static Vector2 getGridPosition(Vector2 pos, Vector2 dim){
		
		return new Vector2(Math.floor((pos.x+dim.x/2)/mapGridSize), Math.floor((pos.y+dim.y/2)/mapGridSize));
		
	}
	
	public static Vector2[] getGridPositions(Vector2 pos, Vector2 dim){
		
		Vector2[] output = {
				new Vector2(floor((pos.x)/mapGridSize),floor((pos.y)/mapGridSize)),
				new Vector2(floor((pos.x+dim.x)/mapGridSize),floor((pos.y)/mapGridSize)),
				new Vector2(floor((pos.x)/mapGridSize),floor((pos.y+dim.y)/mapGridSize)),
				new Vector2(floor((pos.x+dim.x)/mapGridSize),floor((pos.y+dim.y)/mapGridSize))
		};
		
		return output;
	}
	
	
	public static Vector2 rayCast(Vector2 pos, Vector2 dim, Vector2 dir){
		float len = (float) sqrt(pow(dir.x,2)+pow(dir.y,2));
		
		dir = unitVector2(dir);
		Vector2 accumulator = new Vector2(0,0);
		
		boolean[][] collisionData; if (UnderSquare.getGameHandler() != null) { collisionData = UnderSquare.getGameHandler().levelMap.collisionData; } else return ZERO_VECTOR_2;
		
		while(sqrt(pow(accumulator.x,2) + pow(accumulator.y,2)) < len){
			
			float tempX = dir.x;
			float tempY = dir.y;
			
			Vector2[] coords = getGridPositions(new Vector2(pos.x + accumulator.x + tempX, pos.y + accumulator.y + tempY),dim);
			
			if(collisionData[(int)coords[0].y][(int)coords[0].x])break;
			if(collisionData[(int)coords[1].y][(int)coords[1].x])break;
			if(collisionData[(int)coords[2].y][(int)coords[2].x])break;
			if(collisionData[(int)coords[3].y][(int)coords[3].x])break;
			
			
			accumulator.x += tempX;
			accumulator.y += tempY;
			
			if(sqrt(pow(accumulator.x,2)+pow(accumulator.y,2)) > len){ return new Vector2(dir.x * len, dir.y * len); }
			
			
		}
		
		return accumulator;
		
	}
	
	public static Direction[] calcCollisionAxis(Vector2 pos, Vector2 dim){
		
		Direction[] escape = {Direction.NONE,Direction.NONE};
		boolean[][] collisionData; if (UnderSquare.getGameHandler() != null) { collisionData = UnderSquare.getGameHandler().levelMap.collisionData; } else return escape;
		
		Vector2[] positiveXAxisCheckCoords = getGridPositions(new Vector2(pos.x + checkRange, pos.y),dim);
		Vector2[] negativeXAxisCheckCoords = getGridPositions(new Vector2(pos.x - checkRange, pos.y),dim);
		Vector2[] positiveYAxisCheckCoords = getGridPositions(new Vector2(pos.x, pos.y + checkRange),dim);
		Vector2[] negativeYAxisCheckCoords = getGridPositions(new Vector2(pos.x, pos.y - checkRange),dim);
		
		Direction xAxisDirection = Direction.NONE;
		Direction yAxisDirection = Direction.NONE;
		
		for(int i = 0; i < 4; i++){
			if(collisionData[(int)positiveXAxisCheckCoords[i].y][(int)positiveXAxisCheckCoords[i].x]){ xAxisDirection = Direction.RIGHT;} else
			if(collisionData[(int)negativeXAxisCheckCoords[i].y][(int)negativeXAxisCheckCoords[i].x]){ xAxisDirection = Direction.LEFT ;}
			if(collisionData[(int)positiveYAxisCheckCoords[i].y][(int)positiveYAxisCheckCoords[i].x]){ yAxisDirection = Direction.DOWN ;} else
			if(collisionData[(int)negativeYAxisCheckCoords[i].y][(int)negativeYAxisCheckCoords[i].x]){ yAxisDirection = Direction.UP   ;}
		}
		
		Direction[] output = {xAxisDirection,yAxisDirection};
		
		return output;
		
	}
	
	public static boolean boxCollision(Vector2 pos1, Vector2 dim1, Vector2 pos2, Vector2 dim2){
		
		return (pos1.x <= pos2.x + dim2.x && pos1.x + dim1.x >= pos2.x) && (pos1.y <= pos2.y + dim2.y && pos1.y + dim1.y >= pos2.y);
		
	}
	
	public static ArrayList<Entity> entityCollision(Entity origin) {
		
		ArrayList<Entity> output = new ArrayList<>();
		
		List<Entity> entities = UnderSquare.getGameHandler().entities;
		for(int i = 0; i < entities.size(); i++){
			
			if(entities.get(i) != origin) {
				
				if(boxCollision(origin.pos,origin.dim,entities.get(i).pos,entities.get(i).dim)){output.add(entities.get(i));}
				
			}
			
		}
		
		return output;
		
	}

	public static ArrayList<Entity> entityGridCollision(Vector2 pos) {
		
		ArrayList<Entity> output = new ArrayList<>();
		
		Vector2 gridPos = getGridPosition(pos, ZERO_VECTOR_2);

		List<Entity> entities = UnderSquare.getGameHandler().entities;
		for(int i = 0; i < entities.size(); i++){
			
			if(boxCollision(new Vector2(gridPos.x * Constants.mapGridSize, gridPos.y * Constants.mapGridSize), new Vector2(Constants.mapGridSize, Constants.mapGridSize),entities.get(i).pos,entities.get(i).dim)){output.add(entities.get(i));}
			
		}
		
		return output;
		
	}
	
}
