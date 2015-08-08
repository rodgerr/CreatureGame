package map.blocks;

import game.models.Creature;
import game.models.CreatureFactory;
import game.models.Player;
import game.models.PlayerEntity;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map.Entry;

import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;
import map.MapObject;
import map.RasterCell;
import map.blocks.base.ImageBasedBlock;

public class WildGrass extends ImageBasedBlock {

	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = -4701890259736654175L;
	private static final String SPAWNTEMPLATE_PROPID = "spawntemplate";
	private static final String BLOCK_ENTITIES_FILE = "ressources/gamedata/spawn_templates.cres";
	
	private static CResNode spawn_template;
	
	
	public WildGrass(String imageSource) {
		super(imageSource);
		enterable = true;
		
		properties.put(SPAWNTEMPLATE_PROPID, "grass_default");
	}

	
	
	@Override
	public boolean entered(Player actor) {
		
		try {
			Creature crt = CreatureFactory.spawnBytemplate(properties.get(SPAWNTEMPLATE_PROPID));
			if(crt != null){
				CLog.info(crt +" appeared!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void deseriralize() {
		super.deseriralize();
	}

	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new WildGrass(imageSource);
	}
	
	
	private CResNode getSpawnTemplates(){
		if(spawn_template == null){
			CResReader entityReader = new CResReader();	
			ArrayList<CResNode> val = entityReader.readFile(BLOCK_ENTITIES_FILE);
			
			for(CResNode node : val){
				if(node.getName().equals(properties.get(SPAWNTEMPLATE_PROPID))){
					spawn_template = node;
				}
			}
			if(spawn_template == null){
				throw new NullPointerException();
			}
		}
		
		return spawn_template;
	}
	

}
