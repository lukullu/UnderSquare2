package com.lukullu.undersquare.game.entity.projectile;

import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;

@FunctionalInterface
public interface ProjectileConstructor {

	Projectile construct(Vector2 _pos, Vector2 _dim, Vector2 _initForce, int dmg, float ttl, float _mass, float _inertiaCoefficient, Entity _origin);

}
