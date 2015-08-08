package map.terrain;

import game.models.Player;

import java.awt.event.MouseEvent;

import map.Direction;
import map.MapObject;
import map.RasterCell;
import map.SurfaceLevel;

public class IceTerain extends BasicTerrain {

	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 5824326928689863787L;

	public IceTerain(String imageSource) {
		super(imageSource);
	}

	public IceTerain(String imageSource, SurfaceLevel level) {
		super(imageSource, level);
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new IceTerain(imageSource,surfaceLevel);
	}

	@Override
	public boolean entered(Player actor) {
		System.out.println("test");
		System.out.println(actor.getOrientation());
		Direction direction =  actor.getOrientation();
		
		switch (direction) {

		case UP:	actor.moveUp();			
					break;

		case DOWN:	actor.moveDown();
					break;

		case LEFT:	actor.moveLeft();
					break;
			
		case RIGHT:	actor.moveRight();
					break;

		}
		return true;
	}	
	

}
