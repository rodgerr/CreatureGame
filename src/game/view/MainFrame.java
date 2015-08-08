package game.view;

import game.dialog.DialogController;
import game.input.MovementController;
import game.models.PlayerEntity;
import game.models.WaterMovementBehaviour;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;

import renderer.GameView;
import renderer.MapRenderer;

import console.view.CommandHandler;

import accessory.CLog;
import accessory.Command;
import accessory.cres.CResNode;
import accessory.crtm.FileHandler;

import map.MapEntity;
import map.MapStore;
import map.RasterCell;
import renderer.RosterFullScreenRenderer;

public class MainFrame extends JFrame {

	//constants
	private static final String GRAFICS_LOADING_FRAME_WIDTH_STRING = "loading_frame_width";
	private static final String GRAFICS_LOADING_FRAME_HEIGHT_STRING = "loading_frame_height";
	private static final String GRAFICS_CONTENT_FRAME_WIDTH_STRING = "content_frame_width";
	private static final String GRAFICS_CONTENT_FRAME_HEIGHT_STRING = "content_frame_height";
	private static final String GRAFICS_CONTENT_FRAME_FULLSCREEN = "content_frame_fullscreen";
	
	private CResNode graficsNode;
	private MovementController movementController;
	private HashMap<String, GameCanvas> maps;

	private GameCanvas gameContainer;
	private PlayerEntity playerObject;
	private String acticveMap;
	
	public MainFrame(CResNode graficsNode, PlayerEntity playerInstance){
		playerObject = playerInstance;
		this.graficsNode = graficsNode;
		this.maps = new HashMap<String, GameCanvas>();
		this.movementController = new MovementController(playerObject);
		
		
		final DialogController dialogController = new DialogController();
		
		int dialogWidth = Integer.parseInt(graficsNode.getNodeValue("dialog_width"));
		int dialogheight = Integer.parseInt(graficsNode.getNodeValue("dialog_height"));

		int dialogX = Integer.parseInt(graficsNode.getNodeValue("dialog_x"));
		int dialogY = Integer.parseInt(graficsNode.getNodeValue("dialog_y"));

		dialogController.setRenderUnitBounds(new Rectangle(dialogX, dialogY, dialogWidth, dialogheight));
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
		addKeyListener(movementController);
		
		//################ Keyboad Commands
		movementController.addAction("SHOW_ROSTER", new KeyboardCommand() {
			boolean active;
			RosterFullScreenRenderer rosterView;
			GameView defaultView;
			
			@Override
			public void execute() {
				System.out.println(acticveMap);
				defaultView = maps.get(acticveMap).getDefaultView();
				System.out.println("test");
				
				if(!active){
					if(rosterView == null){
						rosterView = new RosterFullScreenRenderer(playerObject);
						maps.get(acticveMap).addMouseListener(rosterView);
						rosterView.addClosedHandler(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								maps.get(acticveMap).setActiveView(defaultView);
								active = false;
							}
						});
					}
					maps.get(acticveMap).setActiveView(rosterView);
					active = true;
				}
				else{
					rosterView.close();
				}
				
			}
		});
		
		movementController.addAction("SURF", new KeyboardCommand() {
			@Override
			public void execute() {
				playerObject.setActiveMovementBehaviour(new WaterMovementBehaviour(playerObject));
			}
		});
		
		movementController.addAction("CONFIRM", new KeyboardCommand() {
			@Override
			public void execute() {
				boolean ret = dialogController.next();
				if(!ret){
					CLog.info("No Text");
				}
			}
		});
		
		//############ Console Commands
		CommandHandler.addCommand(CommandHandler.QUIT_COMMAND, new Command() {
			@Override
			public String execute(String[] args) {
				System.exit(0);
				return "exit";
			}
		});
		
		CommandHandler.addCommand(CommandHandler.COMMANDS_COMMAND, new Command() {
			
			@Override
			public String execute(String[] args) {
				String output = "";
				for(Entry<Integer, KeyboardCommand> entry : movementController.getRuntimeCommands().entrySet()){
					output = output+ (KeyEvent.getKeyText(entry.getKey()))+" - " + entry.getValue().toString() +" \n"; 
				}
				return output;
			}
		});
		
		
		CommandHandler.addCommand(CommandHandler.RENDERDIALOG_COMMAND, new Command() {
			@Override
			public String execute(String[] args) {
				if(args.length < 2){
					return "invalid syntax! use 'renderdialog <token> <...>'" ;
				}
				else {
					ArrayList<String> contents = new ArrayList<String>();
					for(int i = 1; i < args.length; i++){
						contents.add(args[i]);
					}
					DialogController.showDialog(contents);
					return "dialog activated";
				}
			}
		});
		
		CommandHandler.addCommand(CommandHandler.SET_RENDERENGINE_COMMAND, new Command() {
			
			@Override
			public String execute(String[] args) {
				if(args.length < 2){
					return "invalid syntax! use 'renderengine <class>'" ;
				}
				
			    
				try {
					Class c = Class.forName("renderer."+args[1]);
					MapRenderer render = (MapRenderer) c.newInstance();
					
									
				} catch (Exception e) {
					//e.printStackTrace();
					return "error during instantiation: " + e.getClass().getCanonicalName() + ":" + e.getMessage();
				} 
				
				return "set render to " + args[1];
			}
		});
		
		CommandHandler.addCommand(CommandHandler.LOAD_MAP_COMMAND, new Command() {
			
			@Override
			public String execute(String[] args) {
				if(args.length != 2){
					return "invalid syntax, use 'loadmap <mapfile>'";
				}
				
				setContentPane(new FullScreenLoadingCanvas(20,20));
				
				System.out.println(args[0]+" - "+args[1]);
				String mapfile = args[1];
				loadGameContents(mapfile, MapStore.getMap(mapfile));
				return "done";
			}
		});
	}

	
	public void loadGameContents(String mapName, MapEntity mainMap){
		movementController.setInputAllowed(false);
		setTitle("Game - "+mapName);
		acticveMap = mapName;
		
		if(!Boolean.parseBoolean(graficsNode.getNodeValue(GRAFICS_CONTENT_FRAME_FULLSCREEN))){
			resizeFrame(Integer.parseInt(graficsNode.getNodeValue(GRAFICS_CONTENT_FRAME_WIDTH_STRING)),  Integer.parseInt(graficsNode.getNodeValue(GRAFICS_CONTENT_FRAME_HEIGHT_STRING)));
						
		} else {
			//TODO:
			resizeFrame(1920, 1080);
		}
		
		if(gameContainer == null){
			gameContainer = new GameCanvas(mainMap, playerObject);
			gameContainer.setSize(getWidth(), getHeight());
			gameContainer.setFocusable(true);
			
		}
		else {
			gameContainer.loadContents(mainMap);
		}

		
		movementController.setCanvas(gameContainer);
		
		DialogController.setGraphicContainer(gameContainer.getGraphics());
		
		playerObject.setActiveMap(mainMap);
		playerObject.setPosition(gameContainer.getPov());
		
		maps.put(acticveMap, gameContainer);
		gameContainer.addKeyListener(new MovementController(playerObject));
		gameContainer.start();
		
		
		movementController.setInputAllowed(true);
		setContentPane(gameContainer);
	}


	public void loadDialog() {
		setTitle("Loading...");
		resizeFrame(Integer.parseInt(graficsNode.getNodeValue(GRAFICS_LOADING_FRAME_WIDTH_STRING)),  Integer.parseInt(graficsNode.getNodeValue(GRAFICS_LOADING_FRAME_HEIGHT_STRING)));
	}
	
	private void resizeFrame(int width, int height){
		setBounds(new Rectangle(width, height));
	}
}
