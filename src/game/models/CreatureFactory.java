package game.models;

import java.util.HashMap;
import java.util.Map.Entry;

import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;

public class CreatureFactory {

	private static final String TEMPLATE_FILE = "ressources/gamedata/spawn_templates.cres";
	private static final String CREATUREINDEX_FILE = "ressources/gamedata/creature_index.cres";
	public static final String DEFAULT_SPRITE = "ressources/sprites/creature/default.png";

	static CResReader resReader;
	static HashMap<String, HashMap<Integer, String>> templates;
	static HashMap<String, CResNode> creatureIndex;
	static HashMap<String, HashMap<String,CResNode>> creatureData;
	
	public CreatureFactory() {
		
	}
	
	
	public static void init() {
		resReader = new CResReader();			
		templates = new HashMap<String, HashMap<Integer, String>>();
		
		HashMap<String, CResNode> raw_templates = resReader.readFileIntoMap(TEMPLATE_FILE);
		
		for(Entry<String, CResNode> entry : raw_templates.entrySet()){
			HashMap<Integer, String> innerMap = new HashMap<Integer, String>();
			int counter = 0;
			
			for(Entry<String, String> nodeEntry : entry.getValue().getNodes().entrySet()){
				counter = counter + Integer.parseInt(nodeEntry.getValue());
				
				innerMap.put(counter,nodeEntry.getKey());
			}
		
			templates.put(entry.getKey(), innerMap);
		}
	}
	
	public static void initCreatureIndex(){
		creatureIndex = new HashMap<String, CResNode>();
		
		for(CResNode node : resReader.readFile(CREATUREINDEX_FILE)){
			
			for(Entry<String, String> entry : node.getNodes().entrySet()){
				CLog.info("loading creature file: '"+entry.getValue()+"' ("+entry.getKey()+")");
				
				if(creatureData == null){
					creatureData = new HashMap<String, HashMap<String,CResNode>>();
				}
				creatureData.put(entry.getKey(), resReader.readFileIntoMap(entry.getValue()));
			}
			
		}
	}
	
	
	
	public static Creature spawnBytemplate(String templateName) throws Exception{
		if(resReader == null){
			init();
		}
		
		HashMap<Integer, String> workingNode = templates.get(templateName);
		if(workingNode ==  null){
			throw new Exception("template " +templateName+ " not specified");
		}

		int randomNumber = (int) (Math.random()*100);
		
		for(Entry<Integer, String> entry : workingNode.entrySet()){
			CLog.info("entry:"+entry.getKey()+" |diceroll:" +randomNumber);
			if(entry.getKey() >= randomNumber){
				return createBaseCreature(entry.getValue());
			}
		}
				
		return null;
	}


	public static Creature createBaseCreature(String ident) throws Exception {
		CLog.info("Create creature:"+ident);
		
		if(resReader == null){
			init();
		}
		if(creatureIndex == null){
			initCreatureIndex();
		}
		
		HashMap<String,CResNode> data = creatureData.get(ident);
		if(data == null){
			throw new Exception("creature " +ident+ " not specified");
		}
			
		CResNode metaNode = data.get("meta");
		CResNode baseNode = data.get("base");
		CResNode portraitNode = data.get("portrait");
		
		
		//meta
		Creature crt = new Creature();
		crt.setName(metaNode.getNodeValue("name"));
		crt.setSprite(metaNode.getNodeValue("sprite"));
		
		//base
		crt.setBaseHP(Integer.parseInt(baseNode.getNodeValue("hp")));
		//crt.setCurrentHP(Integer.parseInt(baseNode.getNodeValue("hp")));
		crt.setCurrentHP((int) (crt.getBaseHP()*Math.random()));
		
		crt.setBaseMP(Integer.parseInt(baseNode.getNodeValue("mp")));
		crt.setCurrentMP((int) (crt.getBaseMP()*Math.random()));
		
		if(portraitNode != null){
			crt.setPortraitProperties(portraitNode);
		}
		
		return crt;
	}




}
