package map.blocks.base;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import map.MapObject;
import map.RasterCell;

public class DefaultMapObject extends MapObject {
	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 3099243690382550477L;

	public DefaultMapObject(){

	}
	
	public DefaultMapObject(int x, int y){
		this.currentX = x;
		this.currentY = y;
	}


	@Override
	public boolean interact(Player actor) {
		return false;
	}

	@Override
	public boolean entered(Player actor) {
		return true;
	}


	@Override
	public void deseriralize() {
		
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
	
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new DefaultMapObject();
	}

	@Override
	public boolean enterable(Player actor) {
		return true;
	}

	
}
