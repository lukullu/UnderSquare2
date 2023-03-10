package com.lukullu.undersquare.common.data;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Objects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class Vector2 implements Serializable {
	
	public Vector2(Number _x, Number _y) { x = _x.floatValue(); y = _y.floatValue(); }
	
	public float x, y;
	
	public String toString() { return String.format("Vec2(%.2f, %.2f)", x, y); }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector2 vector2 = (Vector2) o;
		return Float.compare(vector2.x, x) == 0 && Float.compare(vector2.y, y) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	public static Vector2 toVector2(float h) {
		float s = (float) Math.cos(45)/h;
		
		return new Vector2(s,s);
	}
	
	public static Vector2 calcAngleAndValueFromVector(Vector2 v) {
		
		return new Vector2(Math.atan2(v.y,v.x), Math.sqrt(Math.pow(v.x,2) + Math.pow(v.y,2)));
		
	}
	
	public static Vector2 unitVector2(Vector2 v){
		
		float len = (float) Math.sqrt(Math.pow(v.x,2) + Math.pow(v.y,2));
		
		return new Vector2(v.x/len, v.y/len);
		
	}
	
	public static Vector2 calcVectorFromAngleAndValue(Vector2 v) {
		
		return new Vector2(v.y * Math.cos(v.x), v.y * Math.sin(v.x));
		
	}
	
	public static Vector2 capVector(Vector2 v, float maxValue) {
		
		Vector2 output;
		Vector2 anv = calcAngleAndValueFromVector(v);
		
		if(anv.y <= maxValue){
			
			output = v;
			
		}else{
			
			output = new Vector2(calcVectorFromAngleAndValue(v).x,maxValue);
			
		}
		
		return output;
		
	}
	
	public static class Deserializer implements JsonDeserializer<Vector2>
	{

		@Override
		public Vector2 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException 
		{

			String sJson = json.getAsString();
			sJson = sJson.substring("Vec2(".length(), sJson.length() - 1);
			String[] values = sJson.split(", ");

			return new Vector2(Float.parseFloat(values[0]), Float.parseFloat(values[1]));

		}
		
	}
	
}