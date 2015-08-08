package game.input;

import game.models.PlayerEntity;
import game.view.GameCanvas;
import game.view.KeyboardCommand;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import accessory.CLog;
import accessory.Command;
import accessory.cres.CResNode;
import accessory.cres.CResReader;


public class MovementController implements KeyListener {
	
	private static final String CONTROLS_FILE = "ressources/gamedata/controls.cres";
	private static final String MOVEMENT_NODE = "movement";
	

	private GameCanvas canvas;
	private CResNode movementNode;
	
	private PlayerEntity player;
	
	HashMap<Integer, KeyboardCommand> runtimeCommands;
	boolean inputAllowed;
	
	public MovementController(PlayerEntity player){
		this.player = player;
		this.runtimeCommands = new HashMap<Integer, KeyboardCommand>();
		this.inputAllowed = true;
		
		CResReader resReader = new CResReader();
		HashMap<String, CResNode> controlsList = resReader.readFileIntoMap(CONTROLS_FILE);
		movementNode = controlsList.get(MOVEMENT_NODE);
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(canvas != null && inputAllowed){
			int keyCode = arg0.getKeyCode(); 
			
			if(keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_LEFT")) || keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_LEFT_ALT"))){
				player.moveLeft();
			}
			else if(keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_RIGHT")) || keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_RIGHT_ALT"))){
				player.moveRight();
			}
			else if(keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_UP")) || keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_UP_ALT"))){
				player.moveUp();
			}
			else if(keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_DOWN")) || keyCode == Integer.parseInt(movementNode.getNodeValue("MOVE_DOWN_ALT"))){
				player.moveDown();
			}
			else if(runtimeCommands.get(keyCode) != null){
				CLog.info("Shortcut "+keyCode+" activated");
				runtimeCommands.get(keyCode).execute();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void setCanvas(GameCanvas canvas){
		this.canvas = canvas;
	}
	
	public void addAction(String keyIdent, KeyboardCommand action){
		try {
			int keyCode = Integer.parseInt(movementNode.getNodeValue(keyIdent));
			if(keyCode != 0){
				runtimeCommands.put(keyCode, action);
			} else {
				CLog.error("key identy unknown");
			}
		} catch (Exception e){
			CLog.error(e);
		}

		
	}
	
	
	
	public HashMap<Integer, KeyboardCommand> getRuntimeCommands() {
		return runtimeCommands;
	}

	public void setRuntimeCommands(HashMap<Integer, KeyboardCommand> runtimeCommands) {
		this.runtimeCommands = runtimeCommands;
	}

	public void executeAction(String keyIdent){
		runtimeCommands.get(keyIdent).execute();
	}

	public boolean isInputAllowed() {
		return inputAllowed;
	}

	public void setInputAllowed(boolean inputAllowed) {
		this.inputAllowed = inputAllowed;
	}
	
	
}
