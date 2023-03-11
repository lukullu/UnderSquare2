package com.lukullu.undersquare.game.entity.enemy;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;

public class Spawner extends Enemy{
    
    private static final int MAX_HP = 50;

    private static final int TIME_BETWEEN_SPAWNS = 300;

    private static final Vector2 DIMENSIONS = new Vector2(40, 40);

    private float rotation = 0;

    public static int enemyID;
    public int counter = (int) random(0,TIME_BETWEEN_SPAWNS);

    public Spawner(Vector2 _pos, int _enemyID) {
        super(_pos, DIMENSIONS, 50); // TODO: Point Reward
        enemyID = _enemyID;
        startingHP = MAX_HP;
		HP = startingHP;
    }

    @Override
    public void behavior()
    {

        if(counter <= 0)
        {
            if(Collision.entityGridCollision(pos).size() <= 5){
                switch(enemyID)
                {
                        case 0: 
                        UnderSquare.getGameHandler().entities.add(new Bouncer(new Vector2(pos.x + dim.x/2, pos.y + dim.y/2)));
                            break;
                        case 1:
                            UnderSquare.getGameHandler().entities.add(new Spawner(new Vector2(pos.x + dim.x/2, pos.y + dim.y/2),0));
                            break;
                        case 2:
                            UnderSquare.getGameHandler().entities.add(new Persuer(new Vector2(pos.x + dim.x/2, pos.y)));
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
		}

	}

    @Override
    public void takeKnockback(Vector2 amount){}

    public void updatePaint()
    {
        rotation += Constants.ITEM_ROTATION_RATE;
        rotation %= 2 * Math.PI;
    }
    

    @Override
    public void paint( Vector2 _pos, float opacity, boolean stroke)
    {
        pushMatrix();
        translate(pos.x + dim.x/2, pos.y + dim.y/2);
        rotate(rotation);
        noStroke();
        //scale(scale);
        fill(Constants.enemyColor.getRGB());
        rectMode(CENTER);
        rect(0,0,dim.x,dim.y);
        popMatrix();
        rectMode(CORNER);
        stroke(1);
    }

}
