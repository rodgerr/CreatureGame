package map.blocks.base;

import game.models.Player;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import map.MapObject;
import map.RasterCell;

public class MultiCellChild extends MapObject {

	private static final long serialVersionUID = -9039277892893770435L;
	
	private MapObject parent; 
	
	public MultiCellChild(MapObject parent) {
		this.parent  = parent;
	}

	@Override
	public boolean interact(Player actor) {
		return parent.interact(actor);
	}

	@Override
	public boolean entered(Player actor) {
		// TODO Auto-generated method stub
		return parent.interact(actor);
	}

	@Override
	public boolean enterable(Player actor) {
		// TODO Auto-generated method stub
		return parent.interact(actor);
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deseriralize() {

	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		// TODO Auto-generated method stub
		return new MultiCellChild(parent);
	}

}
