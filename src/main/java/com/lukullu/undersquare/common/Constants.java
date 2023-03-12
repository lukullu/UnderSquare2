package com.lukullu.undersquare.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.game.entity.projectile.*;

import java.awt.*;
import java.io.File;

import static java.lang.Math.PI;

public class Constants{

	public static final Vector2 ZERO_VECTOR_2 = new Vector2(0, 0);

	//UX
	public static final float CLICK_DELAY = 0.1f;

	//entities
	public static final float appliedForce = 45000f;
	public static final float appliedShiftForce = 15000f;
	public static final float coefficientOfFriction = 0.08f;
	public static final int AFTERIMAGE_LENGTH = 6;
	public static final float I_FRAME_TIME = 0.05f;
	public static final float HEALTH_BAR_WIDTH = 60;
	public static final float HEALTH_BAR_HEIGHT = 5;

	//player
	public static final int playerDimensions = 40;
	public static final int playerHP = 25;
	public static final int playerContactDMG = 5;
	public static final float PLAYER_DASH_DELAY = 0.7f;
	public static final float PLAYER_WEAPON_SWAP_DELAY = 0.1f;
	public static final float DASH_ACCELERATION = 3;
	public static final float PLAYER_DASH_IFRAME_TIME = 0.25f;

	// points
	public static final float COMBO_TIME = 2f;
	public static final float COMBO_MAX_MULTIPLIER = 16f;
	public static final int   REWARD_DISPLAY_FRAMES = 60;
	public static final Color REWARD_DISPLAY_COLOR = Color.WHITE;
	public static final float TIME_POINTS_FOR_LIFE = 0.1f;
	public static final int   POINTS_FOR_LIFE_REWARD = 1;

	//itemBox
	public static final int itemBoxDimensions = 30;
	public static final float ITEM_ROTATION_RATE = (float)PI/180;

	//AI
	public static final float timeUntilNextBehaviorUpdate = 2;

	//enemy
	public final static int enemyDimensions = 40;
	
	public static final Vector2 bouncerStartingForce = new Vector2(300000, 300000);
	public static final int bouncerHP = 10;
	public static final int bouncerContactDMG = 5;
	public static final int bouncerPointReward = 100;

	//Pursuer
	public static final int persuerDetectionRange = 1000;
	public static final int persuerAttackRange = 200;

	//projectiles

	//colors
	public static final Color wallGridColor = new Color(0xFAFAFA);
	public static final Color emptyGridColor = new Color(0x1C1C1C).brighter().brighter();
	public static final Color errorGridColor = Color.magenta.brighter();
	public static final Color textColor = Color.black;
	public static final Color playerGridColor = Color.red;
	public static final Color enemyGridColor = Color.green.darker();
	public static final Color spawnerGridColor = Color.yellow;
	public static final Color itemBoxGridColor = Color.blue.darker();
	public static final Color OBSTACLE_GRID_COLOR = Color.magenta.darker();
	public static final Color ACTOR_GRID_COLOR = Color.yellow.brighter();
	public static final Color SENSOR_GRID_COLOR = Color.yellow.darker();
	public static final Color backgroundColor = Color.gray.darker().darker();
	public static final Color prjColor = Color.black;
	public static final Color enemyColor = Color.black;

	public static final Color UI_BACKGROUND_COLOR = new Color(0x0101010);
	public static final Color UI_CONTRAST_COLOR = new Color(0x1C1C1C);
	public static final Color UI_FOCUS_COLOR = new Color(0x2F2F2F);
	public static final Color UI_HEADLINE_COLOR = Color.white;
	public static final Color UI_TEXT_COLOR = new Color(0x888888);
	public static final Color UI_LINE_COLOR = new Color(0x4F4F4F);

	public static final Color HP_BAR_BORDER_COLOR = Color.black;
	public static final Color HP_BAR_HEALTH_COLOR = Color.green.darker();
	public static final Color HP_BAR_OVERSHOOT_COLOR = new Color(0,72,181);

	public static final int FIRERATE_COLOR_RANGE = 40;
	public static final int FIRERATE_COLOR_THRESHOLD = 2;

	//style
	public static final int ROUNDEDCORNERS = 24;
	public static final int DEFAULT_TEXT_SIZE = 15;

	//mapGrid
	public static int mapGridSize = 160; // TODO: create way for this to be loaded with a map

	// sensor/actor
	public static final int ID_AMOUNT = 10; 

	//LevelEdit  or
	public static final int defaultGridSize = 32;

	//rayCast
	public static final float rayCastAccuracy = 0.2f;
	
	//collision
	public static final int checkRange = 1;
	public static final float scrollScale = 10;
	
	//camera
	public static Vector2 cameraPosition = new Vector2(680,405);
	public static Vector2 cameraDimensions = new Vector2(480,270); //720 : 480

	public static final String[] enemyTypeNames =
			{
					"Bouncer",
					"Spawner",
					"Persuer"
			};

	public static final String[] itemTypeNames =
			{
					"Sniper",
					"Machine Gun",
					"Flamethrower",
					"Quadshot",
					"Shotgun",
					"Orbit",
					"Small Potion",
					"Medium Potion",
					"Large Potion"
			};

	public static final Color[] CHANNEL_COLORS = 
			{
				Color.red,
				Color.yellow,
				Color.blue,
				Color.magenta,
				Color.green,
				Color.orange,
				Color.pink,
				Color.cyan,
				Color.white,
				Color.gray
			};

	//prj
	
	//Pellets
	public static final int pelletSpreadForce = 100000;
	public static final int bulletSpreadForce = 50000;
	
	//projectile constructions
	public static final ProjectileConstructor bulletConstruction = Bullet::new;
	public static final ProjectileConstructor shellConstruction = Shell::new;
	public static final ProjectileConstructor quadConstruction = Quad::new;
	public static final ProjectileConstructor HSConstruction = HighSpeed::new;
	public static final ProjectileConstructor orbitConstruction = Orbit::new;
	
	//weapons
	public static final float innertiaCoefficient = 0.25f;
	
	// I/O
	public static final Gson GSON = new GsonBuilder()
	.setLenient()
	.setPrettyPrinting().registerTypeAdapter(Vector2.class, new Vector2.Deserializer())
	.create();


	public static final File MAPS_BASE_DIR = new File("./data/maps/");


	public static final String[] RANDOM_DEATH_MESSAGES = IO.loadDeathMessages();

	//networking
	public static final float PACKET_RATE = 2;

}
