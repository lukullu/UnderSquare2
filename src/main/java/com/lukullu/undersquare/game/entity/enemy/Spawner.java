package com.lukullu.undersquare.game.entity.enemy;

import java.util.Random;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.projectile.Projectile;

public class Spawner extends Enemy{
    
    private static int MAX_HP = 50;

    private static int TIME_BETWEEN_SPAWNS = 300;
    private static int RANGE = Constants.mapGridSize / 4;

    private float rotation = 0;
    private float scale;

    public static int enemyID;
    public int counter = (int) random(0,TIME_BETWEEN_SPAWNS);

    public Spawner(Vector2 _pos, Vector2 _dim, int _ftl, int _enemyID) {
        super(_pos, _dim, 50); // TODO: Point Reward
        enemyID = _enemyID;
        startingHP = MAX_HP;
		HP = startingHP;
    }

    @Override
    public void behavior()
    {
        Debug.displayTemp("hiiii");
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

        updatePaint();

    }

    @Override
	public void collide(){

		for(int i = 0; i < entityColliders.size(); i++){
			if(!(entityColliders.get(i) instanceof Enemy)){takeDMG(entityColliders.get(i));}
			//if (entityColliders.get(i) instanceof Projectile) takeKnockback(entityColliders.get(i).force);
		}

	}

    @Override
    public void takeKnockback(Vector2 amount){}

    public void updatePaint()
    {
        rotation += Constants.ITEM_ROTATION_RATE;
        rotation %= 2 * Math.PI;
        scale = (float)(Math.sin(System.currentTimeMillis() / 800.0) * 0.2 + 1);
    }
    

    @Override
    public void paint( Vector2 _pos, float opacity, boolean stroke)
    {
        pushMatrix();
        translate(pos.x + dim.x/2, pos.y + dim.y/2);
        rotate(rotation);
        noStroke();
        scale(scale);
        fill(Constants.enemyColor.getRGB());
        rectMode(CENTER);
        rect(0,0,dim.x,dim.y);
        popMatrix();
        rectMode(CORNER);
        stroke(1);
        
    }

}
