package com.lukullu.undersquare.game.entity.enemy;

import java.util.ArrayList;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.collision.Collision;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.common.msc.Geometry;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.player.Player;
import com.lukullu.undersquare.game.entity.projectile.Projectile;
import com.lukullu.undersquare.game.item.Weapon;

public class Turret extends Enemy{


    private static final int POINT_REWARD = 200; //TODO: Point Reward
    private static final int MAX_HP = 15;
    private static final int DMG = 5;
    private static final int WEAPON_DMG = 5;
    private static final Vector2 DIMENSIONS = new Vector2(30, 30);

    private static final float TIME_BETWEEN_AQUISITIONS = 1;
    private static final float MAX_DISTANCE_TO_TARGET = 800;
    private static final float TIME_BETWEEN_SHOTS = 0.8f;


    private float timeSinceLastShot = 0;
    private float aquisitionTimer = 0;
    private Entity curTarget;

    public Turret(Vector2 _pos) {
        super(_pos, DIMENSIONS, POINT_REWARD);
        startingHP = MAX_HP;
		HP = startingHP;
        dmg = DMG;
    }

    @Override
    public void behavior()
    {
        

        if(aquisitionTimer >= TIME_BETWEEN_AQUISITIONS)
        {
            curTarget = aquireTarget();
            aquisitionTimer = 0;
        }

        if(timeSinceLastShot >= TIME_BETWEEN_SHOTS)
        {
            if(curTarget != null)
            {
                if(Collision.rayCastSuccess(pos, new Vector2(2,2), new Vector2(curTarget.pos.x-pos.x, curTarget.pos.y-pos.y)))
                {
                    fireWeapon(this, Weapon.SNIPER, new Vector2(curTarget.pos.x-pos.x, curTarget.pos.y-pos.y));
                }
            }
            timeSinceLastShot = 0;

        }

        aquisitionTimer += UnderSquare.deltaTime;
        timeSinceLastShot += UnderSquare.deltaTime;
    }

    public void fireWeapon(Entity origin, Weapon weapon, Vector2 dirForce) {

		Vector2 prjDim = new Vector2(5, 5);

        dirForce = Vector2.unitVector2(dirForce);
		
		Vector2 directedBlowBackForce = new Vector2(weapon.blowBackForce.x * dirForce.x,weapon.blowBackForce.y * dirForce.y);
        Vector2 startingForce = new Vector2(weapon.initForce.x * dirForce.x, weapon.initForce.y * dirForce.y);
		
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
						WEAPON_DMG,
						weapon.ttl,
						weapon.mass,
						weapon.inertiaCoefficient,
						origin
				)
		);
		
		//origin.force = new Vector2(origin.force.x - directedBlowBackForce.x, origin.force.y - directedBlowBackForce.y);
		
	}

    public Entity aquireTarget()
    {
        
        ArrayList<Entity> possibleTargets = new ArrayList<>();
        float smallestDistance = (float) Math.pow(MAX_DISTANCE_TO_TARGET,2);

        Entity output = null;

        if(UnderSquare.state instanceof GameHandler)
            for(Entity entity : UnderSquare.getGameHandler().entities)
            {
                if(entity instanceof Player)
                {
                    if(Geometry.getDistanceSquared(entity, this) < smallestDistance) possibleTargets.add(entity);
                }
            }

        if(possibleTargets.size() > 0)
        {

            if(possibleTargets.size() == 1)
            {
                return possibleTargets.get(0);
            }

            for(Entity target : possibleTargets)
            {

                float distance = Geometry.getDistanceSquared(target, this);
                if(distance < smallestDistance)
                {
                    output = target;
                    smallestDistance = distance; 
                }

            }

        }

        return output;

    }
    
    @Override
    public void collide(){

		for (Entity entityCollider : entityColliders) {
			if(entityCollider instanceof Projectile)
            {
                Projectile temp = (Projectile) entityCollider;
                if(!(temp.origin instanceof Enemy))
                {
                    takeDMG(entityCollider);
                    //takeKnockback(entityCollider.force);
                }
            }
            else
            {
                takeDMG(entityCollider);
                //takeKnockback(new Vector2(-entityCollider.force.x, -entityCollider.force.y));
            }
		}
		
	}

}
