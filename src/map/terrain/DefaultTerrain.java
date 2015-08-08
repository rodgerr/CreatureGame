package map.terrain;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import map.MapObject;
import map.RasterCell;

public class DefaultTerrain extends MapObject {

	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = -8745881248184393508L;

	@Override
	public boolean interact(Player actor) {
		return false;
	}

	@Override
	public boolean entered(Player actor) {
		return false;
	}

	@Override
	public void deseriralize() {
		
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {

	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new DefaultTerrain();
	}

	@Override
	public boolean enterable(Player actor) {
		return true;
	}

}
