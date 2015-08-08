package renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import map.RasterCell;

public class PropertyRenderer implements MapRenderer {

	@Override
	public void renderCell(RasterCell source, boolean border, Graphics g) {
		int cx = source.getCx();
		int cy = source.getCy();
		
		

		
		if(border){
			g.setColor(Color.BLACK);
			g.drawRect(cx*RasterCell.RASTERSIZE, cy*RasterCell.RASTERSIZE, RasterCell.RASTERSIZE, RasterCell.RASTERSIZE);
		}
		
		Font font = new Font("Serif", Font.PLAIN, 10);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(cx+"/"+cy, cx*RasterCell.RASTERSIZE+2, cy*RasterCell.RASTERSIZE+RasterCell.RASTERSIZE-2);
	}

}
