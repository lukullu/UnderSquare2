package com.lukullu.undersquare.widgets.button;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.widgets.TextWidget;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.collision.Collision.pointRectangleCollision;

public class ButtonWidget extends TextWidget implements ProcessingClass {
	
	public ButtonState buttonState = ButtonState.IDLE;
	public Runnable onClick;
	
	public boolean reset = false;
	
	public ButtonWidget(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, String _text, int _textSize, int _alignment){
		super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, _text, _textSize, _alignment);
		onClick = () -> {};
	}
	
	public ButtonWidget(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, String _text, int _textSize, int _alignment, Runnable _onClick){
		super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, _text, _textSize, _alignment);
		onClick = _onClick;
	}
	
	@Override
	public void update(Vector2 _rel) {
		
		if(pointRectangleCollision(new Vector2(getMouseX(), getMouseY()), new Vector2(pos.x + _rel.x, pos.y + _rel.y), dim) && !reset){
			buttonState = ButtonState.HOVER;
		}else{
			buttonState = ButtonState.IDLE;
		}
		
		if(buttonState == ButtonState.HOVER && getMousePressed() && getMouseButton() == LEFT && !reset && UnderSquare.timeSinceLastClick >= CLICK_DELAY){
			buttonState = ButtonState.CLICKED;
			reset = true;
			onClick();
		}
		
		if(! getMousePressed()){
			reset = false;
		}
		
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
		textAlign(alignment);
		textSize(DEFAULT_TEXT_SIZE);
		text(getText(), pos.x + _rel.x + dim.x/2, pos.y + _rel.y + dim.y/4 + DEFAULT_TEXT_SIZE);
	}
	
	public void onClick() { onClick.run(); }
	
}
