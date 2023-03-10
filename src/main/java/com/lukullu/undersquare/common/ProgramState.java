package com.lukullu.undersquare.common;

import com.lukullu.undersquare.common.data.LevelMap;
import com.lukullu.undersquare.game.camera.Camera;

import java.io.File;

public class ProgramState {
	
	public Camera cam;
	
	public void init() {}
	public void update() {}
	public void paint() {}
	public void updateOnResume(){ update(); }
	
	public void setLevel(LevelMap map, File file) {}

}