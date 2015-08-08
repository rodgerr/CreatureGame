package map.templates;

import java.util.HashMap;

import accessory.CLog;
import accessory.cres.CResNode;

import map.MapObject;

public abstract class BaseTemplate {
	
	public static final String NODE_SPRITE = "sprite";
	public static final String NODE_TEMPLATE = "template";
	public static final String NODE_ENTERABLE = "enterable";
	public static final String NODE_RESIZE = "resize";
	public static final String NODE_SURFACE = "surface";
	public static final String NODE_CELLS_WIDTH = "cells_width";
	public static final String NODE_CELLS_HEIGHT = "cells_height";
	
	public abstract MapObject create(CResNode arg0);
	
	public boolean valueNotNull(String arg, String node){
		if(arg == null || arg.isEmpty()){
			CLog.error("missing value:" + node +" in list ");
			return false;
		}
		CLog.info("using value:'" +arg+ "' as '"+ node +"'");
		return true;
	}

}
