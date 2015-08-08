package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import map.MapObject;
import map.RasterCell;
import map.SurfaceLevel;
import map.blocks.InterstateTeleporter;
import map.blocks.OverlayInterstateTeleporter;
import map.blocks.SolidBlock;
import map.blocks.Teleporter;
import map.blocks.WildGrass;
import map.blocks.base.DefaultMapObject;
import map.blocks.base.ImageBasedBlock;
import map.templates.BaseTemplate;
import map.templates.ImageBasedBlockTemplate;
import map.terrain.BasicTerrain;
import map.terrain.DefaultTerrain;
import map.terrain.LiquidEnterableTerraint;
import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;
import editor.view.mousehandling.MousePointer;

public class ToolsPanel extends JPanel {

	private static final String BLOCK_ENTITIES_FILE = "ressources/appdata/editor_blockentities.cres";
	private static final String TERRAIN_ENTITIES_FILE = "ressources/appdata/editor_terrainentities.cres";
	private static final String ERASER_PNG = "ressources/sprites/editor/eraser.png";
	private static final String DEFAULT_PNG = "ressources/sprites/editor/default.png";
	private static final int BUTTON_BORDER_SIZE = 2;
	
	
	MapEditorPanel map;
	ArrayList<JButton> buttons;
	
	public ToolsPanel() {
		setPreferredSize(new Dimension(90,60));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		buttons = new ArrayList<JButton>();
		
		JPanel blockPanel = new JPanel();
		//blockPanel.setSize(new Dimension(30, 100));
		blockPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		blockPanel.setBorder(BorderFactory.createTitledBorder("Block Tools"));
		
		//Block-tools
		CLog.info("\n=== Creating block tools....");
		
		CResReader entityReader = new CResReader();
		HashMap<String, BaseTemplate> factories = new HashMap<String, BaseTemplate>();
		
		ArrayList<CResNode> blockEntities = entityReader.readFile(BLOCK_ENTITIES_FILE);
		for (CResNode cResNode : blockEntities) {
			
			//template node?
			if(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE) != null &&  !cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE).isEmpty()){
				
				//template node template there?
				if(factories.get(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)) == null){
					try {
						factories.put(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE), (BaseTemplate) Class.forName(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)).newInstance());
					} catch (InstantiationException e) {
						CLog.error("failed to create template");
						CLog.error(e);
					} catch (IllegalAccessException e) {
						CLog.error("failed to create template");
						CLog.error(e);
					} catch (ClassNotFoundException e) {
						CLog.error("failed to create template");
						CLog.error(e);
					}
				}
				
				if(factories.get(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)) != null){
					//create
					String spriteSource = cResNode.getNodeValue(BaseTemplate.NODE_SPRITE);		
					
					MapObject val = factories.get(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)).create(cResNode);
					if(val != null){
						blockPanel.add(createObjectButton(val, spriteSource));
					}
				}			
			}
			else {
				CLog.error("failed to create '"+cResNode.getName()+"' - missing "+BaseTemplate.NODE_TEMPLATE);
			}
		}
	
		blockPanel.add(createObjectButton(new OverlayInterstateTeleporter(null), DEFAULT_PNG));
		blockPanel.add(createObjectButton(new DefaultMapObject(), ERASER_PNG));
		CLog.info("\n=== done");
		
		
		CLog.info("\n=== Creating terrain tools....");
		
		JPanel terrainPanel = new JPanel();
		//terrainPanel.setSize(new Dimension(30, 100));
		terrainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		terrainPanel.setBorder(BorderFactory.createTitledBorder("Terrain"));
		
		//terrain-tools
		ArrayList<CResNode> terrainEntities = entityReader.readFile(TERRAIN_ENTITIES_FILE);
		for (CResNode cResNode : terrainEntities) {	
			
			//template node?
			if(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE) != null &&  !cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE).isEmpty()){
				
				//template node template there?
				if(factories.get(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)) == null){
					try {
						factories.put(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE), (BaseTemplate) Class.forName(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)).newInstance());
					} catch (InstantiationException e) {
						CLog.error("failed to create template");
						CLog.error(e);
					} catch (IllegalAccessException e) {
						CLog.error("failed to create template");
						CLog.error(e);
					} catch (ClassNotFoundException e) {
						CLog.error("failed to create template");
						CLog.error(e);
					}
				}
				
				if(factories.get(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)) != null){
					//create
					String spriteSource = cResNode.getNodeValue(BaseTemplate.NODE_SPRITE);		
					
					MapObject val = factories.get(cResNode.getNodeValue(BaseTemplate.NODE_TEMPLATE)).create(cResNode);
					if(val != null){
						terrainPanel.add(createTerrainButton(val, spriteSource));
					}
				}			
			}
			else {
				CLog.error("failed to create '"+cResNode.getName()+"' - missing "+BaseTemplate.NODE_TEMPLATE);
			}
		}
		
		terrainPanel.add(createTerrainButton(new DefaultTerrain(), ERASER_PNG));
		CLog.info("\n=== done");
		
		add(blockPanel);
		add(terrainPanel);
		
	}
	
		
	public MapEditorPanel getMap() {
		return map;
	}



	public void setMap(MapEditorPanel map) {
		this.map = map;
	}

	private JButton createObjectButton(final MapObject object, String image){
		if(image != null){
			if(image.equals("")){
				image = DEFAULT_PNG;
			}
		} else {
			image = DEFAULT_PNG;
		}
			
		ImageIcon icon = new ImageIcon(image);
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		
		final JButton output = new JButton();
		output.setToolTipText(object.toString());
		output.setIcon(icon);
		output.setSize(new Dimension(15,15));
		output.setBorder(BorderFactory.createLineBorder(Color.BLACK,BUTTON_BORDER_SIZE));
		output.addActionListener(new ActionListener() {
			boolean toggled = false;
				@Override
			public void actionPerformed(ActionEvent arg0) {					
				if(map != null){
					
					if(!toggled){
						for (JButton btn : buttons) {
							if(btn != arg0.getSource()){
								//btn.setEnabled(false);
								btn.setBorder(BorderFactory.createLineBorder(Color.BLACK,BUTTON_BORDER_SIZE));
							}
						}
						output.setBorder(BorderFactory.createLineBorder(Color.RED,BUTTON_BORDER_SIZE));
						map.setCurrPointer(new MousePointer() {

							@Override
							public boolean onClick(RasterCell cell, MouseEvent event) {
								try {
									System.out.println(cell);
									MapObject newObject = object.newInstance(cell,event);
									newObject.setCurrentX(cell.getCx());
									newObject.setCurrentY(cell.getCy());
									cell.setAttachedObject(newObject);
								} catch (Exception e) {
									e.printStackTrace();
								}

								return false;
							}

							@Override
							public Dimension getPointerDimension() {
								return object.getObjectDimension();
							}
						});
						toggled = true;
					} else {
						output.setBorder(BorderFactory.createLineBorder(Color.BLACK,BUTTON_BORDER_SIZE));
						map.setCurrPointer(null);
						toggled = false;
					}
				}
			}
		});
		buttons.add(output);
		return output;
	}
	
	//TODO: redundanten code entfernen
	private JButton createTerrainButton(final MapObject object, String image){
		if(image != null){
			if(image.equals("")){
				image = DEFAULT_PNG;
			}
		} else {
			image = DEFAULT_PNG;
		}
			
		final JButton output = new JButton();
		output.setToolTipText(object.toString());
		output.setIcon(new ImageIcon(image));
		output.setSize(new Dimension(15,15));
		output.setBorder(BorderFactory.createLineBorder(Color.BLACK,BUTTON_BORDER_SIZE));
		output.addActionListener(new ActionListener() {
			boolean toggled = false;
				@Override
			public void actionPerformed(ActionEvent arg0) {					
				if(map != null){
					if(!toggled){
						for (JButton btn : buttons) {
							if(btn != arg0.getSource()){
								btn.setBorder(BorderFactory.createLineBorder(Color.BLACK,BUTTON_BORDER_SIZE));
							}
						}
						output.setBorder(BorderFactory.createLineBorder(Color.RED,BUTTON_BORDER_SIZE));
						map.setCurrPointer(new MousePointer() {

							@Override
							public boolean onClick(RasterCell cell, MouseEvent event) {
								object.setCurrentX(cell.getCx());
								object.setCurrentY(cell.getCy());
								cell.setTerrain(object);
								return false;
							}

							@Override
							public Dimension getPointerDimension() {
								return object.getObjectDimension();
							}

						});
						toggled = true;
					} else {
						output.setBorder(BorderFactory.createLineBorder(Color.BLACK,BUTTON_BORDER_SIZE));
						map.setCurrPointer(null);
						toggled = false;
					}
				}
			}
		});
		buttons.add(output);
		return output;
	}
}


