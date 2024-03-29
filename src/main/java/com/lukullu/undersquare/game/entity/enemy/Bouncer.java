package com.lukullu.undersquare.game.entity.enemy;

import com.lukullu.undersquare.common.data.Direction;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.projectile.Projectile;


import static com.lukullu.undersquare.common.Constants.*;

public class Bouncer extends Enemy{

	private static final Vector2 DIMENSIONS = new Vector2(40, 40);

	Vector2 initForce;

	public Bouncer(Vector2 _pos) {
		super(_pos, DIMENSIONS, bouncerPointReward);
		initForce = new Vector2(bouncerStartingForce.x * random(0.3, 1), bouncerStartingForce.y * random(0.3, 1));
		force = initForce;
		startingHP = bouncerHP;
		HP = startingHP;
		dmg = bouncerContactDMG;
	}
	
	@Override
	public void collide(){

		for(int i = 0; i < entityColliders.size(); i++){
			if(!(entityColliders.get(i) instanceof Enemy)){takeDMG(entityColliders.get(i));}
			if (entityColliders.get(i) instanceof Projectile) takeKnockback(entityColliders.get(i).force);
			else takeKnockback(new Vector2(  -entityColliders.get(i).force.x , -entityColliders.get(i).force.y));
		}

	}

	@Override
	public void behavior() {

		if(Math.sqrt(Math.pow(force.x,2)+Math.pow(force.y,2)) > Math.sqrt(Math.pow(initForce.x,2)+Math.pow(initForce.y,2)))
			force = new Vector2(force.x - force.x * coefficientOfFriction, force.y - force.y * coefficientOfFriction);

		if(force == ZERO_VECTOR_2) force = initForce;

		if(collisionDirection[0] == Direction.RIGHT && force.x > 0) { force = new Vector2(-force.x,force.y); }
		if(collisionDirection[0] == Direction.LEFT  && force.x < 0) { force = new Vector2(-force.x,force.y); }
		if(collisionDirection[1] == Direction.DOWN  && force.y > 0) { force = new Vector2(force.x,-force.y); }
		if(collisionDirection[1] == Direction.UP    && force.y < 0) { force = new Vector2(force.x,-force.y); }
		
	}
	
}