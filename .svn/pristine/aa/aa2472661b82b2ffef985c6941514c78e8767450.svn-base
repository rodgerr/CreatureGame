package game.models;

import java.util.HashMap;

import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;

import map.Direction;
import map.blocks.base.ImageBasedBlock;

public abstract class MovementBehaviour {
	
	public final String MODEL_MAP_PROPERTIES_FILE = "ressources/gamedata/movement_model_maps.cres";
	
	public abstract boolean moveLeft();
	public abstract boolean moveRight();
	public abstract boolean moveUp();
	public abstract boolean moveDown();
	
	public HashMap<Direction, ImageBasedBlock> getModelMap() {
		CLog.info("creating Model map for class - "+this.getClass().getSimpleName());
		
		HashMap<Direction, ImageBasedBlock> valueMap = new HashMap<Direction, ImageBasedBlock>();
		try{
			CResReader resReader = new CResReader();
			CResNode valueNode = resReader.readFileIntoMap(MODEL_MAP_PROPERTIES_FILE).get(this.getClass().getSimpleName());
			CLog.info("using values "+valueNode);
			
			valueMap.put(Direction.UP, new ImageBasedBlock(valueNode.getNodeValue(Direction.UP.toString())));
			valueMap.put(Direction.DOWN, new ImageBasedBlock(valueNode.getNodeValue(Direction.DOWN.toString())));
			valueMap.put(Direction.LEFT, new ImageBasedBlock(valueNode.getNodeValue(Direction.LEFT.toString())));
			valueMap.put(Direction.RIGHT, new ImageBasedBlock(valueNode.getNodeValue(Direction.RIGHT.toString())));
			
		}
		catch (Exception e){
			CLog.error("Error while trying to create model map for");
		}
		return valueMap;
	}
	
}
