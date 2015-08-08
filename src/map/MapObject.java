package map;

import game.models.Player;
import game.models.PlayerEntity;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.HashMap;


public abstract class MapObject implements Serializable{
	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = 1789218770835645530L;
	
	protected int currentX;
	protected int currentY;

	protected HashMap<String, String> properties;
	
	public MapObject(){
		properties = new HashMap<String, String>();
	}
	
	public abstract boolean interact(Player actor);
	public abstract boolean entered(Player actor);
	public abstract boolean enterable(Player actor);
	public abstract void paintComponent(Graphics arg0, RasterCell target);
	/** deserialize wird aufgerufen wenn das object nach dem speichern wieder neu hergestellt wird */
	public abstract void deseriralize();
	
	/** mit newInstance werden neue Instanzen kreaiert wenn blöcke auf die map gepackt werden 
	 * @param event 
	 * @param cell */
	public abstract MapObject newInstance(RasterCell cell, MouseEvent event);

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	
	public void setProperties(HashMap<String, String> properties){
		this.properties = properties;
	}
	
	public void setProperty(String id, String value){
		this.properties.put(id, value);
	}
	
	public HashMap<String, String> getProperties(){
		return properties;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" ["+currentX+","+currentY+"]";
	}
	
	public SurfaceLevel getSurfaceLevel(){
		return SurfaceLevel.GROUND;
	}

	public void leaved(PlayerEntity actor){
		
	}

	public Dimension getObjectDimension() {
		return new Dimension(1,1);
	}
}
