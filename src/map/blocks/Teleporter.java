package map.blocks;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import map.MapObject;
import map.RasterCell;

public class Teleporter extends MapObject {
	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 7924020560993911464L;

	public Teleporter(){
		properties.put("targetX", "20");
		properties.put("targetY", "20");
	}

	@Override
	public boolean interact(Player actor) {
		return false;
	}

	@Override
	public boolean entered(Player actor) {
		int targetX = Integer.parseInt(properties.get("targetX"));
		int targetY = Integer.parseInt(properties.get("targetY"));
		
		System.out.println(targetX+"/"+targetY+ " - teleport activated");
		
		actor.setPositionX(targetX);
		actor.setPositionY(targetY);
		return true;
	}


	@Override
	public void deseriralize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		
		float[] dash = { 6, 2 };
		BasicStroke stroke = new BasicStroke( 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,  1, dash, 0 );

		Graphics2D g2 = (Graphics2D) arg0.create();
		
		g2.setColor(Color.RED.brighter());
		g2.setStroke( stroke );
		g2.draw( new Rectangle2D.Float(target.getCx()*target.RASTERSIZE+4, target.getCy()*target.RASTERSIZE+4,  target.RASTERSIZE-6,  target.RASTERSIZE-6 ) );
		
		g2.setTransform( new AffineTransform() );
		g2.dispose();
		
//		arg0.setColor(Color.BLUE);
//		arg0.drawRect(target.getCx()*target.RASTERSIZE+3, target.getCy()*target.RASTERSIZE+3, target.RASTERSIZE-6,  target.RASTERSIZE-6);
		
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new Teleporter();
	}

	@Override
	public boolean enterable(Player actor) {
		return true;
	}

}
