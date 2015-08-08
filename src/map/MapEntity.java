package map;

import java.awt.Rectangle;

import accessory.CLog;
import accessory.cres.CResNode;

public class MapEntity {

	public static final String SPAWNPOINT_Y = "SPAWNPOINT_Y";
	public static final String SPAWNPOINT_X = "SPAWNPOINT_X";
	public static final String CLIMATE_NODE = "climate";
	public static final String NAME_NODE = "name";
	
	RasterCell[][] raster;
	CResNode properties;
	
	public MapEntity(){
		super();
		properties = MapFactory.createDefaultProperties();
	}

	public MapEntity(RasterCell[][] raster) {
		super();
		this.raster = raster;
		properties = MapFactory.createDefaultProperties();
	}

	//copy contstructor
	public MapEntity(MapEntity original){
		super();
		CLog.info("Copy map:"+original);
		this.properties = original.getProperties();
		this.raster = original.getRaster();
	}
	
	public RasterCell[][] getRaster() {
		return raster;
	}

	public void setRaster(RasterCell[][] raster) {
		this.raster = raster;
	}

	public CResNode getProperties() {
		return properties;
	}

	public void setProperties(CResNode properties) {
		this.properties = properties;
	}
	
	public Rectangle getSpawnPoint(){
		int x = 0;
		int y = 0;
		
		
		System.out.println(properties);
		
		try{
			x = Integer.parseInt(properties.getNodeValue(SPAWNPOINT_X));
			y = Integer.parseInt(properties.getNodeValue(SPAWNPOINT_Y));
			
			if(x > raster.length || y > raster[0].length){
				throw new Exception("Spawnpoints exceed map bounds! " +
									"x="+x+" width="+raster.length +
									"y="+y+" height="+raster[0].length);
			}
		}
		catch(Exception e){
			CLog.error("Error while parsing spawnpoint! using default values: " +x+"/"+y);
			CLog.error(e);
			
		}
		
		CLog.info("spawnpoint - x="+x+ " y="+y);
		return new Rectangle(x, y);
		
	}
	
}
