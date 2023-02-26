package com.lukullu.undersquare.game.entity.player;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.KeyHandler;
import com.lukullu.undersquare.common.data.Direction;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.common.msc.Filter;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.enemy.Enemy;
import com.lukullu.undersquare.game.entity.projectile.Projectile;
import com.lukullu.undersquare.game.item.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

import static com.lukullu.undersquare.UnderSquare.deltaTime;
import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.collision.Collision.entityCollision;

public class Player extends Entity implements Serializable {

	public ArrayList<Weapon> weapons = new ArrayList<>();

	public int currentWeaponIndex = 0;

	float timeSinceLastShot = 0;
	float dashDelay = 0;

	boolean xReset = false;
	boolean cReset = false;
	boolean shiftReset = false;
	
	public Player(Vector2 _pos, Vector2 _dim) {
		super(_pos, _dim);
		UnderSquare.state.cam.setUp(this);
		startingHP = playerHP;
		HP = startingHP;
		dmg = playerContactDMG;
	}

	@Override
	public void onDeath(Entity cause) {

		assert UnderSquare.getGameHandler() != null;
		UnderSquare.getGameHandler().paint();
		UnderSquare.getGameHandler().didIDie = true;
	}

	@Override
	public void entityCollide() {

		entityColliders = entityCollision(this);
		entityColliders = Filter.filterOwnPrj(entityColliders,this);
		if(entityColliders.size() > 0){ collide(); }

	}

	public void behavior() {

		Debug.displayTemp(""+HP);

		if (UnderSquare.state.cam != null) UnderSquare.state.cam.update(this,deltaPos);
		
		float fx = 0;
		float fy = 0;

		if(KeyHandler.a){ fx -= appliedForce;}

		if(KeyHandler.d){ fx += appliedForce;}

		if(KeyHandler.w){ fy -= appliedForce;}

		if(KeyHandler.s){ fy += appliedForce;}
		
		force = new Vector2(fx == 0 ? force.x : force.x + fx, fy == 0 ? force.y : force.y + fy);

		// dash
		if(KeyHandler.shift && dashDelay >= PLAYER_DASH_DELAY && shiftReset){
			force = new Vector2(force.x * DASH_ACCELERATION, force.y * DASH_ACCELERATION);
			dashDelay = 0;
			iFrameTimeCounter = PLAYER_DASH_IFRAME_TIME;
			shiftReset = false;
		}

		// shoot weapon
		if(weapons.size() > 0){
			if(timeSinceLastShot > 1/weapons.get(currentWeaponIndex).fireRate){
				if(KeyHandler.up || KeyHandler.down || KeyHandler.left || KeyHandler.right) {
					
					Direction dir = Direction.NONE;
					
					if(KeyHandler.up){ dir = Direction.UP;}
					if(KeyHandler.down){ dir = Direction.DOWN;}
					
					if(KeyHandler.right){ dir = Direction.LEFT;}
					if(KeyHandler.left){ dir = Direction.RIGHT;}
					
					fireWeapon(this,weapons.get(currentWeaponIndex),dir);
					timeSinceLastShot = 0;
				}
			}
		}

		// weapon selection
		if(weapons.size() > 1) {
			if (KeyHandler.x && xReset) {
				currentWeaponIndex++;
				xReset = false;
				timeSinceLastShot = 1000;
			}
			if (KeyHandler.c && cReset) {
				currentWeaponIndex--;
				cReset = false;
				timeSinceLastShot = 1000;
			}
			if (currentWeaponIndex > weapons.size() - 1) {
				currentWeaponIndex = 0;
			}
			if (currentWeaponIndex < 0) {
				currentWeaponIndex = weapons.size() - 1;
			}
		}

		// control resets
		if(!shiftReset && !KeyHandler.shift){ shiftReset = true;}
		if(!xReset && !KeyHandler.x){ xReset = true;}
		if(!cReset && !KeyHandler.c){ cReset = true;}


		// delay updates
		timeSinceLastKill += deltaTime;
		timeSinceLastShot += deltaTime;
		dashDelay += deltaTime;
	}
	
	public void fireWeapon(Entity origin, Weapon weapon, Direction dir) {
		Vector2 startingForce = new Vector2(0,0);
		Vector2 prjDim = weapon.dim;
		
		Vector2 directedBlowBackForce = new Vector2(0,0);
		
		if(dir == Direction.LEFT) {startingForce.x += weapon.initForce.x; directedBlowBackForce.x += weapon.blowBackForce.x; prjDim = new Vector2(weapon.dim.y,weapon.dim.x);}
		if(dir == Direction.RIGHT){startingForce.x -= weapon.initForce.x; directedBlowBackForce.x -= weapon.blowBackForce.x; prjDim = new Vector2(weapon.dim.y,weapon.dim.x);}
		if(dir == Direction.UP)   {startingForce.y -= weapon.initForce.y; directedBlowBackForce.y -= weapon.blowBackForce.x; }
		if(dir == Direction.DOWN) {startingForce.y += weapon.initForce.y; directedBlowBackForce.y += weapon.blowBackForce.x; }
		
		startingForce.x += origin.force.x * weapon.inertiaCoefficient;
		startingForce.y += origin.force.y * weapon.inertiaCoefficient;

		assert UnderSquare.getGameHandler() != null;
		UnderSquare.getGameHandler().entities.add( weapon.projectileConstructor.construct(
						new Vector2(
								origin.pos.x + origin.dim.x/2 - prjDim.x/2,
								origin.pos.y + origin.dim.y/2 - prjDim.y/2
						),
						prjDim,
						startingForce,
						weapon.dmg,
						weapon.ttl,
						weapon.mass,
						weapon.inertiaCoefficient,
						origin
				)
		);
		
		origin.force = new Vector2(origin.force.x - directedBlowBackForce.x, origin.force.y - directedBlowBackForce.y);
		
	}

	@Override
	public void kills(Entity victim)
	{
		
		if(timeSinceLastKill < Constants.COMBO_TIME)
		{
			comboMultiplier *= 2;
		}
		else
		{
			comboMultiplier = 1;	
		}

		gainPoints(victim.pointReward * comboMultiplier, victim);

		timeSinceLastKill = 0;

		Debug.displayConst("Points: "+points + " Combo: " + comboMultiplier );

	}

}