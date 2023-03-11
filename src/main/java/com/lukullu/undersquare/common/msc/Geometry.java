package com.lukullu.undersquare.common.msc;

import com.lukullu.undersquare.game.entity.Entity;

public class Geometry {

    public static float getDistance(Entity e1, Entity e2){

        return (float) Math.sqrt(Math.pow(e1.pos.x - e2.pos.x,2)+Math.pow(e1.pos.y - e2.pos.y,2));

    }

    public static float getDistanceSquared(Entity e1, Entity e2)
    {
        return (float) (Math.pow(e1.pos.x - e2.pos.x,2) + Math.pow(e1.pos.y - e2.pos.y,2));
    }

}
