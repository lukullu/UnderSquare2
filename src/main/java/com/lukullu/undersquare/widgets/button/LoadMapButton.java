package com.lukullu.undersquare.widgets.button;


import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.editor.LevelEditor;
import com.lukullu.undersquare.game.GameHandler;
import com.lukullu.undersquare.menu.MainMenu;
import com.lukullu.undersquare.widgets.Grid;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.IO.getItemPositionsfromIndices;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenX;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class LoadMapButton extends ButtonWidget {

	public LoadMapButton(Vector2 _pos, Vector2 _dim , int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, int _textSize, int _alignment){
		super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, "Load", _textSize, _alignment);

	}

	@Override
	public void paint(Vector2 _rel) {
		switch(buttonState) {
			case CLICKED:
				fill(UI_BACKGROUND_COLOR.getRGB());
				break;
			case HOVER:
				fill(UI_FOCUS_COLOR.getRGB());
				break;
			default:
				fill(UI_CONTRAST_COLOR.getRGB());
		}

		noStroke();
		rect(pos.x + _rel.x, pos.y + _rel.y, dim.x, dim.y, cornerTL, cornerTR, cornerBR, cornerBL);
		fill(UI_TEXT_COLOR.getRGB());
		textAlign(CENTER);
		textSize(DEFAULT_TEXT_SIZE);
		text(getText(), pos.x + _rel.x + dim.x/2, pos.y + _rel.y + dim.y/4 + DEFAULT_TEXT_SIZE);
	}

	@Override
	public void onClick() {

		if(UnderSquare.state instanceof LevelEditor) {
			assert UnderSquare.getLevelEditor() != null;
			if (UnderSquare.getLevelEditor().mapToBeLoaded != null){
				UnderSquare.getLevelEditor().tileSettings.reset();
				UnderSquare.getLevelEditor().itemIndicesMap = getItemPositionsfromIndices(UnderSquare.getLevelEditor().mapToBeLoaded);
				UnderSquare.getLevelEditor().curSettings = UnderSquare.getLevelEditor().mapToBeLoaded.settings;
				UnderSquare.getLevelEditor().curGrid = new Grid(new Vector2(scaleToScreenY(950), scaleToScreenY(950)), UnderSquare.getLevelEditor().mapToBeLoaded.mapData.length, UnderSquare.getLevelEditor().mapToBeLoaded, UnderSquare.getLevelEditor().fileToBeLoaded);
			}
		} else
		if(UnderSquare.state instanceof MainMenu){
			assert UnderSquare.getMainMenu() != null;
			if (UnderSquare.getMainMenu().mapToBeLoaded != null)
				UnderSquare.changeState(new GameHandler(UnderSquare.getMainMenu().mapToBeLoaded));

		}
	}
	
}
