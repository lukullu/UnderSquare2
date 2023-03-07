package com.lukullu.undersquare.game.entity.inert;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;

public class Inert extends Entity {
    
    private int ftl;

    public Inert(Vector2 _pos, Vector2 _dim, int _ftl)
    {
        super(_pos, _dim);
        ftl = _ftl;
    }

    // nullify
    @Override
    public void collide(){}

    @Override
    public void simulate()
    {

        if(ftl != -1){
            ftl -= 1;
            if(ftl == 0)
            {
                UnderSquare.getGameHandler().entitiesToDie.add(this);
            }
        }
    }

}
