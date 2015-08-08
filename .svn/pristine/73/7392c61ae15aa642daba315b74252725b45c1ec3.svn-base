package map.templates;

import java.util.HashMap;

import map.MapObject;
import map.blocks.WildGrass;
import accessory.CLog;
import accessory.cres.CResNode;

public class WildGrassTemplate extends BaseTemplate{

	@Override
	public MapObject create(CResNode arg0)  {
		HashMap<String,String> args = arg0.getNodes();
		CLog.info(this.getClass().getSimpleName()+" creating object based on"+args);
		
		WildGrass val = null;
		
		String sprite = args.get(BaseTemplate.NODE_SPRITE);
		if(valueNotNull(sprite, NODE_SPRITE)){
			val = new WildGrass(sprite);
		}
		
		return val;		
	
	}

}
