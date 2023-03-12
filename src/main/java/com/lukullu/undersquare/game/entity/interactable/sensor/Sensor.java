package com.lukullu.undersquare.game.entity.interactable.sensor;

import java.util.ArrayList;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.interactable.actor.Actor;
import com.lukullu.undersquare.game.entity.player.Player;

public class Sensor extends Entity{ 

    private static final Vector2 DIMENSIONS = new Vector2(30,30);

    
    public int id = -1;

    public Sensor(Vector2 _pos, int _id) {
        super(_pos, DIMENSIONS);
        id = _id;
    }

    @Override
    public void simulate()
    {

    }

    @Override
    public void collide()
    {

    }

    public void updateActors()
    {
        for(Actor actor : getActorsWithID(id))
        {
            actor.act();
        }
    }

    public static ArrayList<Actor> getActorsWithID(int id)
    {
        
        ArrayList<Actor> output = new ArrayList<>();

        for(Entity entity : UnderSquare.getGameHandler().entities)
        {

            Actor actor;
            if(entity instanceof Actor) 
            {
                actor = (Actor)entity;
                
                if(actor.id == id) output.add(actor);
                
            }

        }

        return output;

    }
    
}
