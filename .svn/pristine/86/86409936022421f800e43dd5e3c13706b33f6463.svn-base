package map.blocks.base;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import accessory.CLog;

import map.MapObject;
import map.RasterCell;

public class ImageBasedBlock extends MapObject {

	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = -4881956455759663960L;

	protected static final String ENTERABLE_ID = "enterable";
	
	protected transient BufferedImage img;
	protected String imageSource;
	protected boolean enterable; 
	protected boolean resize;
	
	
	public ImageBasedBlock(String imageSource) {
		this(imageSource, false,true);
	}
	
	public ImageBasedBlock(String imageSource, boolean enterable){
		this(imageSource,enterable,true);
	}
	
	public ImageBasedBlock(String imageSource, boolean enterable,boolean resize) {
		this.imageSource = imageSource;
		this.enterable = enterable;
		this.resize = resize;
		try {
			img = ImageIO.read(new File(imageSource));
		} catch (IOException e) {
			CLog.error("Error while reading image: " + imageSource);
			e.printStackTrace();
		}	
		
		properties.put(ENTERABLE_ID, String.valueOf(enterable));
	}
	
	public ImageBasedBlock(BufferedImage img){
		this.imageSource = "default.png";
	}

	@Override
	public boolean interact(Player actor) {
		return false;
	}

	@Override
	public boolean entered(Player actor) {
		return enterable;
	}
	
	@Override
	public boolean enterable(Player actor) {
		return enterable;
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		if(!resize){
			arg0.drawImage(img, target.getCx()*target.RASTERSIZE, target.getCy()*target.RASTERSIZE, null);
		}
		else {
			arg0.drawImage(img, target.getCx()*target.RASTERSIZE, target.getCy()*target.RASTERSIZE, target.RASTERSIZE, target.RASTERSIZE, null);
		}
		
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
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new ImageBasedBlock(imageSource,enterable,resize);
	}

	@Override
	public String toString() {
		return imageSource;
	}
	
	public void setEnterable(boolean arg){
		enterable = arg;
	}

	@Override
	public void setProperty(String id, String value) {
		if(id.equals(ENTERABLE_ID)){
			try{
				enterable = Boolean.parseBoolean(value);
			}
			catch (Exception e){
				CLog.error("Error while parsing "+value+" into boolean (id:"+ENTERABLE_ID+")");
				CLog.error(e);
				return;
			}
		}
		super.setProperty(id, value);
	}

	public void setResize(boolean arg){
		this.resize = arg;
	}
	
	
}
