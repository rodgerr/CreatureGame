package editor.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import map.MapEntity;
import map.MapStore;
import map.RasterCell;
import map.blocks.base.DefaultMapObject;
import renderer.CollisionRenderer;
import renderer.DefaultRenderer;
import renderer.MapRenderer;
import renderer.PropertyRenderer;
import renderer.SurfaceRenderer;
import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;
import accessory.cres.CResWriter;
import accessory.crtm.FileHandler;
import editor.view.mousehandling.DragReleaseEvent;
import editor.view.mousehandling.MouseOverHandler;
import game.GameApp;

import javax.swing.JSplitPane;

public class MainWindow extends JFrame {

	private static final String WORKPATH_PROP = "workpath";
	private static final String INDEX_FILE_PROP = "index_file"; 

	public static final String RESSOURCE_ROOT = "ressources/";
	private static final String WORK_PROPERTIES_FILE = "ressources/appdata/work.properties";
	
	//toolbar icons
	private static final int ICON_HEIGHT = 23;
	private static final int ICON_WIDTH = 21;
	private static final String NEW_ICON = "ressources/sprites/editor/new.png";
	private static final String SAVE_ICON = "ressources/sprites/editor/save.png";
	private static final String LOAD_ICON = "ressources/sprites/editor/load.png";
	private static final String RASTER_ICON = "ressources/sprites/editor/raster.png";
	private static final String LAUNCH_ICON = "ressources/sprites/editor/launch.png";
	
	//work variables
	private Properties properties;
	private String workPath;
	
	private HashMap<JPanel, MapEditorPanel> maps;
	private HashMap<JPanel, MapEditorPanel> maps_properties;
	
	private CreateDialog dialog;
	private JTabbedPane jtp;
	private JMenuItem menubtnSave;
		
	//view elements
	private ToolsPanel toolsPanel;
	private BlockInfoPanel blockInfoPanel;
	private CursorInfoPanel cursorPanel;
	private MapPropertyPanel mapPropertiePanel;
	
	public MainWindow(String title) {
		setTitle(title);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setSize(new Dimension(WIDTH, HEIGHT));
		
		
		properties = new Properties();
		try {
		  properties.load(new FileInputStream(WORK_PROPERTIES_FILE));
		  workPath = (String) properties.get(WORKPATH_PROP);
		  
		  if(workPath == null || workPath.isEmpty()){
			  throw new Exception();
		  }
		}
		catch (Exception e){
			CLog.error("Error while reading work properties file:"+WORK_PROPERTIES_FILE);
			workPath =  MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			
		}
		
		CLog.info("working with path"+workPath);
		
		//rendermodes
		HashMap<String, MapRenderer> renderOptions = new HashMap<String, MapRenderer>();
		renderOptions.put("default", new DefaultRenderer());
		renderOptions.put("surface", new SurfaceRenderer());
		renderOptions.put("collision", new CollisionRenderer());
		renderOptions.put("coordinates", new PropertyRenderer());
		

		maps = new HashMap<JPanel, MapEditorPanel>();
		maps_properties = new HashMap<JPanel, MapEditorPanel>();
		
		//blockproperties
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		blockInfoPanel = new BlockInfoPanel();	
		mapPropertiePanel = new MapPropertyPanel();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mapPropertiePanel, blockInfoPanel);
		//splitPane.setDividerLocation(0.5);
		splitPane.setOneTouchExpandable(true);

		getContentPane().add(splitPane, BorderLayout.EAST);

		//tools
		toolsPanel = new ToolsPanel();
		getContentPane().add(toolsPanel, BorderLayout.WEST);
		
		//cursor info
		cursorPanel = new CursorInfoPanel();
		getContentPane().add(cursorPanel, BorderLayout.SOUTH);
		
		//TABPANE
		jtp = new JTabbedPane();
        getContentPane().add(jtp);     	
		//jtp.addTab("Unknown.crtm", createWorkingCanvas(createEmptyRaster(500, 500)));
		jtp.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				JTabbedPane selection = (JTabbedPane) arg0.getSource();	
				toolsPanel.setMap(maps.get(selection.getSelectedComponent()));
				mapPropertiePanel.loadContents(maps.get(selection.getSelectedComponent()).getMapProperties());
			}
		});
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar();
		FlowLayout fl_menuBar = new FlowLayout();
		fl_menuBar.setAlignment(FlowLayout.LEFT);
		menuBar.setLayout(fl_menuBar);
		setJMenuBar(menuBar);

		//new 
		menuBar.add(createMenuItem("New", KeyEvent.VK_C, NEW_ICON, new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				showCreateDialog();
			}
		}));
		
		//save
		menubtnSave = createMenuItem("Save", KeyEvent.VK_S, SAVE_ICON, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(maps.get(jtp.getSelectedComponent()).getContentpath() != null){
						saveAction(maps.get(jtp.getSelectedComponent()).getContentpath());
					}
				else {
					final JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File(workPath));
					fc.setFileFilter(new FileFilter() {
						
						public String getDescription() {
							return "Creature Map Files (*.crtm)";
						}
						
						@Override
						public boolean accept(File arg0) {
							if(arg0 == null) return false;
							if(arg0.isDirectory()) return true;
							return arg0.getName().toLowerCase(). endsWith("crtm");
						}
					});
					
					int returnVal = fc.showOpenDialog(maps.get(jtp.getSelectedComponent()));
					if(returnVal == JFileChooser.APPROVE_OPTION){
						String filePath = fc.getSelectedFile().toString();
						if(!filePath.contains(".crtm")){
							filePath = filePath +".crtm";
						}
						saveAction(filePath);
					}	
				}
			}
		});
		menubtnSave.setEnabled(false);
		menuBar.add(menubtnSave);
		
		//load
		menuBar.add(createMenuItem("Load", KeyEvent.VK_L, LOAD_ICON, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();		
				fc.setCurrentDirectory(new File(workPath));
				fc.setFileFilter(new FileFilter() {
					
					public String getDescription() {
						return "Creature Map Files (*.crtm)";
					}
					
					@Override
					public boolean accept(File arg0) {
						if(arg0 == null) return false;
						if(arg0.isDirectory()) return true;
						return arg0.getName().toLowerCase().endsWith("crtm");
					}
				});				
				int returnVal = fc.showOpenDialog(maps.get(jtp.getSelectedComponent()));
				if(returnVal == JFileChooser.APPROVE_OPTION){
					CLog.info("opening..." +fc.getSelectedFile());
					workPath = fc.getSelectedFile().toString();
					
					MapEntity val = openRasterFile(fc.getSelectedFile().toString());
					
					if(val != null){
						jtp.addTab(fc.getSelectedFile().toString(), createWorkingCanvas(val));
						jtp.setSelectedIndex(jtp.getTabCount()-1);
						maps.get(jtp.getSelectedComponent()).setContentpath(fc.getSelectedFile().toString());
					}
				}	
			}
		}));
		
		//launch
		JMenuItem launchItem = createMenuItem("Launch", KeyEvent.VK_L, LAUNCH_ICON, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MapEntity map = maps.get(jtp.getSelectedComponent()).getMap();
				if(map != null){
					GameApp.launchGame(new MapEntity(map));				
				}
			}
		});
		launchItem.setEnabled(false);
		menuBar.add(launchItem);
		
		
		//raster
		menuBar.add(createMenuItem("Raster", KeyEvent.VK_R, RASTER_ICON, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Entry entry : maps.entrySet()){
					MapEditorPanel pane = (MapEditorPanel)entry.getValue();
					pane.setShowRaster(!pane.isShowRaster());
				}
			}
		}));
		
		//rendermodes
		JMenu renderMenu = new JMenu("Rendermodes");
		renderMenu.setMnemonic(KeyEvent.VK_E);
		//renderMenu.setBorder(BorderFactory.createEtchedBorder());
		renderMenu.getAccessibleContext().setAccessibleDescription("accessiblecontext goes here");
		menuBar.add(renderMenu);
		
		
		for(final Entry<String, MapRenderer> entry : renderOptions.entrySet()){
			
			JMenuItem renderItem = new JMenuItem(entry.getKey());
			renderItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					
					for(Entry mapEntry : maps.entrySet()){
							MapEditorPanel pane = (MapEditorPanel)mapEntry.getValue();
							pane.setRenderer(entry.getValue());
					}
				}
			});
			renderMenu.add(renderItem);
		}
		
		//tools
		JMenu toolsMenu = new JMenu("Tools");
		toolsMenu.setMnemonic(KeyEvent.VK_T);
		menuBar.add(toolsMenu);
		
		//add to map index
		JMenuItem addToMapIndex = new JMenuItem("create Index Entry");
		addToMapIndex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createIndexEntryAction();
			}
		});
		toolsMenu.add(addToMapIndex);
		
		JMenuItem saveLogToolEntry = new JMenuItem("save Editor Log");
		saveLogToolEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveLogAction();
			}
		});
		toolsMenu.add(saveLogToolEntry);
		
		pack();
		setSize(new Dimension(	(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.9),
								(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9)));
	}

	protected void saveLogAction() {
		final String fileExtension = ".txt"; 
		JFileChooser fc = new JFileChooser();		
		fc.setCurrentDirectory(new File(properties.getProperty(INDEX_FILE_PROP)));
		fc.setFileFilter(new FileFilter() {
			
			public String getDescription() {
				return "Text files (*"+fileExtension+")";
			}
			
			@Override
			public boolean accept(File arg0) {
				if(arg0 == null) return false;
				if(arg0.isDirectory()) return true;
				return arg0.getName().toLowerCase().endsWith(fileExtension);
			}
		});		
		
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			String path = fc.getSelectedFile().toString(); 
			if(!path.endsWith(fileExtension)){
				path += fileExtension;
			}
			
		    File file = new File(path);
		    FileWriter writer;
		     try {

		       writer = new FileWriter(file ,false);
		       
		       for(String line : CLog.getLogStack()){
		    	   writer.write(line);
		    	   writer.write(System.getProperty("line.separator"));
		       }

		       writer.flush();
		       writer.close();
			
			
		     }
		     catch(IOException e){
		    	 JOptionPane.showMessageDialog(this, 	"Error while trying to save log file! (Message:"+e.getMessage()+")",
    			 										"Error", JOptionPane.ERROR_MESSAGE);
		    	 CLog.error(e);
		     }
		}
	}
	
	private void createIndexEntryAction() {
		final JFileChooser fc = new JFileChooser();		
		fc.setCurrentDirectory(new File(properties.getProperty(INDEX_FILE_PROP)));
		fc.setFileFilter(new FileFilter() {
			
			public String getDescription() {
				return "Map Index Files (*.cres)";
			}
			
			@Override
			public boolean accept(File arg0) {
				if(arg0 == null) return false;
				if(arg0.isDirectory()) return true;
				return arg0.getName().toLowerCase().endsWith("cres");
			}
		});				
		int returnVal = fc.showOpenDialog(maps.get(jtp.getSelectedComponent()));
		if(returnVal == JFileChooser.APPROVE_OPTION){
			String filePath = fc.getSelectedFile().toString();

			properties.setProperty(INDEX_FILE_PROP, filePath);
			saveProperties();
				
			CResReader reader = new CResReader();
			HashMap<String,CResNode> nodes = reader.readFileIntoMap(filePath);
			CResNode files = nodes.get(MapStore.FILES_NODE);
			if(files == null){
				files = new CResNode();
			}
			
			String contentPath = maps.get(jtp.getSelectedComponent()).getContentpath();
			String mapName = maps.get(jtp.getSelectedComponent()).getMapProperties().getNodeValue(MapEntity.NAME_NODE);
			
			contentPath = contentPath.replace("\\" , "/");
			if(contentPath.contains(RESSOURCE_ROOT)){
				contentPath = contentPath.substring(contentPath.indexOf(RESSOURCE_ROOT));
			}
			

			JTextField new_text = new JTextField(contentPath);
			JOptionPane new_pane = new JOptionPane(new_text);
			JDialog new_dialog = new_pane.createDialog(this, "Creating alias for map '"+mapName+"' from file:");
			new_dialog.setModal(true);
			new_dialog.setVisible(true);
			new_dialog.toFront();
			
			
			files.addNode(mapName, contentPath);
			
			CResWriter resWriter = new CResWriter(false);
			resWriter.writeNode(files, filePath);
			
		}
	}

	private JMenuItem createMenuItem(String title, int key, String iconSource, ActionListener action) {
		JMenuItem btn = new JMenuItem(title);
		btn.setMnemonic(key);
		btn.setIcon(getResizedIcon(iconSource));
		//btn.setBorder(BorderFactory.createEtchedBorder());
		btn.addActionListener(action);
		btn.setSize(0, 40);
		
		return btn;
	}

	private void saveAction(String path) {
		maps.get(jtp.getSelectedComponent()).saveRaster(path);
		jtp.setTitleAt(jtp.getSelectedIndex(), path);
		workPath = path;
		
		properties.setProperty(WORKPATH_PROP, path);
		saveProperties();
	}

	private void saveProperties() {		
		try {
			properties.store(new FileOutputStream(WORK_PROPERTIES_FILE), null);
		} catch (Exception e) {
			CLog.error(e);
		}
	}
	
	public void showCreateDialog() {
		menubtnSave.setEnabled(true);
		dialog = new CreateDialog(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				MapEntity map = createEmptyRaster(dialog.getEnteredWidth(),dialog.getEnteredHeight());
				
				jtp.addTab("Unknown.crtm", createWorkingCanvas(map));
				dialog.dispose();
			}
		});
	}
	
	private MapEntity createEmptyRaster(int width, int height){
		MouseOverHandler defaultHandle = new MouseOverHandler() {
			@Override
			public void entered(RasterCell arg0, MouseEvent arg1) {
				cursorPanel.print(arg0);
				cursorPanel.printToolInfo(maps.get(jtp.getSelectedComponent()).getCurrPointer());
			}
			@Override
			public void clicked(RasterCell arg0, MouseEvent arg1) {
				blockInfoPanel.printDetails(arg0);
				blockInfoPanel.repaint();
			}
		};
		
		RasterCell[][] raster = new RasterCell[width][height];

		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				raster[i][j] = new RasterCell(i, j);
				raster[i][j].addMouseOverHandler(defaultHandle);
			}
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				RasterCell tmp = raster[i][j];
				
				if(i > 0){
					raster[i - 1][j].getNeighbors().put(MapOrientation.RIGHT, tmp);
				}
				if(j > 0){
					raster[i][j - 1].getNeighbors().put(MapOrientation.BOTTOM, tmp);
				}
				
				if((i + 1) < width){
					raster[i + 1][j].getNeighbors().put(MapOrientation.LEFT, tmp);
				}
				if((j + 1) < height){
					raster[i][j + 1].getNeighbors().put(MapOrientation.TOP, tmp);
				}
			}
		}
		
		CLog.info("["+width+"x"+height+"="+(height*width)+"] cells created");
		
		return new MapEntity(raster);
	}
	
	private MapEntity openRasterFile(String fileName){
		menubtnSave.setEnabled(true);
		
		//Mousehandler werden nicht serialisiert da sie Editor spezifisch sind, deswegen hier neu hinzufügen;
		MouseOverHandler defaultHandle = new MouseOverHandler() {
			@Override
			public void entered(RasterCell arg0, MouseEvent arg1) {
				cursorPanel.print(arg0);
			}

			@Override
			public void clicked(RasterCell arg0, MouseEvent arg1) {
				if(arg1.getButton() == 1){
					blockInfoPanel.printDetails(arg0);
				} else {
					blockInfoPanel.clearDetails();
				}
				
			}
		};
		FileHandler fhandler = new FileHandler();
		try {
			return fhandler.readMapFile(fileName, defaultHandle);
		} catch (InvalidClassException e) {
			e.printStackTrace();
			CLog.error("Incompatible Block version found!");
			CLog.error(e);
			
			JOptionPane.showMessageDialog(this, "Incompatible Block version found! \n"+e.getLocalizedMessage(), "Fatal", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
 
	public JPanel createWorkingCanvas(MapEntity map) {
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout(0, 0));
		
		MapEditorPanel mapPane = new MapEditorPanel(map);
		
		toolsPanel.setMap(mapPane);
		mapPropertiePanel.loadContents(map.getProperties());
		
		JScrollPane scrollPane = new JScrollPane(mapPane);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		container.add(scrollPane, BorderLayout.CENTER);
		maps.put(container, mapPane);
		
		mapPane.addDragReleaseEvent(new DragReleaseEvent() {
			
			@Override
			public void release(RasterCell[][] cells) {
		
				if(cells.length > 0){
					//multi edit only possible due to same cell classes
					Class rootClass = cells[0][0].getAttachedObject().getClass();
					ArrayList<RasterCell> workList = new ArrayList<RasterCell>();
					
					
					for(int i = 0; i < cells.length; i++){
						for(int j = 0; j < cells[i].length; j++){
							if(rootClass == DefaultMapObject.class && cells[i][j].getAttachedObject().getClass() != DefaultMapObject.class){
								rootClass = cells[i][j].getAttachedObject().getClass();
								workList = new ArrayList<RasterCell>();
							}
							if(cells[i][j].getAttachedObject().getClass() == rootClass){
								workList.add(cells[i][j]);
							}
						}
					}
					blockInfoPanel.printDetails(workList);
				}
				else {
					blockInfoPanel.clearDetails();
				}
				
			}
		});
		
		
		//cell highlighting on mouseover;
		mapPane.addDragReleaseEvent(new DragReleaseEvent() {
			
			ArrayList<RasterCell> activeCells; 
			
			@Override
			public void release(RasterCell[][] cells) {
				
				
				if(activeCells == null){
					activeCells = new ArrayList<RasterCell>();
				} else {
					for(RasterCell cell : activeCells){
						cell.setHighlighted(false);
					}
					activeCells = new ArrayList<RasterCell>();
				}
				
				if(toolsPanel.getMap().getCurrPointer() == null){
					for(int i = 0; i < cells.length; i++){
						for(int j = 0; j < cells[i].length; j++){
							cells[i][j].setHighlighted(true);
							activeCells.add(cells[i][j]); 
						}
					}
				}
			}

		});
		
		return container;
	}

	private Icon getResizedIcon(String image){
		ImageIcon icon = new ImageIcon(image);
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance( ICON_WIDTH, ICON_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
		return new ImageIcon( newimg );
	}
	
}
