package map.blocks;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import map.MapObject;
import map.RasterCell;

public class OverlayInterstateTeleporter extends InterstateTeleporter {

	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 2140822125835316767L;
	
	MapObject parentCell;
	
	public OverlayInterstateTeleporter(MapObject parent){
		parentCell = parent;
	}
	
	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		if(parentCell != null){
			parentCell.paintComponent(arg0, target);
		}
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new OverlayInterstateTeleporter(cell.getAttachedObject());		
	}

	@Override
	public void deseriralize() {
		parentCell.deseriralize();
	}

	
	
}
