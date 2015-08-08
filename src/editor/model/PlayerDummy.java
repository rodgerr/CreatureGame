package editor.model;

import map.Direction;
import game.models.Player;

public class PlayerDummy implements Player {

	@Override
	public boolean moveLeft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveRight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPositionX(int targetX) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPositionY(int targetY) {
		// TODO Auto-generated method stub

	}

	@Override
	public Direction getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

}
