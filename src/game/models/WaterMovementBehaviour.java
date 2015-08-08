package game.models;

import map.SurfaceLevel;

public class WaterMovementBehaviour extends RunningMovementBehaviour{

	private static final int WATER_MOVEMENT_SPEED = 500;
	
	private PlayerEntity player;
	
	public WaterMovementBehaviour(PlayerEntity player){
		super(player,WATER_MOVEMENT_SPEED);
		
		this.player = player;
		
		int positionX = player.getCurX();
		int positionY = player.getCurY();
		
		switch (player.getModelDirection()) {

		case UP:
			if(player.getMapTerrain(positionX, positionY-1).getSurfaceLevel() == SurfaceLevel.WATER){
				moveUp();
			}
			break;

		case DOWN:
			if(player.getMapTerrain(positionX, positionY+1).getSurfaceLevel() == SurfaceLevel.WATER){
				moveDown();
			}
			break;
		
		case LEFT:
			if(player.getMapTerrain(positionX-1, positionY).getSurfaceLevel() == SurfaceLevel.WATER){
				moveLeft();
			}
			break;
		
		case RIGHT:
			if(player.getMapTerrain(positionX+1, positionY).getSurfaceLevel() == SurfaceLevel.WATER){
				moveRight();
			}
			break;
		}

		
	}
	
	@Override
	public boolean moveLeft() {
		boolean retval = player.moveAction(player.getCurX()-1, player.getCurY());
		
		if(!isWaterCell()){
			exit();
		}
		
		return retval;
	}

	@Override
	public boolean moveRight() {
		boolean retval = player.moveAction(player.getCurX()+1, player.getCurY());
		
		if(!isWaterCell()){
			exit();
		}
		
		return retval;
	}

	@Override
	public boolean moveUp() {
		boolean retval = player.moveAction(player.getCurX(), player.getCurY()-1);
		
		if(!isWaterCell()){
			exit();
		}
		
		return retval;
	}

	@Override
	public boolean moveDown() {
		boolean retval = player.moveAction(player.getCurX(), player.getCurY()+1);
		
		if(!isWaterCell()){
			exit();
		}
		
		return retval;
	}
	
	private boolean isWaterCell(){
		if(player.getMapTerrain(player.getCurX(), player.getCurY()).getSurfaceLevel() != SurfaceLevel.WATER){
			return false;
		}
		return true;
	}
	
	private void exit(){
		player.setActiveMovementBehaviour(new RunningMovementBehaviour(player));
	}


	
}
