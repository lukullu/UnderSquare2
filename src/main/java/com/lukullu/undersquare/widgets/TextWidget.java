package com.lukullu.undersquare.widgets;

import com.lukullu.undersquare.common.data.Vector2;

import static com.lukullu.undersquare.common.Constants.*;

public class TextWidget extends Widget{
	
	private String text;
	public int textSize;
	public int alignment;
	
	public TextWidget(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, String _text, int _textSize, int _alignment) {
		super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, UI_CONTRAST_COLOR);  textSize = _textSize; setText(_text); alignment = _alignment;
	}
	
	@Override
	public void paint(Vector2 _rel) {

		noStroke();
		fill(UI_CONTRAST_COLOR.getRGB());
		rect(pos.x + _rel.x, pos.y + _rel.y, dim.x, dim.y, cornerTL, cornerTR, cornerBR, cornerBL);
		fill(UI_TEXT_COLOR.getRGB());
		textSize(textSize);
		textAlign(alignment,CENTER);
		if(alignment == CENTER){text(getText(), pos.x+_rel.x+dim.x/2f,pos.y + _rel.y + dim.y/2f);}
		else {text(getText(), pos.x+_rel.x+dim.x/10f,pos.y + _rel.y + dim.y/2f);}

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
