package com.lukullu.undersquare.game.entity.interactable.sensor;

import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.player.Player;

public class Button extends Sensor{

    private boolean isColliding = false;

    public Button(Vector2 _pos, int _id) {
        super(_pos, _id);
    }
    
    @Override
    public void entityCollide() {

		entityColliders = Collision.entityCollision(this);

        boolean collidesWithPlayer = false;
        for(Entity collider : entityColliders)
        {
            if( collider instanceof Player ) collidesWithPlayer = true;
        }

        if(collidesWithPlayer)
        {
		    if(entityColliders.size() > 0) collide(); 
        }
        else
        {
            resetCollide(); 
        }
        
	}

    @Override
    public void collide()
    {

        if(!isColliding)
        {
            updateActors();
            isColliding = true;
        }

    }

    public void resetCollide()
    {
        isColliding = false;
    }

}
