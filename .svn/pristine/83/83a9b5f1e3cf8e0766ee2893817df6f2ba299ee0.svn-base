package accessory.crtm;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;
import accessory.cres.CResWriter;

import editor.view.MapOrientation;
import editor.view.mousehandling.MouseOverHandler;

import map.MapEntity;
import map.MapFactory;
import map.RasterCell;

public class FileHandler {
	
	public MapEntity readMapFile(String fileName, MouseOverHandler handler) throws InvalidClassException{
		CLog.info("Reading map file '"+fileName+"'");
		RasterCell[][] stock = null;
		
		try {

			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream o = new ObjectInputStream(file);
			Dimension dim = (Dimension) o.readObject();
			CLog.info("map size: "+dim);

			stock = new RasterCell[new Double(dim.getWidth()).intValue()][new Double(dim.getHeight()).intValue()];

			for (int i = 0; i < dim.getWidth(); i++) {
				for (int j = 0; j < dim.getHeight(); j++) {
					RasterCell cell = (RasterCell) o.readObject();
					stock[i][j] = cell;
					if(handler != null){
						stock[i][j].addMouseOverHandler(handler);
					}
					stock[i][j].getAttachedObject().deseriralize();
					stock[i][j].getTerrain().deseriralize();										
				}
			}
			
			for (int i = 0; i < dim.getWidth(); i++) {
				for (int j = 0; j < dim.getHeight(); j++) {
					RasterCell cell = stock[i][j];					
							
					if(i > 0){
						stock[i - 1][j].getNeighbors().put(MapOrientation.RIGHT, cell);
					}
					if(j > 0){
						stock[i][j - 1].getNeighbors().put(MapOrientation.BOTTOM, cell);
					}		
					if((i + 1) < dim.getWidth()){
						stock[i + 1][j].getNeighbors().put(MapOrientation.LEFT, cell);
					}
					if((j + 1) < dim.getHeight()){
						stock[i][j + 1].getNeighbors().put(MapOrientation.TOP, cell);
					}				
				}
			}
			
			CLog.info("loaded "+stock.length+" lines");
			o.close();
		}
		catch (InvalidClassException e){
		 throw e;
		} 
		catch (IOException e) {
			System.err.println(e);
		} 
		catch (ClassNotFoundException e) {
			System.err.println(e);
		} 
				
		MapEntity map = new MapEntity(stock);
		
		//check if theres an property file attached to the map
		//file should be in the same directory, same name+res file extension
		String propertyFilePath = fileName+CResWriter.FILE_EXTENSION;
		
		File propertyFile = new File(propertyFilePath);
		if(propertyFile.isFile()) {
			CResReader resReader = new CResReader();
			HashMap<String, CResNode> nodes = resReader.readFileIntoMap(propertyFilePath);
			
			
			//file exists, file is legit if property node is found
			if(nodes.get(MapFactory.PROPERTY_NODE_NAME) != null){
				CLog.info("Maproperties file is legit!");
				map.setProperties(nodes.get(MapFactory.PROPERTY_NODE_NAME));
			}
		}
		
		return map;
	}
	
}
