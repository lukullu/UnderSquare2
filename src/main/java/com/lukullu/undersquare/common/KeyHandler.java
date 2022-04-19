package com.lukullu.undersquare.common;

import com.kilix.processing.ProcessingClass;
import com.lukullu.undersquare.UnderSquare;

public class KeyHandler implements ProcessingClass {
	
	public static boolean w;
	public static boolean a;
	public static boolean s;
	public static boolean d;
	public static boolean e;
	public static boolean q;
	public static boolean x;
	public static boolean c;
	
	public static boolean shift  = false;
	public static boolean ctrl   = false;
	public static boolean up     = false;
	public static boolean down   = false;
	public static boolean left   = false;
	public static boolean right  = false;
	public static boolean escape = false;

	public static boolean wR;
	public static boolean aR;
	public static boolean sR;
	public static boolean dR;
	public static boolean eR;
	public static boolean qR;
	public static boolean xR;
	public static boolean cR;

	public static boolean shiftR  = false;
	public static boolean ctrlR   = false;
	public static boolean upR     = false;
	public static boolean downR   = false;
	public static boolean leftR   = false;
	public static boolean rightR  = false;
	public static boolean escapeR = false;

	public static char lastPressedKey;
	
	public static void updatePressed() {
		
		lastPressedKey = UnderSquare.INSTANCE.key;
		
		w = checkKeyPressed("w");
		a = checkKeyPressed("a");
		s = checkKeyPressed("s");
		d = checkKeyPressed("d");
		e = checkKeyPressed("e");
		q = checkKeyPressed("q");
		x = checkKeyPressed("x");
		c = checkKeyPressed("c");
		
		shift  = checkKeyCodePressed(SHIFT);
		up     = checkKeyCodePressed(UP);
		down   = checkKeyCodePressed(DOWN);
		left   = checkKeyCodePressed(LEFT);
		right  = checkKeyCodePressed(RIGHT);
		ctrl   = checkKeyCodePressed(CONTROL);
		escape = checkKeyCodePressed(ESC);
		
	}
	
	public static void updateReleased(){
		
		w = checkKeyReleased("w");
		a = checkKeyReleased("a");
		s = checkKeyReleased("s");
		d = checkKeyReleased("d");
		e = checkKeyReleased("e");
		q = checkKeyReleased("q");
		x = checkKeyReleased("x");
		c = checkKeyReleased("c");
		
		shift  = checkKeyCodeReleased(SHIFT);
		up     = checkKeyCodeReleased(UP);
		down   = checkKeyCodeReleased(DOWN);
		left   = checkKeyCodeReleased(LEFT);
		right  = checkKeyCodeReleased(RIGHT);
		ctrl   = checkKeyCodeReleased(CONTROL);
		escape = checkKeyCodeReleased(ESC);
		
	}
	
	public static boolean checkKeyPressed(String keyDesignator){
		char keyName = keyDesignator.charAt(0);
		boolean old = false;
		old = switch (keyName) {
			case 'w' -> w;
			case 'a' -> a;
			case 's' -> s;
			case 'd' -> d;
			case 'e' -> e;
			case 'q' -> q;
			case 'x' -> x;
			case 'c' -> c;
			default -> false;
		};
		return ("" + UnderSquare.INSTANCE.key).toLowerCase().charAt(0) == keyName || old;
	}
	
	public static boolean checkKeyCodePressed(int keyName){
		boolean old = false;
		old = switch (keyName) {
			case SHIFT   -> shift;
			case UP      -> up;
			case DOWN    -> down;
			case LEFT    -> left;
			case RIGHT   -> right;
			case CONTROL -> ctrl;
			case ESC     -> escape;
			default -> false;
		};

		UnderSquare.INSTANCE.key = 0;

		return UnderSquare.INSTANCE.keyCode == keyName || old;
	}
	
	public static boolean checkKeyReleased(String keyDesignator){
		char keyName = keyDesignator.charAt(0);
		boolean old = false;
		old = switch (keyName) {
			case 'w' -> w;
			case 'a' -> a;
			case 's' -> s;
			case 'd' -> d;
			case 'e' -> e;
			case 'q' -> q;
			case 'x' -> x;
			case 'c' -> c;
			default  -> false;
		};
		return ("" + UnderSquare.INSTANCE.key).toLowerCase().charAt(0) != keyName && old;
	}
	
	public static boolean checkKeyCodeReleased(int keyName){
		boolean old = false;
		old = switch (keyName) {
			case SHIFT   -> shift;
			case UP      -> up;
			case DOWN    -> down;
			case LEFT    -> left;
			case RIGHT   -> right;
			case CONTROL -> ctrl;
			case ESC     -> escape;
			default -> false;
		};
		return UnderSquare.INSTANCE.keyCode != keyName && old;
	}

	public static void setResets(){

		if(w){ wR = false; } else if(!wR){ wR = true; }
		if(a){ aR = false; } else if(!aR){ aR = true; }
		if(s){ sR = false; } else if(!sR){ sR = true; }
		if(d){ dR = false; } else if(!dR){ dR = true; }

		if(e){ eR = false; } else if(!eR){ eR = true; }
		if(q){ qR = false; } else if(!qR){ qR = true; }
		if(x){ xR = false; } else if(!xR){ xR = true; }
		if(c){ cR = false; } else if(!cR){ cR = true; }

		if(shift){ shiftR = false; }   else if(!shiftR){ shiftR = true; }
		if(ctrl){ ctrlR = false; }     else if(!ctrlR){ ctrlR = true; }
		if(escape){ escapeR = false; } else if(!escapeR){ escapeR = true; }

		if(up){ upR = false; }       else if(!upR){ upR = true; }
		if(down){ downR = false; }   else if(!downR){ downR = true; }
		if(left){ leftR = false; }   else if(!leftR){ leftR = true; }
		if(right){ rightR = false; } else if(!rightR){ rightR = true; }

	}

}
