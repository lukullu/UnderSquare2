package com.lukullu.undersquare.widgets;

import com.lukullu.undersquare.common.data.Vector2;
import com.lukullu.undersquare.common.msc.Utils;

import java.util.ArrayList;

import static com.lukullu.undersquare.common.Constants.*;
import static com.lukullu.undersquare.common.msc.Translation.scaleToScreenY;

public class ScrollWidget extends Widget{

	public static final int LINE_DISTANCE = 2;
	public Widget LINE_PARTER = new Widget(new Vector2(dim.x/10f, 0),new Vector2(dim.x - dim.x/5,scaleToScreenY(2)),ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,ROUNDEDCORNERS,UI_FOCUS_COLOR);

	public ArrayList<Widget> widgets = new ArrayList<>();
	public Widget title;
	public int totalContentHeight = 0;
	String name = "";

	public static final int WIDGET_DISTANCE = 4;

	public int scrollPosition = 0;
	
	public ScrollWidget(Vector2 _pos, Vector2 _dim, int _cornerBL, int _cornerBR, int _cornerTL, int _cornerTR, String _name) {

		super(_pos, _dim, _cornerBL, _cornerBR, _cornerTL, _cornerTR, UI_CONTRAST_COLOR);
		name = _name;

		title = new TextWidget(
				pos,
				new Vector2(
						dim.x,
						scaleToScreenY(80)),
				0,0, _cornerTL, cornerTR,
				name, 24,
				CORNER);

	}
	
	// add/remove widgets to/from the list
	public void addWidget(Widget _toAdd)       { widgets.add(LINE_PARTER); widgets.add(_toAdd); updateContentHeight();}
	public void removeWidget(Widget _toRemove) { widgets.remove(_toRemove); }
	public void removeWidget(int _index)       { widgets.remove(_index); }
	public void clearWidgets() { widgets = new ArrayList<>();}

	public void updateContentHeight(){
		totalContentHeight = 0;
		for(int i = 0; i < widgets.size(); i++) {
			Widget cur = widgets.get(i);

			totalContentHeight += cur.dim.y + scaleToScreenY(WIDGET_DISTANCE);
		}
	}

	// programmatically set scroll position
	public void setScrollPosition(int _scrollPosition) { scrollPosition = _scrollPosition; }

	@Override
	public void update(Vector2 _rel) {
		int contentHeight = 0;

		for(int i = 0; i < widgets.size(); i++) {
			Widget cur = widgets.get(i);

			// top overflow
			if (scrollPosition > contentHeight) { contentHeight += cur.dim.y + scaleToScreenY(LINE_DISTANCE); continue; }
			// bottom overflow
			if (contentHeight + scaleToScreenY(WIDGET_DISTANCE) + cur.dim.y - scrollPosition > dim.y - cur.dim.y){println("break!" + " | "+i ); break;}//TODO this doesn't work yet!!! -------------------------------
			
			cur.update(new Vector2(
							pos.x + _rel.x,
							pos.y + _rel.y + contentHeight + scaleToScreenY(WIDGET_DISTANCE) - scrollPosition +  title.dim.y
					)
			);
			
			contentHeight += cur.dim.y + scaleToScreenY(WIDGET_DISTANCE);
		}


		scrollPosition = Utils.clamp(0,(int)Math.max(totalContentHeight - dim.y,0), scrollPosition);
	}
	
	@Override
	public void paint(Vector2 _rel){

		title.paint();

		noStroke();
		fill(UI_CONTRAST_COLOR.getRGB());
		rect(pos.x, pos.y + title.dim.y, dim.x, dim.y - title.dim.y);

		int contentHeight = 0;
		
		for(int i = 0; i < widgets.size(); i++){
			
			Widget cur = widgets.get(i);

			if (scrollPosition > contentHeight) { contentHeight += cur.dim.y + scaleToScreenY(LINE_DISTANCE); continue; }

			if (contentHeight + scaleToScreenY(WIDGET_DISTANCE) - scrollPosition > dim.y - title.dim.y) break;
			
			cur.paint(new Vector2(
							pos.x,
							pos.y + contentHeight + scaleToScreenY(WIDGET_DISTANCE) - scrollPosition +  title.dim.y
					)
			);

			contentHeight += cur.dim.y + scaleToScreenY(WIDGET_DISTANCE);
		}

	}
	
}