package com.lukullu.undersquare.game.item;

import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;
import com.lukullu.undersquare.game.entity.player.Player;

import java.io.Serializable;

import static com.lukullu.undersquare.common.Constants.ITEM_ROTATION_RATE;

public class ItemBox extends Entity implements Serializable {

    Item item;
    float rotation = 0;
    float scale;

    public ItemBox(Vector2 _pos, Vector2 _dim, Item _item){ super(_pos, _dim); item = _item;}

    @Override
    public void update(){
        rotation += ITEM_ROTATION_RATE;
        rotation %= 2 * Math.PI;
        scale = (float)(Math.sin(System.currentTimeMillis() / 800.0) * 0.2 + 1);

    }

    @Override
    public void collide(){

        for(int i = 0; i < entityColliders.size(); i++){
            if (entityColliders.get(i) instanceof Player) {
                takeDMG(entityColliders.get(i));
                item.onGet((Player) entityColliders.get(i));
            }
        }

    }

    @Override
    public void takeDMG(Entity collider){ HP -= collider.dmg; if(HP <= 0){ onDeath(collider); }}

    @Override
    public void paint(Vector2 _pos, float opacity, boolean stroke){

        pushMatrix();
        translate(pos.x + dim.x/2, pos.y + dim.y/2);
        rotate(rotation);
        noStroke();
        scale(scale);
        fill(item.color.getRGB());
        rectMode(CENTER);
        rect(0,0,dim.x,dim.y);
        popMatrix();
        rectMode(CORNER);
        stroke(1);

    }

}
