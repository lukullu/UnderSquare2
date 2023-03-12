package com.lukullu.undersquare.game.item;

import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.player.Player;
import com.lukullu.undersquare.game.entity.projectile.ProjectileConstructor;

import java.awt.*;

import static com.lukullu.undersquare.common.Constants.*;

public class Weapon extends Item{
	
	//name ; construction ; dim ; dmg ; initForce ; bbForce ; fireRate
	public static final Weapon SNIPER          = new Weapon("Sniper",          Color.gray, HSConstruction,     new Vector2(5,10    ),10, new Vector2(1200000,1200000),   new Vector2(100000,100000), 1   , 3    , 10  , 0.25f );
	public static final Weapon MACHINEGUN      = new Weapon("Machinegun",      Color.gray, bulletConstruction, new Vector2(5,40    ),4,  new Vector2(800000 ,800000 ),   new Vector2(40000,40000  ), 10  , 3    , 10  , 0.25f );
	public static final Weapon SHOTGUN         = new Weapon("Shotgun",         Color.gray, shellConstruction,  new Vector2(5,10    ),5,  new Vector2(600000 ,600000 ),   new Vector2(400000,400000), 1   , 3    , 10  , 0.25f );
	public static final Weapon FLAMETHROWER    = new Weapon("Flamethrower",    Color.gray, shellConstruction,  new Vector2(5,5     ),1,  new Vector2(12000  ,12000  ),   new Vector2(1000  ,1000  ), 100 , 0.3f , 0.2f, 0.005f);
	public static final Weapon QUADSHOT        = new Weapon("QuadShot",        Color.gray, quadConstruction,   new Vector2(5,5     ),4,  new Vector2(600000 ,600000 ),   ZERO_VECTOR_2,                     3   , 3    , 10  , 0.25f );
	public static final Weapon ORBIT           = new Weapon("Orbit",           Color.gray, orbitConstruction,  new Vector2(5,10    ),6,  new Vector2(100000 ,100000 ),   ZERO_VECTOR_2,                     3   , 30   , 10  , 10f   );
	//public static final Weapon SNIPER          = new Weapon("Laser",           Color.gray, HSConstruction,     new Vector2(5,40    ),5,  new Vector2(1200000,1200000),   new Vector2(100000,100000), 5   , 1f   , 10  , 0.01f );
	// TODO: LASER

	public float fireRate;
	public ProjectileConstructor projectileConstructor;
	public Vector2 blowBackForce;
	public Vector2 initForce;
	public Vector2 dim;
	public float ttl;
	public float mass;
	public int dmg;
	public float inertiaCoefficient;
	
	public Weapon(String _name, Color _color, ProjectileConstructor _projectileConstructor, Vector2 _dim, int _dmg, Vector2 _initForce, Vector2 _blowBackForce, float _fireRate, float _ttl, float projectileMass, float _inertiaCoefficient){
		super(_name, _color);
		fireRate = _fireRate; projectileConstructor = _projectileConstructor; dmg = _dmg; blowBackForce = _blowBackForce; initForce = _initForce; dim = _dim; ttl = _ttl; mass = projectileMass; inertiaCoefficient = _inertiaCoefficient;
	}

	@Override
	public void onGet(Player target){
		if(!target.weapons.contains(this)){
			target.weapons.add(this);
			target.currentWeaponIndex = target.weapons.size()-1;
		}
	}
}
