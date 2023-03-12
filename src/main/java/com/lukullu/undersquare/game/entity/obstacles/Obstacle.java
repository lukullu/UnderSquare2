package com.lukullu.undersquare.game.entity.obstacles;

import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;

public class Obstacle extends Entity{

    private static final Vector2 DIMENSIONS = new Vector2(Constants.mapGridSize, Constants.mapGridSize);

    public Obstacle(Vector2 _pos) {
        super(new Vector2(_pos.x - DIMENSIONS.x/2 + 15, _pos.y - DIMENSIONS.y/2 + 15), DIMENSIONS); // TODO: Find out wtf is wrong here
    }

    @Override
    public void simulate(){}

    @Override
    public void collide(){}
    

}
