package com.lukullu.undersquare.widgets;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.common.data.Vector2;
import java.awt.*;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.msc.Translation.*;

public class LegendWidget extends  TextWidget implements ProcessingClass {

    Color color;

    public LegendWidget(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, String _text, int _textSize, Color _color) {
        super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, _text, _textSize, CORNER); color = _color;
    }


    public void paint(Vector2 _rel){

        fill(UI_CONTRAST_COLOR.getRGB());
        stroke(color.getRGB());
        rect(pos.x + _rel.x, pos.y + _rel.y, dim.x, dim.y, cornerTL, cornerTR, cornerBR, cornerBL);
        textSize(textSize);
        fill(UI_TEXT_COLOR.getRGB());
        textAlign(alignment,CENTER);
        text(getText(), pos.x+_rel.x+dim.x/10f + scaleToScreenX(20),pos.y + _rel.y + dim.y/2f);
        fill(color.getRGB());
        rect(pos.x + _rel.x, pos.y + _rel.y, scaleToScreenX(20), dim.y, cornerTL, 0, 0, cornerBL);

    }

}
