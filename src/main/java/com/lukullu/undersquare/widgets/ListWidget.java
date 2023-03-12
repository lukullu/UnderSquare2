package com.lukullu.undersquare.widgets;

import com.lukullu.undersquare.common.data.Vector2;

import java.util.ArrayList;

import static com.lukullu.undersquare.common.Constants.UI_CONTRAST_COLOR;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class ListWidget extends Widget{

    public ArrayList<Widget> widgets = new ArrayList<>();

    public ListWidget(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR){
        super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, UI_CONTRAST_COLOR);
    }

    @Override
    public void update(Vector2 _rel) {

        float contentHeight = 0;
        for(int i = 0; i < widgets.size(); i++){

            Widget cur = widgets.get(i);

            if (contentHeight + scaleToScreenY(10)  > dim.y) break;

            cur.update(new Vector2(pos.x, pos.y + contentHeight + scaleToScreenY(10)));

            contentHeight += cur.dim.y + scaleToScreenY(10);
        }

    }

    @Override
    public void paint(Vector2 _rel) {

        noStroke();
        fill(UI_CONTRAST_COLOR.getRGB());
        rect(pos.x, pos.y, dim.x, dim.y, cornerTL, cornerTR, cornerBR, cornerBL);


        float contentHeight = 0;
        for(int i = 0; i < widgets.size(); i++){

            Widget cur = widgets.get(i);

            if (contentHeight + scaleToScreenY(10)  > dim.y) break;

            cur.paint(new Vector2(pos.x, pos.y + contentHeight + scaleToScreenY(10)));

            contentHeight += cur.dim.y + scaleToScreenY(10);
        }

    }

    public void reset()
    {
        widgets = new ArrayList<>();
    }

}
