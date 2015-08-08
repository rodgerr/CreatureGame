package map.terrain;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import map.MapObject;
import map.RasterCell;
import map.SurfaceLevel;

public class DirectionRenderedTerrain extends BasicTerrain {

	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = -2481775582601135317L;

	public DirectionRenderedTerrain(String imageSource, SurfaceLevel lowSurface, SurfaceLevel level) {
		super(imageSource, level);

	}

	
	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new BasicTerrain(this.imageSource);
	}

	@Override
	public boolean enterable(Player actor) {
		return true;
	}

	@Override
	public SurfaceLevel getSurfaceLevel() {
		return surfaceLevel;
	}
	
	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		arg0.drawImage(img, target.getCx()*target.RASTERSIZE, target.getCy()*target.RASTERSIZE, null);
	}
	
}
