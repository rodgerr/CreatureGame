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

import accessory.CLog;

import console.view.CommandHandler;

import map.MapObject;
import map.RasterCell;

public class InterstateTeleporter extends Teleporter {

	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 7304421621265927770L;
	
	private static final String MAP_FILE_DEFAULT = "unknown";
	private static final String MAP_FILE_ID = "Map-File";

	public InterstateTeleporter() {
		super();
		properties.put(MAP_FILE_ID, MAP_FILE_DEFAULT);
	}
	
	@Override
	public boolean entered(Player actor) {
		if(properties.get(MAP_FILE_ID).equals(MAP_FILE_DEFAULT)){
			CLog.error("Missing teleport destination! [x:"+getCurrentX()+"/y:"+getCurrentY()+" | Destination:"+properties+"]");
			return false;
		}
		
		CommandHandler handler = new CommandHandler();
		String [] args = new String[2];
		args[0] = CommandHandler.LOAD_MAP_COMMAND;
		args[1] = properties.get(MAP_FILE_ID);
		
		handler.executeCommand(CommandHandler.LOAD_MAP_COMMAND, args);
		return true;
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		float[] dash = { 6, 2 };
		BasicStroke stroke = new BasicStroke( 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,  1, dash, 0 );

		Graphics2D g2 = (Graphics2D) arg0.create();
		
		g2.setColor(Color.MAGENTA.brighter());
		g2.setStroke( stroke );
		g2.draw( new Rectangle2D.Float(target.getCx()*target.RASTERSIZE+4, target.getCy()*target.RASTERSIZE+4,  target.RASTERSIZE-6,  target.RASTERSIZE-6 ) );
		
		g2.setTransform( new AffineTransform() );
		g2.dispose();
		
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new InterstateTeleporter();
	}


	
	
	
}
