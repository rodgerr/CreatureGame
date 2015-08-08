package map;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;

import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;
import accessory.crtm.FileHandler;

public class MapStore {

	public static final String FILES_NODE = "files";
	private static final String MAPCFG_FILE = "ressources/gamedata/mapfiles.cres";

	private static HashMap<String,CResNode> mapNodes;
	private static HashMap<String, MapEntity> maps;
	
	
	public static MapEntity getMap(String id){
		if(mapNodes == null){
			CLog.info("\nLoading Map indecies...");
			CResReader resReader = new CResReader();
			mapNodes = resReader.readFileIntoMap(MAPCFG_FILE);		
		}
		return getMapFromFile(mapNodes.get(FILES_NODE).getNodeValue(id));
	}
	
	public static MapEntity getMapFromFile(String path){
		CLog.info("\ngetting Map "+path);
		if(maps == null){
			maps = new HashMap<String, MapEntity>();
		}
		
		if(maps.get(path) == null){
			FileHandler fhandler = new FileHandler();		
			try {		
				maps.put(path,fhandler.readMapFile(path, null));
			}	
			catch (InvalidClassException e){
				CLog.error("Incompatible Block version found! Exiting after error message");
				CLog.error(e);
				System.exit(0);
				return null;
			}
		}
		
		return maps.get(path);
		
	}

}
