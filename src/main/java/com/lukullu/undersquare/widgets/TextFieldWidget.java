package com.lukullu.undersquare.widgets;

import com.lukullu.undersquare.UnderSquare;
import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.widgets.button.ButtonState;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.collision.Collision.pointRectangleCollision;

public class TextFieldWidget extends TextWidget{


    //TODO: deselect!!!

    public boolean active = false;
    public boolean reset = false;
    public ButtonState buttonState;

    public TextFieldWidget(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, String _text, int _textSize, int _alignment) {
        super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, _text, _textSize, _alignment);
    }


    public boolean resetKey = false;
    public int lastKeyCode = 0;

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
            active = true;
        }

        if(!getMousePressed()){
            reset = false;
        }

        if(active){

            if(getKeyPressed()) {
                if (getKey() == BACKSPACE && getKey() != lastKeyCode && resetKey) {
                    if (getText().length() > 0) {
                        setText(getText().substring(0, getText().length() - 1));
                    }
                    resetKey = false;
                } else if (getKey() >= 'A' && getKey() <= 'z' && resetKey) {

                    setText(getText() + getKey());
                    resetKey = false;

                }

            }else{

                resetKey = true;

            }

        }

    }

    @Override
    public void paint(Vector2 _rel) {

        noStroke();

        if(active){fill(UI_CONTRAST_COLOR.brighter().getRGB()); }else fill(UI_CONTRAST_COLOR.getRGB());
        rect(pos.x + _rel.x, pos.y + _rel.y, dim.x, dim.y, cornerTL, cornerTR, cornerBR, cornerBL);
        fill(UI_TEXT_COLOR.getRGB());
        textSize(textSize);
        textAlign(alignment,CENTER);
        if(alignment == CENTER){text(getText(), pos.x+_rel.x+dim.x/2f,pos.y + _rel.y + dim.y/2f);}
        else {text(getText(), pos.x+_rel.x+dim.x/10f,pos.y + _rel.y + dim.y/2f);}

    }

}
