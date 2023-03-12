package com.lukullu.undersquare.game.entity;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.data.Direction;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.effect.PointReward;
import com.lukullu.undersquare.game.entity.projectile.Projectile;
import com.lukullu.undersquare.game.item.Weapon;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import static com.lukullu.undersquare.UnderSquare.deltaTime;
import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.collision.Collision.*;

public class Entity implements ProcessingClass, Serializable {
	
	public boolean isActive = false;
	public boolean isVisible = false;

	public int startingHP = 1;

	public int HP = 1;
	public int dmg;
	
	public Vector2 pos;
	public Vector2 dim;
	public Vector2 deltaPos;

	public int points = 0;
	public int pointReward = 0;
	public int comboMultiplier = 1;
	public float timeSinceLastKill = Constants.COMBO_TIME;

	public Vector2[] prevPos = new Vector2[AFTERIMAGE_LENGTH];
	
	public Vector2 force = new Vector2(0,0);
	public Direction[] collisionDirection = { Direction.NONE, Direction.NONE };
	public ArrayList<Entity> entityColliders;
	
	public float mass = 10;

	public float iFrameTimeCounter = 0;
	
	public Color c = Color.black;
	
	public Entity(Vector2 _pos, Vector2 _dim){
		init(_pos, _dim);
	}

	public Entity(Vector2 _pos, Vector2 _dim, int _pointReward){
		init(_pos, _dim);
		pointReward = _pointReward;
	}
	
	public void init(Vector2 _pos, Vector2 _dim){
		
		isActive  = true;
		isVisible = true;
		pos  = _pos;
		dim  = _dim;
		HP = startingHP;

	}
	
	public void update(){
		simulate();
		behavior();
		iFrameTimeCounter -= deltaTime;
		updatePreviousPositions();
	}

	public void updatePreviousPositions(){

		if (prevPos.length - 1 >= 0) System.arraycopy(prevPos, 0, prevPos, 1, prevPos.length - 1);
		prevPos[0] = pos;
	}

	public void paintAfterImages(){

		for(int i = 0; i < prevPos.length; i++){
			if(prevPos[i] != null)
				paint(prevPos[i],125-(125f/prevPos.length)*i, false);
		}
	}
	
	public void collide(){

		for (Entity entityCollider : entityColliders) {
			takeDMG(entityCollider);
			if (entityCollider instanceof Projectile) takeKnockback(entityCollider.force);
			else takeKnockback(new Vector2(-entityCollider.force.x, -entityCollider.force.y));
		}
		
	}

	public void behavior() {}
	public void takeDMG(Entity collider){ if(iFrameTimeCounter <= 0){ HP -= collider.dmg; if(HP <= 0){ onDeath(collider); } iFrameTimeCounter = I_FRAME_TIME;} }
	public void kills(Entity victim){}
	public void onDeath(Entity cause){ cause.kills(this) ; UnderSquare.getGameHandler().entitiesToDie.add(this); }
	
	public void takeKnockback(Vector2 amount)
	{ 
		force.x += amount.x * innertiaCoefficient; 
		force.y += amount.y * innertiaCoefficient; 
	}

	public void gainPoints(int amount, Entity victim)
	{ 
		points += amount; 
	
		assert UnderSquare.getGameHandler() != null;
		UnderSquare.getGameHandler().entities.add( new PointReward(victim.pos, dim, Constants.REWARD_DISPLAY_FRAMES, amount) );
	}

	public void gainPoints(int amount)
	{ 
		points += amount; 
	
		// TODO: display otherwise
		//assert UnderSquare.getGameHandler() != null;
		//UnderSquare.getGameHandler().entities.add( new PointReward(victim.pos, dim, Constants.REWARD_DISPLAY_FRAMES, amount) );
	}

	public void restoreHP(int amount){
		if( HP + amount > startingHP*2){ HP = startingHP*2;} else HP += amount;
	}

	public void entityCollide() {
		entityColliders = entityCollision(this);
		if(entityColliders.size() > 0){ collide(); }
	}
	
	//default physics
	public void simulate(){
		deltaPos = new Vector2(0,0);
		Vector2 vel;
		Vector2 acc;
		
		if(collisionDirection[0] == Direction.RIGHT && force.x > 0) { force = new Vector2(0,force.y); }
		if(collisionDirection[0] == Direction.LEFT  && force.x < 0) { force = new Vector2(0,force.y); }
		if(collisionDirection[1] == Direction.DOWN  && force.y > 0) { force = new Vector2(force.x,0); }
		if(collisionDirection[1] == Direction.UP    && force.y < 0) { force = new Vector2(force.x,0); }
		
		// force - force * coefficientOfFriction = correctedForce
		force = new Vector2(force.x - force.x * coefficientOfFriction, force.y - force.y * coefficientOfFriction);
		// force / mass = acceleration;
		acc = new Vector2(force.x / mass, force.y / mass);
		// acceleration * deltaTime = velocity;
		vel = new Vector2(acc.x * deltaTime, acc.y * deltaTime);
		// velocity * deltaTime = deltaPosition;
		deltaPos = new Vector2(vel.x * deltaTime, vel.y * deltaTime);

		// if stuck -> reverse thrust!
		if(force.x + force.y != 0 && deltaPos.x + deltaPos.y == 0)
			force = new Vector2(-force.x,-force.y);

		
		if(deltaPos.x < 0.001 && deltaPos.x > -0.001){ deltaPos.x = 0; }
		if(deltaPos.y < 0.001 && deltaPos.y > -0.001){ deltaPos.y = 0; }
		
		//collisionDetection
		deltaPos = rayCast(pos,dim,deltaPos);
		collisionDirection = calcCollisionAxis(new Vector2(pos.x + deltaPos.x,pos.y + deltaPos.y), dim);
		
		pos = new Vector2(pos.x + deltaPos.x, pos.y + deltaPos.y);


	}
	
	//default paint
	public void paint( Vector2 _pos, float opacity, boolean stroke){
		if (stroke) stroke(1); else noStroke();
		fill(c.getRGB(),opacity);
		rect(_pos.x,_pos.y,dim.x,dim.y);
	}
	
	public void fireWeapon(Entity origin, Weapon weapon, Direction dir) {}
	
	public void paintHealthBar(){

		Vector2 hbPos = new Vector2(
				pos.x + dim.x/2f - HEALTH_BAR_WIDTH/2f,
				pos.y - HEALTH_BAR_HEIGHT * 2
		);

		fill(HP_BAR_BORDER_COLOR.getRGB());
		rect(hbPos.x,hbPos.y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, 3,3,3,3);

		fill(HP_BAR_HEALTH_COLOR.getRGB());
		rect(hbPos.x,hbPos.y, Math.min((HEALTH_BAR_WIDTH / startingHP) * HP,HEALTH_BAR_WIDTH),HEALTH_BAR_HEIGHT, 3,3,3,3);

		if(HP - startingHP > 0) {
			fill(HP_BAR_OVERSHOOT_COLOR.getRGB());
			rect(hbPos.x, hbPos.y, Math.min((HEALTH_BAR_WIDTH / startingHP) * (HP - startingHP), HEALTH_BAR_WIDTH), HEALTH_BAR_HEIGHT, 3, 3, 3, 3);
		}
	}
	
}
