package com.lukullu.undersquare.game.entity.interactable.actor;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;

public class Door extends Actor{

    public Door(Vector2 _pos, int _id) {
        super(_pos, _id);
    }
    
    @Override
    public void act()
    {
        UnderSquare.getGameHandler().updateGeometry(mapPos);
    }

}
