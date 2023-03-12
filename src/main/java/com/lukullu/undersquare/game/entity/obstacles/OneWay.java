package com.lukullu.undersquare.game.entity.obstacles;

import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.player.Player;

public class OneWay extends Obstacle{

    private static final int PLAYER_PUSH_FORCE = 300000;
    private static final int PUSH_FORCE = 100000;

    private int direction = 0;

    public OneWay(Vector2 _pos, int _direction) {
        super(_pos);
        direction = _direction;
    }
    
    @Override
    public void entityCollide() {
		entityColliders = Collision.entityCollision(this);
		
        for (Entity entity : entityColliders)
        {

            if(!(entity instanceof Player))
            {
                switch(direction){
                    case 0:
                        entity.force.x -= PUSH_FORCE;
                        break;
                    case 1:
                        entity.force.x += PUSH_FORCE;
                        break;
                    case 2:
                        entity.force.y -= PUSH_FORCE;
                        break;
                    case 3:
                        entity.force.y += PUSH_FORCE;
                        break;
                }
            }
            else
            {
                switch(direction){
                    case 0:
                        entity.force.x -= PLAYER_PUSH_FORCE;
                        break;
                    case 1:
                        entity.force.x += PLAYER_PUSH_FORCE;
                        break;
                    case 2:
                        entity.force.y -= PLAYER_PUSH_FORCE;
                        break;
                    case 3:
                        entity.force.y += PLAYER_PUSH_FORCE;
                        break;
                }
            }
        }

	}

    @Override
    public void paint( Vector2 _pos, float opacity, boolean stroke){

        noStroke();
        fill(c.getRGB(),10);
		rect(_pos.x,_pos.y,dim.x,dim.y);

        switch(direction)
        {
            case 0:
                paintToLeft();
                break;
            case 1:
                paintToRight();
                break;
            case 2:
                paintToUp();
                break;
            case 3:
                paintToDown();
                break;
        }
    
	}

    private void paintToRight()
    {
        fill(c.getRGB(),10);
        rect(pos.x+dim.x-dim.x/2,pos.y,dim.x/2,dim.y);
        fill(c.getRGB(),10);
        rect(pos.x+dim.x-dim.x/4,pos.y,dim.x/4,dim.y);
        fill(c.getRGB(),10);
        rect(pos.x+dim.x-dim.x/8,pos.y,dim.x/8,dim.y);
        fill(c.getRGB(),10);
        rect(pos.x+dim.x-dim.x/16,pos.y,dim.x/16,dim.y);
    }

    private void paintToLeft()
    {
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x/2,dim.y);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x/4,dim.y);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x/8,dim.y);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x/16,dim.y);
    }

    private void paintToUp()
    {
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x,dim.y/2);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x,dim.y/4);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x,dim.y/8);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y,dim.x,dim.y/16);
    }

    private void paintToDown()
    {
        fill(c.getRGB(),10);
        rect(pos.x,pos.y+dim.y-dim.y/2,dim.x,dim.y/2);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y+dim.y-dim.y/4,dim.x,dim.y/4);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y+dim.y-dim.y/8,dim.x,dim.y/8);
        fill(c.getRGB(),10);
        rect(pos.x,pos.y+dim.y-dim.y/16,dim.x,dim.y/16);
    }

}
