package com.lukullu.undersquare.widgets.button;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.IO;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.editor.LevelEditor;

import static com.lukullu.undersquare.common.Constants.*;

public class SaveMapButton extends ButtonWidget implements ProcessingClass {
	
	public LevelEditor state;
	
	public SaveMapButton(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, int _textSize, int _alignment, LevelEditor _state){
		super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, "Save", _textSize, _alignment);
		state = _state;
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
		text(text, pos.x + _rel.x + dim.x/2, pos.y + _rel.y + dim.y/4 + DEFAULT_TEXT_SIZE);
	}
	
	@Override
	public void onClick(){

		if(UnderSquare.getLevelEditor().curGrid.map.name != "Empty Template")
			//TODO: get indices of item and enemy types and format them to an int array so they can be saved
			IO.saveLevelMapAsJson(UnderSquare.getLevelEditor().curGrid.map,UnderSquare.getLevelEditor().curGrid.file);
		
		if (UnderSquare.getLevelEditor() != null) {
			UnderSquare.getLevelEditor().fileList.clearWidgets();
			UnderSquare.getLevelEditor().displayFiles(IO.collectFiles());
		};
		
	}
	
}
