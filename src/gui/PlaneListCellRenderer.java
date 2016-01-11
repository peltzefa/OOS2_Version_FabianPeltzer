package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Fabian on 13.12.2015.
 */
public class PlaneListCellRenderer extends JLabel implements ListCellRenderer {


	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean
			cellHasFocus) {

		ListElementPlane plane = (ListElementPlane) value;
		this.setText(plane.getIcao());
		this.setOpaque(true);
		this.setForeground(plane.getColor());
		if (isSelected){
			this.setBackground(Color.blue);
		} else {
			this.setBackground(Color.white);
		}

		return this;
	}
}
