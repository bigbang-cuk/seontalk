package seontalk.util;

import java.awt.Color;

public class Theme {
	//테마이름
	public final static String BASIC   = "basic";
	public final static String BLACK   = "black";
	public final static String LIME    = "lime";
	public final static String SKYBULE = "skyblue";
	Color font_color;
	Color background_color;
	Color inner_color;
	Color line_color;
	public Color setFontColor(String theme) {
		if(BLACK.equals(theme)) {
			font_color = Color.LIGHT_GRAY;
		}
		else {
			font_color = Color.BLACK;
		}
		return font_color;
	}
	public Color setBackgroundColor(String theme) {
		if(BASIC.equals(theme)) {
			background_color = new Color(255,224,200);
		}
		else if(BLACK.equals(theme)) {
			background_color = Color.BLACK;
		}
		else if(LIME.equals(theme)) {
			background_color = new Color(191,255,0);
		}
		else if(SKYBULE.equals(theme)) {
			background_color = new Color(135,206,235);
		}
		return background_color;
	}
	public Color setInnerColor(String theme) {
		if(BASIC.equals(theme)) {
			inner_color = new Color(231,164,100);
		}
		else if(BLACK.equals(theme)) {
			inner_color = Color.LIGHT_GRAY;
		}
		else if(LIME.equals(theme)) {
			inner_color = Color.GREEN;
		}
		else if(SKYBULE.equals(theme)) {
			inner_color = Color.CYAN;
		}
		return inner_color;
	}
	public Color setLineColor(String theme) {
		if(BLACK.equals(theme)) {
			line_color = Color.WHITE;
		}
		else {
			line_color = Color.BLACK;
		}
		return line_color;
	}
}
