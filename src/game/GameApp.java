package game;

import game.models.CooldownSheduler;
import game.models.CreatureFactory;
import game.models.PlayerEntity;
import game.view.MainFrame;

import java.awt.Dimension;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;

import console.view.CommandHandler;
import console.view.ConsoleFrame;

import map.MapEntity;
import map.MapStore;
import map.RasterCell;
import accessory.CLog;
import accessory.Command;
import accessory.cres.CResNode;
import accessory.cres.CResReader;
import accessory.crtm.FileHandler;

public class GameApp {



	public static final String SETTINGS_FILE = "ressources/gamedata/settings.cres";
	public static final String GUI_PROPERTIES_FILE = "ressources/gamedata/gui_properties.cres";
	
	private static final String MAINMAP_NODE_NAME = "main";
	private static final String GRAFICS_SETTINGS_NODE = "grafics";
		
	public static void main(String[] args) {
		
		long appStart = System.currentTimeMillis();
		

		//game contents
		CLog.info("\n --Load game contents");
		MapEntity mainMap = MapStore.getMap(MAINMAP_NODE_NAME);			
		launchGame(mainMap);
		
		CLog.info("\n --App loaded after: " + (System.currentTimeMillis()-appStart) + "ms \n");
		
		
	}

	public static void launchGame(MapEntity mainMap){
		CLog.info("\n --Load console");
		ConsoleFrame console = new ConsoleFrame(new CommandHandler());
		console.setTitle("console");
		console.setBounds(0,725, 1280, 300);
		//CLog.setConsoleContainer(console);
		
		CResReader resReader = new CResReader();

		CLog.info("\n --Load Player entity");
		final PlayerEntity playerEntitiy = new PlayerEntity();
		
		
		//View Settings
		CLog.info("--Read view settings..");
		HashMap<String , CResNode> settingsMap = resReader.readFileIntoMap(SETTINGS_FILE);
		CResNode graficsNode = settingsMap.get(GRAFICS_SETTINGS_NODE);

		
		//view
		CLog.info("\n --Init view..");
		MainFrame frame = new MainFrame(graficsNode, playerEntitiy);
		frame.loadDialog();

		
		//spawn templates
		CLog.info("\n --Init SpawnTemplates..");
		CreatureFactory.init();
		CreatureFactory.initCreatureIndex();
		
		//init game
		CLog.info("\n --Load map into view");
		frame.loadGameContents(MAINMAP_NODE_NAME, mainMap);
		
		CLog.info("\n --Start Cooldownsheduler");
		CooldownSheduler.getMainSheduler();
	}
	
}
