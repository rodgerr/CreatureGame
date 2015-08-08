package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import renderer.DefaultRenderer;
import renderer.MapRenderer;

import accessory.CLog;
import accessory.cres.CResNode;
import accessory.crtm.CRTMWriter;
import accessory.crtm.SerialCRTMWriter;

import editor.view.mousehandling.DragReleaseEvent;
import editor.view.mousehandling.MousePointer;

import map.MapEntity;
import map.MapFactory;
import map.RasterCell;

public class MapEditorPanel extends JPanel implements MouseMotionListener, MouseListener, Serializable{

	private MapEntity map;
	private RasterCell[][] raster;
	private MousePointer currPointer;

	
	private int xcells;
	private int ycells;
	private int lastxcell;
	private int lastycell;
	
	private int dragCornerA_X;
	private int dragCornerA_Y;
	private int dragCornerB_X;
	private int dragCornerB_Y;
	private boolean inDrag;
	
	private boolean highlightmouse;
	private boolean showRaster;
	
	private String contentpath;

	private MapRenderer mapRenderer;
	
	private ArrayList<DragReleaseEvent> dragReleaseEvents;
	

	public MapEditorPanel(MapEntity map){
		super();
		setBackground(RasterCell.BGCOLOR);
		addMouseMotionListener(this);
		addMouseListener(this);
		currPointer = null;
				
		dragCornerA_X = 0;
		dragCornerA_Y = 0;
		dragCornerB_X = 0;
		dragCornerB_Y = 0;
		inDrag = false;
		
		this.map = map;
		this.raster = map.getRaster();
		xcells =  	 raster.length;
		ycells =	 raster[0].length;

		setPreferredSize(new Dimension(xcells*RasterCell.RASTERSIZE,ycells*RasterCell.RASTERSIZE));
		
		highlightmouse = true;
		
		lastxcell = 0;
		lastycell = 0;
		
		showRaster = true;
	
		mapRenderer = new DefaultRenderer();
		dragReleaseEvents = new ArrayList<DragReleaseEvent>();
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		
		for(int i = 0 ; i < xcells; i++){
			for (int j = 0; j < ycells; j++) {
				mapRenderer.renderCell(raster[i][j], showRaster, arg0);
			}
		}

		if(inDrag){
			int rectx;
			int recty;
			int recth;
			int rectw;
			
			if(dragCornerA_X > dragCornerB_X){
				rectx = dragCornerB_X;
				rectw = dragCornerA_X - dragCornerB_X;
				
				
			} else {
				rectx = dragCornerA_X;
				rectw = dragCornerB_X - dragCornerA_X;
			}
			
			if(dragCornerA_Y > dragCornerB_Y){
				recty = dragCornerB_Y;
				recth = dragCornerA_Y - dragCornerB_Y;
			} else {
				recty = dragCornerA_Y;
				recth = dragCornerB_Y - dragCornerA_Y;
			}
			arg0.setColor(Color.WHITE);
			arg0.drawRect(rectx, recty, rectw, recth);
			
		} 
	}



	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(highlightmouse){

			raster[lastxcell][lastycell].setMouseover(false);
			raster[lastxcell][lastycell].setPointerover(null);
	
			int newxcell = arg0.getX()/RasterCell.RASTERSIZE;
			int newycell = arg0.getY()/RasterCell.RASTERSIZE;
			
			if(newxcell < xcells && newycell < ycells){
				if(currPointer == null){
					raster[newxcell][newycell].setMouseover(true);
				} else {
					raster[newxcell][newycell].setPointerover(currPointer);
				}
				
				raster[newxcell][newycell].fireMouseOverHandler(raster[newxcell][newycell], arg0);
				
				lastxcell = newxcell;
				lastycell = newycell;
			}
	
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		//rightlick, leaving tool
		if(arg0.getButton() == 3){
			currPointer = null;
			repaint();
			return;
		}
		
		
		int xcell = arg0.getX()/RasterCell.RASTERSIZE;
		int ycell = arg0.getY()/RasterCell.RASTERSIZE;
		
		if(arg0.getButton() != 1){
			for(DragReleaseEvent event : dragReleaseEvents){
				event.release(new RasterCell[0][0]);
			}
			
		} else {
			RasterCell[][] dragcells = new RasterCell[1][1];
			dragcells[0][0] = raster[xcell][ycell];
			
			for(DragReleaseEvent event : dragReleaseEvents){
				event.release(dragcells);
			}
			
		}

		
		if(currPointer != null){
			boolean done = currPointer.onClick(raster[xcell][ycell],arg0);
			repaint();
			if(done){
				currPointer = null;
			}
		} else {
			raster[xcell][ycell].fireMouseClickHandler(raster[xcell][ycell], arg0);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		inDrag = true;
		
		dragCornerB_X = arg0.getX();
		dragCornerB_Y = arg0.getY();
		repaint();
//		if(currPointer != null){
//			boolean done = currPointer.onDrag(raster[arg0.getX()/RasterCell.RASTERSIZE][arg0.getY()/RasterCell.RASTERSIZE],arg0);
//			raster[arg0.getX()/RasterCell.RASTERSIZE][arg0.getY()/RasterCell.RASTERSIZE].setMouseover(true);
//			if(done){
//				currPointer = null;
//			}
//		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		dragCornerA_X = arg0.getX();
		dragCornerA_Y = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(inDrag){
			inDrag = false;
			
			int startX;
			int startY;
			int targetX;
			int targetY;
			
			if(dragCornerA_X > dragCornerB_X){
				targetX = dragCornerA_X / RasterCell.RASTERSIZE;
				startX = dragCornerB_X / RasterCell.RASTERSIZE;
							
			} else {
				targetX = dragCornerB_X / RasterCell.RASTERSIZE;
				startX = dragCornerA_X / RasterCell.RASTERSIZE;
			}
			
			if(dragCornerA_Y > dragCornerB_Y){
				targetY = dragCornerA_Y / RasterCell.RASTERSIZE;
				startY = dragCornerB_Y / RasterCell.RASTERSIZE;
			} else {
				targetY = dragCornerB_Y / RasterCell.RASTERSIZE;
				startY = dragCornerA_Y / RasterCell.RASTERSIZE;
			}

			
			RasterCell[][] dragcells = new RasterCell[targetX - startX +1][targetY - startY+1];

			for(int i = startX; i <= targetX; i++){
				for(int j = startY; j <= targetY; j++){
					
					try {
						dragcells[i-startX][j-startY] = raster[i][j];
						if(currPointer != null){
							boolean done = currPointer.onClick(raster[i][j],arg0);
							if(done){
								currPointer = null;
							}
						} 
					}
					catch(ArrayIndexOutOfBoundsException e){
						CLog.info(e.getMessage()+" when trying to fill "+i+"/"+j);
					}
				}
			}
			
			for(DragReleaseEvent event : dragReleaseEvents){
				event.release(dragcells);
			}
			
		}
	}

	public MousePointer getCurrPointer() {
		return currPointer;
	}

	public void setCurrPointer(MousePointer currPointer) {
		this.currPointer = currPointer;
	}	
	
	public String getContentpath() {
		return contentpath;
	}

	public void setContentpath(String contentpath) {
		this.contentpath = contentpath;
	}
	
	
	public boolean isShowRaster() {
		return showRaster;
	}

	public void setShowRaster(boolean showRaster) {
		this.showRaster = showRaster;
		repaint();
	}

	public void setRenderer(MapRenderer arg){
		this.mapRenderer = arg;
		repaint();
	}
	
	
	public void saveRaster(String filename) {
		setContentpath(filename);
		CLog.info("save at: "+filename +" Hash: "+hashCode());
		try {
			CRTMWriter serialWriter = new SerialCRTMWriter();
			map.setRaster(raster);
			serialWriter.writeToFile(filename, map, xcells, ycells, getMapProperties());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void addDragReleaseEvent(DragReleaseEvent event){
		dragReleaseEvents.add(event);
	}

	public CResNode getMapProperties() {
		if(map.getProperties() == null){
			map.setProperties(MapFactory.createDefaultProperties());
		}
		return map.getProperties();
	}

	public void setMapProperties(CResNode mapProperties) {
		map.setProperties(mapProperties);
		
	}

	public MapEntity getMap() {
		return map;
	}
	

}
