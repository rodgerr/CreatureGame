package game.view;

import game.input.PositionChangedHandler;
import game.models.CombatEnteredEvent;
import game.models.FightSequence;
import game.models.PlayerEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Map.Entry;

import javax.swing.JPanel;

import map.MapEntity;
import map.RasterCell;
import renderer.GameBaseRenderer;
import renderer.GameView;
import accessory.CLog;
import accessory.Command;
import accessory.cres.CResNode;
import console.view.CommandHandler;

public class GameCanvas extends JPanel implements Runnable{
	
	private GameView activeView;
	private GameView defaultView;
	
	private RasterCell[][] contents;
	private Rectangle pov;
	
	private PlayerEntity playerObject;
	
	private Thread animatorThread;
	private boolean running;
	
	//debug help
	private boolean fpsGraph;
	private boolean displayfps;
	private boolean displayXY;
	
	public GameCanvas(MapEntity mainMap, PlayerEntity playerObject){
		
		this.playerObject = playerObject;
		this.playerObject.addPositionChangedHandler(new PositionChangedHandler() {
			@Override
			public void onMove(int x, int y) {
				setPov(x, y);
			}
		});
		
		this.fpsGraph = false;
		this.displayfps = false;
				
		loadContents(mainMap);
		
		CommandHandler.addCommand(CommandHandler.SHOW_FPS_COMMAND, new Command() {		
			@Override
			public String execute(String[] args) {
				displayfps = !displayfps;
				return "displaying fps:" +displayfps;
			}
		});
		
		CommandHandler.addCommand(CommandHandler.SHOW_FPS_GRAPH_COMMAND, new Command() {		
			@Override
			public String execute(String[] args) {
				fpsGraph = !fpsGraph;
				return "fps graph active:" +fpsGraph;
			}
		});
		
		CommandHandler.addCommand(CommandHandler.SHOW_X_Y_COMMAND, new Command() {		
			@Override
			public String execute(String[] args) {
				displayXY = !displayXY;
				return "show player cooradinates, active:" +displayXY;
			}
		});
		
		final CResNode propertyNode = mainMap.getProperties();
		
		CommandHandler.addCommand(CommandHandler.SHOW_MAP_PROPERTIES_COMMAND, new Command() {
			
			@Override
			public String execute(String[] args) {
				String output = "";
				for(Entry<String,String> entry : propertyNode.getNodes().entrySet()){
					output += entry.getKey() + " -- " + entry.getValue()+"\n";
				}
				return output;
			}
		});
		
			
		this.playerObject.addCombatEnteredEventListener(new CombatEnteredEvent() {
			
			@Override
			public void entered(FightSequence arg) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void loadContents(MapEntity map){
		this.contents = map.getRaster();
		this.pov = map.getSpawnPoint();
		
		this.defaultView = new GameBaseRenderer(contents, playerObject);
		this.activeView = this.defaultView;
	}


	@Override
	protected void paintComponent(Graphics arg0) {
		long renderStart = System.currentTimeMillis();
		
		
		if(activeView != null){
			activeView.render(arg0,this.getParent(), pov);
		}
		else {
			arg0.setColor(Color.BLACK);
		}

		if(fpsGraph){
			CLog.renderGraph("ms", (int) (System.currentTimeMillis() - renderStart));
		}
		
		arg0.setColor(Color.RED);
		
		if(displayfps){
			try{
				arg0.drawString(1000/(System.currentTimeMillis() - renderStart)+"fps", 5, 20);
				//if game renders to fast we need to hook 'divided by zero' error
			} catch(ArithmeticException e){}
			
		}
		
		if(displayXY){
			arg0.drawString("x="+playerObject.getCurX()+" y="+playerObject.getCurY(), 5, 40);
		}
	}

	
	public void setPov(int x, int y){
		pov.setBounds(new Rectangle(x, y));
		repaint();
	}
	
	
	public Rectangle getPov(){
		return pov;
	}


	public void start(){
		if(animatorThread == null || !running){
			running = true;
			animatorThread = new Thread(this);
			animatorThread.start();
		}
		
	}
	
	@Override
	public void run() {
		
		long renderStart = System.currentTimeMillis();
		
		while(running){
			updateEntities();
			repaint();
			
			//calculate render time
			long renderTime = System.currentTimeMillis()-renderStart;
			//calculate sleep shedule, needed to prevent faster cycles due to fast systems
			//pattern: if rendertime is low, longer sleep required and vice versa
			//optimal rendertime would be 10ms (100fps)
			long sleepShedule = 10 - renderTime;
			
			//if frame render takes longer than 10ms wait a bit anyway, freeing ressources
			if(sleepShedule <= 0){
				sleepShedule = 5;
			}
			
			try {
				Thread.sleep(sleepShedule);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		CLog.info("animation thread stopped!");
	}


	private void updateEntities() {
				
	}

	public void setActiveView(GameView activeView) {
		this.activeView = activeView;
	}
	
	public GameView getDefaultView(){
		return this.defaultView;
	}
}
