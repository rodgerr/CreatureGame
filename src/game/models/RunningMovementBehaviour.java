package game.models;

import accessory.Command;
import console.view.CommandHandler;
import map.SurfaceLevel;

public class RunningMovementBehaviour extends MovementBehaviour implements Runnable{

	private static final int BASE_SPEED = 1000;
	private static final int DEFAULT_MOVESPEED = 200;
	
	private PlayerEntity player;
	private SurfaceLevel activeSurfaceLevel;
	
	private int movementspeed;
	private int stepCooldown;
	
	public RunningMovementBehaviour(PlayerEntity player, int movementspeed){
		this.player = player;
		this.movementspeed = BASE_SPEED - movementspeed;
		this.stepCooldown = 0;
		
		activeSurfaceLevel = player.getMapTerrain(player.getCurX(), player.getCurY()).getSurfaceLevel();
	
		CooldownSheduler.getMainSheduler().addTask(this);
		
		CommandHandler.addCommand("set_ms", new Command() {
			
			@Override
			public String execute(String[] args) {
				if(args.length != 2){
					return "invalid syntax, use 'set_ms' <ms>";
				}
				try{
					int ms = Integer.parseInt(args[1]);
					setMovementSpeed(ms);
					return "set ms to "+ms;
				}
				catch(Exception e){
					return e.getClass().getSimpleName() +" - "+e.getMessage();
				}
				
			}
		});
	}
	
	public RunningMovementBehaviour(PlayerEntity player){
		this(player, DEFAULT_MOVESPEED);
	}
	
	
	@Override
	public boolean moveLeft() {
		if(player.getMapTerrain(player.getCurX()-1, player.getCurY()).getSurfaceLevel() == activeSurfaceLevel){
			if(takeStep()){
				return player.moveAction(player.getCurX()-1, player.getCurY());
			}
		}
		return false;
	}

	@Override
	public boolean moveRight() {
		if(player.getMapTerrain(player.getCurX()+1, player.getCurY()).getSurfaceLevel() == activeSurfaceLevel){
			if(takeStep()){
				return player.moveAction(player.getCurX()+1, player.getCurY());
			}
		}
		return false;
	}

	@Override
	public boolean moveUp() {
		if(player.getMapTerrain(player.getCurX(), player.getCurY()-1).getSurfaceLevel() == activeSurfaceLevel){
			if(takeStep()){
				return player.moveAction(player.getCurX(), player.getCurY()-1);
			}
		}
		return false;
	}

	@Override
	public boolean moveDown() {
		if(player.getMapTerrain(player.getCurX(), player.getCurY()+1).getSurfaceLevel() == activeSurfaceLevel){
			if(takeStep()){
				return player.moveAction(player.getCurX(), player.getCurY()+1);
			}
		}
		return false;
	}

	public boolean takeStep(){
		
		if(stepCooldown > 0){
			return false;
		}
		else {
			stepCooldown = movementspeed;
			return true;
		}
	}

	public void setMovementSpeed(int ms){
		this.movementspeed = BASE_SPEED - ms;
	}

	@Override
	public void run() {
		if(stepCooldown > 0){
			stepCooldown -= 50;
		}
	}

}
