package renderer;

import game.GameApp;
import game.models.Creature;
import game.models.PlayerEntity;
import game.view.CreatureRosterStatusFrame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

import accessory.cres.CResNode;
import accessory.cres.CResReader;


public class RosterFullScreenRenderer implements GameView, MouseListener {

	private static final String CLOSE_VIEW_COMMAND = "close_view";
	private static final int TOP_FOOTER_HEIGHT = 75;
	private static final int BOTTOM_FOOTER_HEIGHT = 75;
	
	public static final Color BAR_COLOR = CreatureRosterStatusFrame.BACKGROUND_COLOR;
	private static final Color BG_COLOR = Color.DARK_GRAY;
		
	private PlayerEntity player;
	private HashMap<Integer,CreatureRosterStatusFrame> creatureDiplays;
	
	private CResNode renderSettings;
	
	private Rectangle closeButtonBounds;
	private ArrayList<ActionListener> actionListenerList;
	
	public RosterFullScreenRenderer(PlayerEntity player){
		this.player = player;		
		this.creatureDiplays = new HashMap<Integer, CreatureRosterStatusFrame>();
		this.closeButtonBounds = new Rectangle(0, 0, 0, 0);
		this.actionListenerList = new ArrayList<ActionListener>();
		
		init();
	}
	
	private void init() {
		CResReader resReader = new CResReader();	
		renderSettings = resReader.readFileIntoMap(GameApp.GUI_PROPERTIES_FILE).get("creature_render_settings");
		
		creatureDiplays.put(0, new CreatureRosterStatusFrame(renderSettings));
	}

	@Override
	public void render(Graphics arg0, Container container, Rectangle pov) {
		Graphics2D g2d = (Graphics2D) arg0.create();
		
		//set up the background
	
		GradientPaint gradient1 = new GradientPaint(0,-550,Color.BLACK,0, container.getHeight()-50,new Color(0.788f, 0.788f, 0.79f, 1.0f));
		g2d.setPaint(gradient1);
		g2d.fill(new Rectangle(container.getWidth(), container.getHeight()));
				
		g2d.setColor(new Color(0.93f,0.93f,0.94f,1.0f));
		g2d.fillRect(0, 0, container.getWidth(), TOP_FOOTER_HEIGHT);
		g2d.fillRect(0, container.getHeight()-BOTTOM_FOOTER_HEIGHT, container.getWidth(), BOTTOM_FOOTER_HEIGHT);

		//render the roster
		int ch = container.getHeight();
		int cw = container.getWidth();
		
//		g2d.setColor(Color.BLACK);
//		g2d.setFont(new Font("Arial", Font.PLAIN, 20));
//		g2d.drawString(player.getName(), 20, 45);
		
		CreatureRosterStatusFrame frame = creatureDiplays.get(0);
		
		//slot1
		if(player.getRoster().get(1) != null){
			frame.render(arg0, player.getRoster().get(1), new Rectangle(cw/10, ch/5, cw/100*39, ch/100*18));
		}
		
		//slot2
		if(player.getRoster().get(2) != null){
			frame.render(arg0, player.getRoster().get(2), new Rectangle(cw/10, ch/5+cw/9, cw/100*39, ch/100*18));
		}
		
		//slot3
		if(player.getRoster().get(3) != null){
			frame.render(arg0, player.getRoster().get(3), new Rectangle(cw/10, ch/5+cw/9*2, cw/100*39, ch/100*18));
		}
		
		//slot4
		if(player.getRoster().get(4) != null){
			frame.render(arg0, player.getRoster().get(4), new Rectangle(cw/10+cw/100*41, ch/5, cw/100*40, ch/100*18));
		}
		
		//slot5
		if(player.getRoster().get(5) != null){
			frame.render(arg0, player.getRoster().get(5), new Rectangle(cw/10+cw/100*41, ch/5+cw/9, cw/100*40, ch/100*18));
		}
		
		//slot6
		if(player.getRoster().get(6) != null){
			frame.render(arg0, player.getRoster().get(6), new Rectangle(cw/10+cw/100*41, ch/5+cw/9*2, cw/100*40, ch/100*18));
		}
				
		g2d.setColor(Color.red);
		this.closeButtonBounds = new Rectangle((int) (cw-(cw*0.05)), (int) (ch-(ch*0.08)), 50, 50);
		g2d.fillRect(closeButtonBounds.x, closeButtonBounds.y, closeButtonBounds.width, closeButtonBounds.height);
		
		g2d.dispose();
	}

	public void addClosedHandler(ActionListener actionListener) {
		actionListenerList.add(actionListener);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(closeButtonBounds.contains(e.getPoint())){
			fireClosedEvent();			
		}
	}

	private void fireClosedEvent() {
		for(ActionListener a : actionListenerList){
			a.actionPerformed(new ActionEvent(this, this.hashCode(), CLOSE_VIEW_COMMAND));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void close(){
		fireClosedEvent();
	}
}
