package com.lukullu.undersquare.game.entity.inert;

import java.util.Random;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.enemy.Bouncer;

public class Spawner extends Inert{
    
    private static int TIME_BETWEEN_SPAWNS = 600;
    private static int RANGE = Constants.mapGridSize / 4;

    public static int enemyID;
    public int counter = (int) random(0,TIME_BETWEEN_SPAWNS);

    public Spawner(Vector2 _pos, Vector2 _dim, int _ftl, int _enemyID) {
        super(_pos, _dim, _ftl);
        enemyID = _enemyID;
    }

    @Override
    public void behavior()
    {
        
        if(counter <= 0)
        {
            if(Collision.entityGridCollision(pos).size() == 1){
                switch(enemyID)
                {
                    case 0:
                        UnderSquare.getGameHandler().entities.add(new Bouncer(new Vector2(pos.x, pos.y), new Vector2(Constants.enemyDimensions, Constants.enemyDimensions)));
                        break;
                    case 1:
                        Debug.displayConst("You done fucked up mate");
                        break;
                }
                counter = TIME_BETWEEN_SPAWNS;
            }
        }   

        counter -= UnderSquare.deltaTime;
    }

    @Override
    public void paint( Vector2 _pos, float opacity, boolean stroke)
    {
        // you don't get to be painted! Sucks for you I guess. You just spawn shit! Muhahahahahah
    }

}
