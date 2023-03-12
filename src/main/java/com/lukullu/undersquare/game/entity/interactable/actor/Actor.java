package com.lukullu.undersquare.game.entity.interactable.actor;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.geometry.LevelGeometry;

public class Actor extends Entity{

    private static final Vector2 DIMENSIONS = new Vector2(15,15);

    public int id = -1;
    public Vector2 mapPos;

    public Actor(Vector2 _pos, int _id) {
        super(_pos, DIMENSIONS);
        id = _id;
        mapPos = Collision.getGridPosition(this);
    }

    @Override
    public void simulate()
    {

    }

    @Override
    public void collide()
    {

    }

    public void act()
    {

    }

    @Override
    public void paint( Vector2 _pos, float opacity, boolean stroke){
		if (stroke) stroke(1); else noStroke();
		fill(Constants.CHANNEL_COLORS[id].getRGB(),opacity);
		rect(_pos.x - Constants.mapGridSize/2 + 2*dim.x,_pos.y - Constants.mapGridSize/2 + 2*dim.y,dim.x,dim.y);
	}
    
}
