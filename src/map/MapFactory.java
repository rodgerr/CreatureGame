package map;

import java.util.ArrayList;
import java.util.HashMap;

import accessory.cres.CResNode;
import accessory.cres.CResReader;

public class MapFactory {
	
	public static final String DEFAULT_VALUES_FILE = "ressources/appdata/map_default_properties.cres";
	public static final String PROPERTY_NODE_NAME = "meta";
	private static CResNode values;
	
	
	public static CResNode createDefaultProperties(){
		
		if(values == null){
			
			CResReader resReader = new CResReader();
			HashMap<String,CResNode> nodes = resReader.readFileIntoMap(DEFAULT_VALUES_FILE);
			values = nodes.get(PROPERTY_NODE_NAME);
			
			if(values == null){
				values = new CResNode();
				values.setName(PROPERTY_NODE_NAME);
				values.addNode(MapEntity.SPAWNPOINT_X, "0");
				values.addNode(MapEntity.SPAWNPOINT_Y, "0");
				values.addNode(MapEntity.CLIMATE_NODE, "default");
				values.addNode(MapEntity.NAME_NODE, "unnamed");
			}
		}
				
		return values;
	}
	
}
