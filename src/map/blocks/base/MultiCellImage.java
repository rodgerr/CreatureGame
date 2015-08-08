package map.blocks.base;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import accessory.CLog;

import editor.view.MapOrientation;

import map.MapObject;
import map.RasterCell;

public class MultiCellImage extends ImageBasedBlock {

	private static final long serialVersionUID = -2937841489581766217L;
	
	private int cellWidth;
	private int cellHeight;
	
	private transient boolean initialized;
	
	public MultiCellImage(String imageSource, int cellWidth, int cellHeight) {
		super(imageSource);

		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}

	public MultiCellImage(String imageSource, boolean enterable, int cellWidth, int cellHeight) {
		super(imageSource, enterable);
		
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}

	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		if(!initialized){
			//TODO: initialized flag setzen
			
			try{
				RasterCell right = target.getNeighbors().get(MapOrientation.RIGHT);
				
				for(int i = 0; i < cellWidth-1; i++){
					right.setAttachedObject(new MultiCellChild(this));
					right.setTerrain(new MultiCellChild(this));
					
					RasterCell bot = right.getNeighbors().get(MapOrientation.BOTTOM);
					for(int j = 0; j < cellHeight-1; j++){
						bot.setAttachedObject(new MultiCellChild(this));
						bot.setTerrain(new MultiCellChild(this));
						
						bot = bot.getNeighbors().get(MapOrientation.BOTTOM);
						if(bot == null){
							break;
						}
					}
					
					right = right.getNeighbors().get(MapOrientation.RIGHT);
					if(right == null){
						break;
					}
					
				
				}
				
				RasterCell bot = target.getNeighbors().get(MapOrientation.BOTTOM);
				bot.setAttachedObject(new MultiCellChild(this));
				bot.setTerrain(new MultiCellChild(this));
			} 
			catch (Exception e){
				CLog.error("Error while creating cells!"+e.getLocalizedMessage());
				CLog.error("Destroying Object "+hashCode()+", replacing with "+DefaultMapObject.class.getSimpleName());
				target.setAttachedObject(new DefaultMapObject());
			}
			
			initialized = true;
		}
		arg0.drawImage(img, target.getCx()*target.RASTERSIZE, target.getCy()*target.RASTERSIZE, cellWidth*target.RASTERSIZE, cellHeight*target.RASTERSIZE, null);
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new MultiCellImage(imageSource, enterable, cellWidth, cellHeight);
	}

	@Override
	public Dimension getObjectDimension() {
		return new Dimension(cellWidth, cellHeight);
	}
	
	
}
