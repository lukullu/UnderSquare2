package com.lukullu.undersquare.game.entity.interactable.actor;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.geometry.LevelGeometry;

public class Actor extends Entity{

    private static final Vector2 DIMENSIONS = new Vector2(30,30);

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
    
}
