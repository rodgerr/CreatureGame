package map.templates;

import java.util.HashMap;

import accessory.CLog;
import accessory.cres.CResNode;

import map.MapObject;
import map.SurfaceLevel;
import map.blocks.base.ImageBasedBlock;
import map.terrain.BasicTerrain;
import map.terrain.IceTerain;
import map.terrain.LiquidEnterableTerraint;

public class BasicTerrainTemplate extends BaseTemplate {

	@Override
	public MapObject create(CResNode arg0)  {
		HashMap<String,String> args = arg0.getNodes();
		CLog.info(this.getClass().getSimpleName()+" creating object based on"+args);
		
		
		MapObject val = null;
		
		String sprite = args.get(BaseTemplate.NODE_SPRITE);
		if(valueNotNull(sprite, NODE_SPRITE)){
			
			if(valueNotNull(args.get(BaseTemplate.NODE_SURFACE), BaseTemplate.NODE_SURFACE)){
				//if surface == water, we use the water class so we can surf on it
				if(args.get(BaseTemplate.NODE_SURFACE).equalsIgnoreCase(SurfaceLevel.WATER.toString())){
					CLog.info(BaseTemplate.NODE_SURFACE+" equals "+ SurfaceLevel.WATER.toString()+" - create "+ LiquidEnterableTerraint.class.getSimpleName());
					return new LiquidEnterableTerraint(sprite,SurfaceLevel.WATER);
				}
				else if(args.get(BaseTemplate.NODE_SURFACE).equalsIgnoreCase(SurfaceLevel.ICE.toString())){
					CLog.info(BaseTemplate.NODE_SURFACE+" equals "+ SurfaceLevel.ICE.toString()+" - create "+ IceTerain.class.getSimpleName());
					return new IceTerain(sprite, SurfaceLevel.GROUND);
				}
				else {
					return new BasicTerrain(sprite);
				}
				
			}
			else {
				return new BasicTerrain(sprite);
			}
		}
		
		
		
		
		return val;
	}

}
