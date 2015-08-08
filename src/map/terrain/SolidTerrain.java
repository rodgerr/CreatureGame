package map.terrain;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import map.MapObject;
import map.RasterCell;

public class SolidTerrain extends MapObject {
	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 450836649511542516L;
	
	private Color color;
	
	public SolidTerrain(Color color){
		this.color = color;
	}

	@Override
	public boolean interact(Player actor) {
		return false;
	}

	@Override
	public boolean entered(Player actor) {
		return false;
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		arg0.setColor(color);
		arg0.fillRect(target.getCx()*target.RASTERSIZE, target.getCy()*target.RASTERSIZE, target.RASTERSIZE, target.RASTERSIZE);
	}

	@Override
	public void deseriralize() {
		
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new SolidTerrain(this.color);
	}

	@Override
	public boolean enterable(Player actor) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
