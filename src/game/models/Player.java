package game.models;

import map.Direction;

public interface Player {

	public boolean moveLeft();
	public boolean moveRight();
	public boolean moveUp();
	public boolean moveDown();
	public void setPositionX(int targetX);
	public void setPositionY(int targetY);
	public Direction getOrientation();
}
