package gui;

import java.awt.*;

/**
 * Created by Fabian on 13.12.2015.
 */
public class ListElementPlane {
	private String icao;
	private Color color;

	public ListElementPlane(String icao, Color color){
		this.icao = icao;
		this.color = color;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
