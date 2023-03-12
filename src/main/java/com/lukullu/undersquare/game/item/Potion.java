package com.lukullu.undersquare.game.item;
import com.lukullu.undersquare.game.entity.player.Player;

import java.awt.*;


public class Potion extends Item{

    public static final Potion SMALL_POTION  = new Potion("Small Potion",  Color.magenta.brighter(), 5);
    public static final Potion MEDIUM_POTION = new Potion("Medium Potion", Color.magenta,            15);
    public static final Potion LARGE_POTION  = new Potion("Large Potion",  Color.magenta.darker(),   25);

    public int healthHealed;
    public Potion(String _name, Color _color, int _healthHealed){ super(_name, _color); healthHealed = _healthHealed; }

    @Override
    public void onGet(Player target){ target.restoreHP(healthHealed); }

}
