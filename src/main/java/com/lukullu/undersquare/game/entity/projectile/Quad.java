package com.lukullu.undersquare.game.entity.projectile;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;

import java.io.Serializable;

public class Quad extends Projectile implements ProcessingClass, Serializable {

    public Quad(Vector2 _pos, Vector2 _dim, Vector2 _initForce, int _dmg, float _ttl, float _mass, float _inertiaCoefficient, Entity _origin) {
        super(new Vector2(10000000, 1000000), _dim, _initForce, _dmg, _ttl, _mass, _inertiaCoefficient, _origin);
        createPellets(_pos, _dim, _initForce, _dmg, _ttl, _mass, _inertiaCoefficient, _origin);
    }

    @Override
    public void update() {
        UnderSquare.getGameHandler().entities.remove(this);
    }

    void createPellets(Vector2 _pos, Vector2 _dim, Vector2 _initForce, int _dmg, float _ttl, float _mass, float _inertiaCoefficient, Entity _origin) {

        UnderSquare.getGameHandler().entities.add(new Bullet(_pos, _dim, new Vector2(  400000,  400000 ), _dmg, _ttl, _mass, _inertiaCoefficient, _origin));
        UnderSquare.getGameHandler().entities.add(new Bullet(_pos, _dim, new Vector2( -400000,  400000 ), _dmg, _ttl, _mass, _inertiaCoefficient,_origin));
        UnderSquare.getGameHandler().entities.add(new Bullet(_pos, _dim, new Vector2(  400000, -400000 ), _dmg, _ttl, _mass, _inertiaCoefficient,_origin));
        UnderSquare.getGameHandler().entities.add(new Bullet(_pos, _dim, new Vector2( -400000, -400000 ), _dmg, _ttl, _mass, _inertiaCoefficient,_origin));

    }
}

