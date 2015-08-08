package game.models;

import game.input.PositionChangedHandler;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import map.Direction;
import map.Interactor;
import map.MapEntity;
import map.MapObject;
import map.RasterCell;
import map.blocks.base.ImageBasedBlock;
import map.blocks.base.PlayerObject;
import accessory.CLog;
import accessory.Command;
import accessory.cres.CResNode;
import console.view.CommandHandler;


public class PlayerEntity extends MapObject implements Interactor, Player, PlayerObject{
	
	/**
	 * 14-03-2013
	 */
	private static final long serialVersionUID = -2118200348302255198L;
	
	public static final String PLAYER_PROPERTIES_FILE = "ressources/gamedata/player_entity.cres";
	public static final String RENDER_PROPERTIES_NODE = "render_properties";
	
	private RasterCell[][] active_map;
	
	private volatile int positionX;
	private volatile int positionY;
	
	private Direction modelDirection;
	private HashMap<Direction,ImageBasedBlock> modelContainerMap;
	private MapObject currentObject;
	private CResNode renderNodes;	
	
	private ArrayList<PositionChangedHandler> positionListener;
	private HashMap<Integer,Creature> roster;
	
	private MovementBehaviour activeMovementBehaviour;
	



	public PlayerEntity() {
		super();
		CLog.info("create player " +hashCode());
				
		modelDirection = Direction.DOWN;
		positionListener = new ArrayList<PositionChangedHandler>();
		
		setActiveMovementBehaviour(new RunningMovementBehaviour(this));
		loadRoster();
			
		//TODO: auslagern
		CommandHandler.addCommand("moveto", new Command() {		
			@Override
			public String execute(String[] args) {
				if(args.length < 3 ){
					return "too few arguments! use 'moveto <x> <y>'";
				}
				else {
					try{
						moveAction(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					} catch (NumberFormatException e){
						return "wrong input type! use 'moveto <integer> <integer>'";
					}
				}
				return "player moved to "+args[1]+"/"+args[2];
			}
		});
	}




	private void loadRoster() {
		roster = new HashMap<Integer, Creature>();
		try {
			roster.put(1, CreatureFactory.createBaseCreature("creature_earth_1"));
			roster.put(2, CreatureFactory.createBaseCreature("creature_dark_1"));
			roster.put(3, CreatureFactory.createBaseCreature("creature_metal_1"));
			roster.put(4, CreatureFactory.createBaseCreature("creature_ice_1"));
			roster.put(5, CreatureFactory.createBaseCreature("creature_fire_1"));
			roster.put(6, CreatureFactory.createBaseCreature("creature_water_1"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CLog.info("Roster initialized:"+roster);
	}


	@Override
	public boolean interact(Player actor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean entered(Player actor) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void deseriralize() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void paintComponent(Graphics arg0, RasterCell target) {
		modelContainerMap.get(getModelDirection()).paintComponent(arg0, target);
	}


	@Override
	public MapObject newInstance(RasterCell cell, MouseEvent event) {
		return new PlayerEntity();
	}
	
	public boolean moveAction(int x, int y) {
		
		if(getMapObject(x, y).enterable(this) && getMapTerrain(x, y).enterable(this)){
			if(currentObject != null){
				currentObject.leaved(this);
			}
			
			positionX = x;
			positionY = y;
						
			notifyListener();
			currentObject = getMapObject(positionX, positionY);
			currentObject.entered(this);
			
			MapObject currentTerrain = getMapTerrain(positionX, positionY);
			if(currentTerrain != null){
				currentTerrain.entered(this);
			}
			
			return true;
		}
		return false;
	}

	@Override
	public boolean moveLeft() {
		modelDirection = Direction.LEFT;
		return activeMovementBehaviour.moveLeft();
	}
	
	@Override
	public boolean moveRight() {
		modelDirection = Direction.RIGHT;
		return activeMovementBehaviour.moveRight();
	}


	@Override
	public boolean moveUp() {
		modelDirection = Direction.UP;
		return activeMovementBehaviour.moveUp();
	}


	@Override
	public boolean moveDown() {
		modelDirection = Direction.DOWN;
		return activeMovementBehaviour.moveDown();
	}

	private MapObject getMapObject(int x, int y){		
		if(active_map != null){
			try{
				return active_map[x][y].getAttachedObject();
			} catch (ArrayIndexOutOfBoundsException e){
				//player reached a map bound
				return this;
			}
		} 
		CLog.error("No map-object registered for player object");
		return this;
	}
	
	public MapObject getMapTerrain(int x, int y){		
		if(active_map != null){
			try{
				return active_map[x][y].getTerrain();
			} catch (ArrayIndexOutOfBoundsException e){
				//player reached a map bound
				return this;
			}
		} 
		CLog.error("No map-object registered for player object");
		return this;
	}

	public RasterCell[][] getActiveMap() {
		return active_map;
	}


	public int getCurX() {
		return positionX;
	}


	public int getCurY() {
		return positionY;
	}


	public void setActiveMap(MapEntity mainMap) {
		this.active_map = mainMap.getRaster();
	}


	public void setPositionX(int curX) {
		this.positionX = curX;
		notifyListener();
	}


	public void setPositionY(int curY) {
		this.positionY = curY;
		notifyListener();
	}


	public void setPosition(Rectangle pov) {
		this.positionX = (int)pov.getWidth();
		this.positionY = (int)pov.getHeight();
		notifyListener();
	}

	public void addPositionChangedHandler(PositionChangedHandler h){
		positionListener.add(h);
	}
	
	public void notifyListener(){
		for (PositionChangedHandler el : positionListener) {
			el.onMove(positionX, positionY);
		}
	}


	@Override
	public boolean enterable(Player actor) {
		return false;
	}




	public Direction getModelDirection() {
		return modelDirection;
	}


	public void setModelDirection(Direction modelDirection) {
		this.modelDirection = modelDirection;
	}


	public void addCombatEnteredEventListener(
			CombatEnteredEvent combatEnteredEvent) {
		//TODO
		
	}


	public HashMap<Integer, Creature> getRoster() {
		return roster;
	}


	@Override
	public Direction getOrientation() {
		return modelDirection;
	}


	public String getName() {
		return "Playername";
	}
	
	public MovementBehaviour getActiveMovementBehaviour() {
		return activeMovementBehaviour;
	}


	public void setActiveMovementBehaviour(MovementBehaviour activeMovementBehaviour) {
		this.activeMovementBehaviour = activeMovementBehaviour;
		this.modelContainerMap = this.activeMovementBehaviour.getModelMap();
	}

}
