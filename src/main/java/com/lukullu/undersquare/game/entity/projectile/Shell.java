package com.lukullu.undersquare.game.entity.projectile;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.Entity;

public class Shell extends Projectile{
	
	public Shell(Vector2 _pos, Vector2 _dim, Vector2 _initForce, int _dmg, float _ttl, float _mass, float _inertiaCoefficient, Entity _origin){
		super(new Vector2(10000000,1000000), _dim, _initForce, _dmg, _ttl, _mass, _inertiaCoefficient, _origin);
		createPellets(_pos, _dim, _initForce, _dmg, _ttl, _mass, _inertiaCoefficient,  _origin);
	}
	
	@Override
	public void update() { UnderSquare.getGameHandler().entities.remove(this); }
	
	void createPellets(Vector2 _pos, Vector2 _dim, Vector2 _initForce, int _dmg, float _ttl, float _mass, float _inertiaCoefficient, Entity _origin){

		float pelletSpreadForce = (force.x + force.y)/6f;

		for(int i = 0; i < Math.round(random(4,10)); i++){
			UnderSquare.getGameHandler().entities.add(new Projectile(_pos, _dim, new Vector2(_initForce.x + pelletSpreadForce * random(-1,1),_initForce.y + pelletSpreadForce * random(-1,1)), _dmg, _ttl, _mass, _inertiaCoefficient, _origin));
		}
	}
	
}
