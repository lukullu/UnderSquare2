package com.lukullu.undersquare.game.entity.projectile;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Direction;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Filter;
import com.lukullu.undersquare.game.entity.Entity;

import java.io.Serializable;

import static com.lukullu.undersquare.UnderSquare.deltaTime;
import static com.lukullu.undersquare.common.Constants.prjColor;
import static com.lukullu.undersquare.common.collision.Collision.*;

public class Projectile extends Entity implements ProcessingClass, Serializable {

	public int startingHP = 1;

	public Entity origin;
	public float tl = 0;
	public float ttl;
	public float inertiaCoefficient;
	
	public Projectile(Vector2 _pos, Vector2 _dim, Vector2 _initForce, int _dmg, float _ttl, float _mass, float _inertiaCoefficient, Entity _origin){
		super(_pos, _dim);
		force = _initForce;
		origin = _origin;
		dmg = _dmg;
		ttl = _ttl;
		mass = _mass;
		inertiaCoefficient = _inertiaCoefficient;
		c = prjColor;
	}
	
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
		
		if(tl >= ttl){ UnderSquare.getGameHandler().entities.remove(this);}
		tl += deltaTime;
		
	}

	@Override
	public void takeDMG(Entity collider){ HP -= collider.dmg; if(HP <= 0){ onDeath(collider); }}

	@Override
	public void kills(Entity victim){ origin.kills(victim); }

	@Override
	public void entityCollide() {
		
		entityColliders = entityCollision(this);
		if(entityColliders.contains(origin)) entityColliders.remove(origin);
		entityColliders = Filter.filterProjectiles(entityColliders);

		if(entityColliders.size() > 0){ collide(); }
		
	}
	
	@Override
	public void behavior() {
		
		//if collides -> destroy
		if(collisionDirection[0] == Direction.RIGHT && force.x > 0 || collisionDirection[0] == Direction.LEFT  && force.x < 0 || collisionDirection[1] == Direction.DOWN  && force.y > 0 || collisionDirection[1] == Direction.UP    && force.y < 0)
		{ UnderSquare.getGameHandler().entities.remove(this);}
		
	}
	
}
