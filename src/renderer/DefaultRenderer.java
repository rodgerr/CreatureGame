package renderer;

import java.awt.Graphics;

import map.RasterCell;


public class DefaultRenderer implements MapRenderer{

	@Override
	public void renderCell(RasterCell source, boolean border, Graphics g) {
		source.paintComponent(g, border);
	}


	
	
}
