package com.lukullu.undersquare.common.data;

import com.kilix.processing.ProcessingClass;

public class LevelMap implements ProcessingClass {

	//women and children
	public String name;
	public String author;
	public int gridSize;
	public float packHierarchy;
	public MapData settings = new MapData();
	public char[][] map;
	public int[] senderReceiverPairingIDs;

	//not being saved from certain death
	public transient boolean[][] collisionData;

}
