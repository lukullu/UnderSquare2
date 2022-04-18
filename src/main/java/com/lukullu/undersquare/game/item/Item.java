package com.lukullu.undersquare.game.item;

import com.lukullu.undersquare.game.entity.player.Player;

import java.awt.*;
import java.io.Serializable;

public class Item implements Serializable {
	
	public String name;
	public Color color;
	Item(String _name, Color _color){ name = _name; color = _color; }

	public void onGet(Player target) {}
}
