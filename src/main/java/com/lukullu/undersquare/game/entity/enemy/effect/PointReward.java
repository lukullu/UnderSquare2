package com.lukullu.undersquare.game.entity.enemy.effect;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.Constants;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Debug;
import com.lukullu.undersquare.game.entity.Entity;
import static com.lukullu.undersquare.UnderSquare.deltaTime;

public class PointReward extends Effect {

    private int amount;
    private float decreaseByPerFrame = 1;

    public PointReward(Vector2 _pos, Vector2 _dim, float _ftl, int _amount)
    {
        super(_pos, _dim, _ftl);

        decreaseByPerFrame = Math.round(_amount / _ftl);

        amount = _amount;

        if(amount <= 0)
        {
            UnderSquare.getGameHandler().entitiesToDie.add(this);
        }
    }

    @Override
    public void behavior()
    {
        amount -= decreaseByPerFrame;

        if(amount <= 0)
        {
            UnderSquare.getGameHandler().entitiesToDie.add(this);
        }
    }

    @Override
    public void paint( Vector2 _pos, float opacity, boolean stroke )
    {
        //if (stroke) stroke(1); else noStroke();
		fill(Constants.REWARD_DISPLAY_COLOR.getRGB());
        textSize(25f);
		text(amount,_pos.x,_pos.y);
    }


    
}
