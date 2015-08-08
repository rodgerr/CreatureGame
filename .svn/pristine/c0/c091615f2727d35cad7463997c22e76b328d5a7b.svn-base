package renderer;

import java.awt.Color;
import java.awt.Graphics;

import editor.model.PlayerDummy;

import map.RasterCell;
			 
public class CollisionRenderer implements MapRenderer {

	@Override
	public void renderCell(RasterCell source, boolean border, Graphics g) {
		if(source.getAttachedObject().enterable(new PlayerDummy())){
			g.setColor(Color.GREEN.brighter());
		}
		else{
			g.setColor(Color.RED);
		}
		
		int cx = source.getCx();
		int cy = source.getCy();
		g.fillRect(cx*RasterCell.RASTERSIZE, cy*RasterCell.RASTERSIZE, RasterCell.RASTERSIZE, RasterCell.RASTERSIZE);
		
		if(border){
			g.setColor(Color.BLACK);
			g.drawRect(cx*RasterCell.RASTERSIZE, cy*RasterCell.RASTERSIZE, RasterCell.RASTERSIZE, RasterCell.RASTERSIZE);
		}
	}

}
