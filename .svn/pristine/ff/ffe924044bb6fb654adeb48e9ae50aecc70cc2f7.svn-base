package map.templates;

import java.util.HashMap;

import accessory.CLog;
import accessory.cres.CResNode;

import map.MapObject;
import map.blocks.base.ImageBasedBlock;
import map.blocks.base.MultiCellImage;

public class ImageBasedBlockTemplate extends BaseTemplate{

	@Override
	public MapObject create(CResNode arg0)  {
		HashMap<String,String> args = arg0.getNodes();
		CLog.info(this.getClass().getSimpleName()+" creating object based on"+args);
		
		ImageBasedBlock val = null;
		
		String sprite = args.get(BaseTemplate.NODE_SPRITE);
		if(valueNotNull(sprite, NODE_SPRITE)){
			
			if(	valueNotNull(args.get(BaseTemplate.NODE_CELLS_HEIGHT), BaseTemplate.NODE_CELLS_HEIGHT) && 
				valueNotNull(args.get(BaseTemplate.NODE_CELLS_WIDTH), BaseTemplate.NODE_CELLS_WIDTH) ){
					
				val = new MultiCellImage(sprite, Integer.parseInt(args.get(BaseTemplate.NODE_CELLS_WIDTH)), Integer.parseInt(args.get(BaseTemplate.NODE_CELLS_HEIGHT)));
			}
			else{
				val = new ImageBasedBlock(sprite);
			}
			
		}
		//sprite is minimum requirement to create a image block, cant create block if no sprite is given
		else {
			CLog.error("Cant create object "+arg0.getName()+" because '"+NODE_SPRITE+"' value is missing");
			return null;
		}
		
		if(args.get(NODE_ENTERABLE) != null && !args.get(NODE_ENTERABLE).isEmpty()){
			try {
				val.setEnterable(Boolean.parseBoolean(args.get(NODE_ENTERABLE)));
			}
			catch (Exception e){
				CLog.error("wrong value used in '"+NODE_ENTERABLE+"' "+args.get(NODE_ENTERABLE));
			}
		}
		
		if(valueNotNull(args.get(NODE_RESIZE), NODE_RESIZE)){
			try{
				val.setResize(Boolean.parseBoolean(args.get(NODE_RESIZE)));
			}
			catch(Exception e){
				CLog.error("wrong input used for entry '"+NODE_RESIZE+"'! Value:"+args.get(NODE_RESIZE)+"Expected true/false");
			}
		}
		
		CLog.info("Object:" +val.hashCode() +" ("+val.getClass().getSimpleName()+") ");
		return val;		
	}

}

