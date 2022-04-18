package com.lukullu.undersquare.game.entity.enemy;

import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;

import java.io.Serializable;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.UnderSquare.*;
import static com.lukullu.undersquare.common.collision.Collision.*;

public class Enemy extends Entity implements Serializable {

	public Enemy(Vector2 _pos, Vector2 _dim){
		super(_pos, _dim);
		
		c = enemyColor;
	}
	
	@Override
	public void simulate() {
		
		deltaPos = new Vector2(0,0);
		Vector2 vel;
		Vector2 acc;
		
		// force / mass = acceleration;
		acc = new Vector2(force.x / mass, force.y / mass);
		// acceleration * deltaTime = velocity;
		vel = new Vector2(acc.x * deltaTime, acc.y * deltaTime);
		// velocity * deltaTime = deltaPosition;
		deltaPos = new Vector2(vel.x * deltaTime, vel.y * deltaTime);
		
		if(deltaPos.x < 0.001 && deltaPos.x > -0.001){ deltaPos.x = 0; }
		if(deltaPos.y < 0.001 && deltaPos.y > -0.001){ deltaPos.y = 0; }
		
		//collisionDetection
		deltaPos = rayCast(pos,dim,deltaPos);
		collisionDirection = calcCollisionAxis(new Vector2(pos.x + deltaPos.x,pos.y + deltaPos.y), dim);
		
		pos = new Vector2(pos.x + deltaPos.x, pos.y + deltaPos.y);
		
	}
	
}
