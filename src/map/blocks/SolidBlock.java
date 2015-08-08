package map.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;

import map.MapObject;
import map.RasterCell;

import editor.view.MapOrientation;
import game.models.Player;
import game.models.PlayerEntity;

public class SolidBlock extends MapObject {
	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 2623184271764381287L;
	private Color color;
	
	public SolidBlock(Color color){
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
	public void deseriralize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		arg0.setColor(color);
		arg0.fillRect((target.getCx()*target.RASTERSIZE)+5, (target.getCy()*target.RASTERSIZE)+5, target.RASTERSIZE-10, target.RASTERSIZE-10);
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new SolidBlock(this.color);
	}

	@Override
	public boolean enterable(Player actor) {
		return false;
	}

}
