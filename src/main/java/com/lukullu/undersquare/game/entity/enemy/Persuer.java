package com.lukullu.undersquare.game.entity.enemy;

import java.util.ArrayList;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Geometry;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.effect.Effect;
import com.lukullu.undersquare.game.entity.projectile.Projectile;
import com.lukullu.undersquare.game.item.ItemBox;

public class Persuer extends Enemy{

    private static final int POINT_REWARD = 1;
    private static final int MAX_HP = 3;

    private static final float TIME_BETWEEN_AQUISITIONS = 1;
    private static final float MAX_DISTANCE_TO_TARGET = 800;

    private static final float PERSUE_FORCE = 400000;
    private static final float IDLE_FORCE = 40000;

    private static final float IDLE_ROTATION_SPEED = 2; 

    

    private static final Vector2 DIMENSTIONS = new Vector2(8, 8);

    private static final int DMG = 2;

    private Entity curTarget;
    private float aquisitionTimer = 0;

    private float rotation = 0;

    public Persuer(Vector2 _pos) {
        super(new Vector2(_pos.x - DIMENSTIONS.x/2, _pos.y - 18), DIMENSTIONS, POINT_REWARD); // TODO: Calc radius
        startingHP = MAX_HP;
		HP = startingHP;
        dmg = DMG;
    }

    @Override
	public void collide(){

		for(int i = 0; i < entityColliders.size(); i++){
			if(!(entityColliders.get(i) instanceof Enemy)){takeDMG(entityColliders.get(i));}
			if (entityColliders.get(i) instanceof Projectile) takeKnockback(entityColliders.get(i).force);
			//else takeKnockback(new Vector2(  -entityColliders.get(i).force.x , -entityColliders.get(i).force.y));
		}

	}
    
    @Override
	public void behavior() 
    {

        // Persuing
        if(curTarget != null)
        {

            Vector2 deltaPos = new Vector2(curTarget.pos.x-pos.x, curTarget.pos.y-pos.y);

            Vector2 unitDeltaPos = Vector2.unitVector2(deltaPos);

            force = new Vector2(unitDeltaPos.x * PERSUE_FORCE, unitDeltaPos.y * PERSUE_FORCE);

        }
        else
        {

            // Idle Behavior
            rotation += UnderSquare.deltaTime * IDLE_ROTATION_SPEED;
		    rotation %= 2 * Math.PI;

            Vector2 direction = new Vector2((float)Math.cos(rotation), (float)Math.sin(rotation));

            force = new Vector2(direction.x * IDLE_FORCE, direction.y * IDLE_FORCE);

        }

        // Aquire Target
        if(aquisitionTimer >= TIME_BETWEEN_AQUISITIONS)
        {
            aquisitionTimer = 0;
            curTarget = aquireTarget();
            
        }

        aquisitionTimer += UnderSquare.deltaTime;

    }

    public float calcRadius()
    {
        //TODO: Implement
        return (float)((IDLE_FORCE * Math.pow((Math.PI * 2) / IDLE_ROTATION_SPEED,2)) / (mass * Math.PI * 2));

    }

    public Entity aquireTarget()
    {
        
        ArrayList<Entity> possibleTargets = new ArrayList<>();
        float smallestDistance = (float) Math.pow(MAX_DISTANCE_TO_TARGET,2);

        Entity output = null;

        if(UnderSquare.state instanceof GameHandler)
            for(Entity entity : UnderSquare.getGameHandler().entities)
            {
                if(!(entity instanceof Enemy) && !(entity instanceof Projectile) && !(entity instanceof ItemBox) && !(entity instanceof Effect))
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
    public void paintHealthBar(){

        if(curTarget != null){

            Vector2 hbPos = new Vector2(
                    pos.x + dim.x/2f - Constants.HEALTH_BAR_WIDTH/2f,
                    pos.y - Constants.HEALTH_BAR_HEIGHT * 2
            );

            fill(Constants.HP_BAR_BORDER_COLOR.getRGB());
            rect(hbPos.x,hbPos.y, Constants.HEALTH_BAR_WIDTH, Constants.HEALTH_BAR_HEIGHT, 3,3,3,3);

            fill(Constants.HP_BAR_HEALTH_COLOR.getRGB());
            rect(hbPos.x,hbPos.y, Math.min((Constants.HEALTH_BAR_WIDTH / startingHP) * HP,Constants.HEALTH_BAR_WIDTH),Constants.HEALTH_BAR_HEIGHT, 3,3,3,3);

            if(HP - startingHP > 0) {
                fill(Constants.HP_BAR_OVERSHOOT_COLOR.getRGB());
                rect(hbPos.x, hbPos.y, Math.min((Constants.HEALTH_BAR_WIDTH / startingHP) * (HP - startingHP), Constants.HEALTH_BAR_WIDTH), Constants.HEALTH_BAR_HEIGHT, 3, 3, 3, 3);
            }

        }
	}
    
}
