package com.lukullu.undersquare.game.entity.effect;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.Entity;
import static com.lukullu.undersquare.UnderSquare.deltaTime;

public class Effect extends Entity {

    private int ftl;

    public Effect(Vector2 _pos, Vector2 _dim, int _ftl)
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

        ftl -= 1;

        if(ftl <= 0)
        {
            UnderSquare.getGameHandler().entitiesToDie.add(this);
        }
    }
    
}
