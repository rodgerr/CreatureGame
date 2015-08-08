package map.terrain;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import accessory.CLog;

import map.MapObject;
import map.RasterCell;
import map.SurfaceLevel;

public class BasicTerrain extends MapObject {
	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 2428314871402792468L;

	protected transient Image img;
	
	protected String imageSource;
	protected SurfaceLevel surfaceLevel;
	
	public BasicTerrain(String imageSource){
		this(imageSource, SurfaceLevel.GROUND);
	}
	
	public BasicTerrain(String imageSource, SurfaceLevel level){
		this.surfaceLevel = level;
		
		this.imageSource = imageSource;
		try {
			img = ImageIO.read(new File(imageSource));
		} catch (IOException e) {
			CLog.error("Error while reading image: '" + imageSource+"'");
			e.printStackTrace();
		}
	}

	@Override
	public boolean interact(Player actor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean entered(Player actor) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public String toString() {
		return imageSource.replaceAll(".png", "");
	}

	@Override
	public void deseriralize() {
		try {
			img = ImageIO.read(new File(imageSource));
		} catch (IOException e) {
			CLog.error("Error while reading image: " + imageSource);
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		arg0.drawImage(img, target.getCx()*target.RASTERSIZE, target.getCy()*target.RASTERSIZE, null);
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
	
}
