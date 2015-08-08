package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import map.blocks.base.DefaultMapObject;
import map.terrain.DefaultTerrain;

import editor.view.MapOrientation;
import editor.view.mousehandling.MouseOverHandler;
import editor.view.mousehandling.MousePointer;

public class RasterCell extends Rectangle implements Serializable{
	
	private static final long serialVersionUID = 20110824;

	public static final int RASTERSIZE = 25;
	
	public static Color BGCOLOR = Color.GRAY.darker();
	public static Color HIGHLIGHT_COLOR = Color.LIGHT_GRAY;
	public static Color HIGHLIGHT_POINTER_COLOR = Color.CYAN;
	public static Color BORDER_COLOR = Color.DARK_GRAY;
	
	private transient boolean mouseover;
	private transient MousePointer pointerover;
	private transient boolean highlighted;

	private int cx;
	private int cy;
	
	private MapObject attachedObject;
	private MapObject terrain;
	private transient HashMap<MapOrientation, RasterCell> neighbors;
	
	private transient ArrayList<MouseOverHandler> mouseOverHandler;
	private transient CoastRenderer coastRenderer;
	
	public RasterCell(int x, int y){
		setBounds(x*RASTERSIZE,y*RASTERSIZE, RASTERSIZE, RASTERSIZE);
		cx = x;
		cy = y;
		neighbors = new HashMap<MapOrientation, RasterCell>();
		mouseOverHandler = new ArrayList<MouseOverHandler>();
	}
	
	public RasterCell(int x, int y, MapObject object){
		this(x,y);
		this.attachedObject = object;
		this.attachedObject.setCurrentX(cx);
		this.attachedObject.setCurrentY(cy);
	}
	
	public void paintComponent(Graphics arg0, boolean raster) {
			arg0.setColor(BGCOLOR);
			
			//TODO: if every block renders a background by default it snaps multicell blocks
			// by overriding them (http://i.imgur.com/fdeXQX5.png)
			//arg0.fillRect(cx*RASTERSIZE, cy*RASTERSIZE, RASTERSIZE, RASTERSIZE);
			
			getTerrain().paintComponent(arg0, this);
			
			for(Entry<MapOrientation, RasterCell> entry : getNeighbors().entrySet()){			
				if(getTerrain().getSurfaceLevel() != entry.getValue().getTerrain().getSurfaceLevel()){
					getCoastRenderer().paintComponent(arg0, this, entry.getValue(), entry.getKey());
				}
			}
					
			getAttachedObject().paintComponent(arg0, this);
			
			if(mouseover){
				arg0.setColor(HIGHLIGHT_COLOR); 
				arg0.fillRect(cx*RASTERSIZE, cy*RASTERSIZE, RASTERSIZE, RASTERSIZE);
			} 
			
			if(pointerover != null){
				arg0.setColor(HIGHLIGHT_POINTER_COLOR);
				arg0.fillRect(	cx*RASTERSIZE, 
								cy*RASTERSIZE, 
								(int)pointerover.getPointerDimension().getWidth()*RASTERSIZE, 
								(int)pointerover.getPointerDimension().getHeight()*RASTERSIZE);
			}
			
			if(raster){
				arg0.setColor(BORDER_COLOR);
				arg0.drawRect(cx*RASTERSIZE, cy*RASTERSIZE, RASTERSIZE, RASTERSIZE);
				
				if(highlighted){
					arg0.setColor(HIGHLIGHT_POINTER_COLOR);
					arg0.drawRect(cx*RASTERSIZE+1, cy*RASTERSIZE+1, RASTERSIZE-2, RASTERSIZE-2);
				}
			} 

	}

	public void setMouseover(boolean mouseover) {
		this.mouseover = mouseover;
	}
	
	
	public void addMouseOverHandler(MouseOverHandler handler){
		if(mouseOverHandler == null){
			mouseOverHandler = new ArrayList<MouseOverHandler>();
		}
		this.mouseOverHandler.add(handler);
	}
	
	public void fireMouseOverHandler(RasterCell arg0, MouseEvent arg1){
		for(MouseOverHandler handler : mouseOverHandler){
			handler.entered(arg0, arg1);
		}
	}
	
	public void fireMouseClickHandler(RasterCell arg0, MouseEvent arg1){
		for(MouseOverHandler handler : mouseOverHandler){
			handler.clicked(arg0, arg1);
		}
	}

	public MapObject getAttachedObject() {
		if(attachedObject == null){
			return new DefaultMapObject(cx, cy);
		}
		return attachedObject;
	}

	public void setAttachedObject(MapObject attachedObject) {
		this.attachedObject = attachedObject;
	}

	public MousePointer isPointerover() {
		return pointerover;
	}

	public void setPointerover(MousePointer currPointer) {
		this.pointerover = currPointer;
	}

	public boolean isMouseover() {
		return mouseover;
	}
	
	public MapObject getTerrain() {
		if(terrain == null){
			return new DefaultTerrain();
		}
		return terrain;
	}

	public void setTerrain(MapObject terrain) {
		this.terrain = terrain;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[x=" + cx + ", y="+ cy + ", size="+ RASTERSIZE + ", object=" + attachedObject+", terrain="+terrain+" ]";
	}

	public int getCx() {
		return cx;
	}


	public int getCy() {
		return cy;
	}

	public HashMap<MapOrientation, RasterCell> getNeighbors() {
		if(neighbors == null){
			neighbors = new HashMap<MapOrientation, RasterCell>();
		}
		return neighbors;
	}

	public String debug(){
		return "["+cx+","+cy+"]";
	}
	
	public void setCx(int cx){
		this.cx = cx;
		getAttachedObject().setCurrentX(cx);
	}
	
	public void setCy(int cy){
		this.cy = cy;
		getAttachedObject().setCurrentY(cy);
	}
	
	private CoastRenderer getCoastRenderer(){
		if(coastRenderer == null){
			coastRenderer = new CoastRenderer();
		}
		return coastRenderer;
	}
	
	
	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
