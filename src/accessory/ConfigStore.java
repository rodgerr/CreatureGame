package accessory;

import accessory.cres.CResNode;
import accessory.cres.CResReader;

public class ConfigStore {
	private static String META_FILE = "ressources/appdata/meta.cres";
	
	private static CResNode metaNode = null;
	
	public ConfigStore() {
	}
	
	public static String getMetaValue(String nodeName){
		if(metaNode == null){
			CResReader reader = new CResReader();
			metaNode = reader.readFile(META_FILE).get(0);			
		}
		return metaNode.getNodeValue(nodeName);
	}

}
