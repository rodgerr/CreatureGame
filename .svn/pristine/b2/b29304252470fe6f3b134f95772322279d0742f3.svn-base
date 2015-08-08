package editor.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.MapObject;
import map.RasterCell;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class BlockInfoPanel extends JPanel{
	
	int activeBlockHash;
	
	private Dimension fieldDimension;
	private JLabel classNamePanel;
	private JLabel coordinatesLabel;
	private JLabel infoStringLabel;
	private JScrollPane scrollPane;
	
	
	private HashMap<String, PropertyComponent> activePropertyElements;
	private JPanel propertieContainer;
	private JLabel surfacelevelLbl;
	
	public BlockInfoPanel(){
		setBorder(BorderFactory.createTitledBorder("Cell Properties"));
		activeBlockHash = -1;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{16, 0};
		gridBagLayout.rowHeights = new int[]{37, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		classNamePanel = new JLabel("");
		classNamePanel.setBorder(BorderFactory.createTitledBorder("Class name"));
		fieldDimension = new Dimension(180,45);
		classNamePanel.setPreferredSize(fieldDimension);
		classNamePanel.setMaximumSize(fieldDimension);
		
		GridBagConstraints gbc_classNamePanel = new GridBagConstraints();
		gbc_classNamePanel.insets = new Insets(0, 0, 5, 0);
		gbc_classNamePanel.anchor = GridBagConstraints.WEST;
		gbc_classNamePanel.gridx = 0;
		gbc_classNamePanel.gridy = 0;
		add(classNamePanel, gbc_classNamePanel);
		
		coordinatesLabel = new JLabel("");
		coordinatesLabel.setBorder(BorderFactory.createTitledBorder("Coordinates [x/y]"));
		coordinatesLabel.setPreferredSize(fieldDimension);
		coordinatesLabel.setMaximumSize(fieldDimension);
		
		GridBagConstraints gbc_lblAsd = new GridBagConstraints();
		gbc_lblAsd.insets = new Insets(0, 0, 5, 0);
		gbc_lblAsd.gridx = 0;
		gbc_lblAsd.gridy = 1;
		add(coordinatesLabel, gbc_lblAsd);
		
		infoStringLabel = new JLabel("");
		infoStringLabel.setBorder(BorderFactory.createTitledBorder("Info String"));
		infoStringLabel.setPreferredSize(fieldDimension);
		infoStringLabel.setMaximumSize(fieldDimension);
		GridBagConstraints gbc_lblInfostring = new GridBagConstraints();
		gbc_lblInfostring.insets = new Insets(0, 0, 5, 0);
		gbc_lblInfostring.gridx = 0;
		gbc_lblInfostring.gridy = 2;
		add(infoStringLabel, gbc_lblInfostring);
		
		surfacelevelLbl = new JLabel("");
		surfacelevelLbl.setBorder(BorderFactory.createTitledBorder("Terrain Surface"));
		surfacelevelLbl.setPreferredSize(fieldDimension);
		surfacelevelLbl.setMaximumSize(fieldDimension);
		GridBagConstraints gbc_lblSurfacelevel = new GridBagConstraints();
		gbc_lblSurfacelevel.insets = new Insets(0, 0, 5, 0);
		gbc_lblSurfacelevel.gridx = 0;
		gbc_lblSurfacelevel.gridy = 3;
		add(surfacelevelLbl, gbc_lblSurfacelevel);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "EditableProperties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);
		
		propertieContainer = new JPanel();
		propertieContainer.setLayout(new BoxLayout(propertieContainer, BoxLayout.PAGE_AXIS));
		scrollPane.setViewportView(propertieContainer);
		
		activePropertyElements = new HashMap<String,PropertyComponent>();
	}




	public void printDetails(RasterCell arg){
		if(arg.hashCode() != activeBlockHash){
			ArrayList<RasterCell> list = new ArrayList<RasterCell>();
			list.add(arg);
			printDetails(list);
			activeBlockHash = arg.hashCode();
		}
	}
	

	public void printDetails(final ArrayList<RasterCell> workList) {	
		activePropertyElements.clear();
		propertieContainer.removeAll();
		
		final MapObject firstItem = workList.get(0).getAttachedObject();
		final MapObject lastItem = workList.get(workList.size()-1).getAttachedObject();
		
		if(firstItem != lastItem){
			classNamePanel.setText(lastItem.getClass().getSimpleName()+"("+workList.size()+")");
			coordinatesLabel.setText(firstItem.getCurrentX()+"/"+firstItem.getCurrentY()+" to "+lastItem.getCurrentX()+"/"+lastItem.getCurrentY());
			infoStringLabel.setText(lastItem.toString());
			surfacelevelLbl.setText(workList.get(workList.size()-1).getTerrain().getSurfaceLevel().toString());
		}
		else{
			classNamePanel.setText(lastItem.getClass().getSimpleName());
			coordinatesLabel.setText(lastItem.getCurrentX()+"/"+lastItem.getCurrentY());
			surfacelevelLbl.setText(workList.get(workList.size()-1).getTerrain().getSurfaceLevel().toString());
			infoStringLabel.setText(lastItem.toString());
		}

		propertieContainer.add(Box.createVerticalStrut(10));
		
		for(Entry<String,String> entry : lastItem.getProperties().entrySet()){
		
			
			PropertyComponent tf = activePropertyElements.get(entry.getKey());
			
			if(tf == null){
				tf = PropertyComponentFactory.buildComponent(entry.getKey(),entry.getValue());
				tf.getElement().setPreferredSize(new Dimension((int)fieldDimension.getWidth()-30, (int)fieldDimension.getHeight()));
				tf.getElement().setMaximumSize(tf.getElement().getPreferredSize());
				//tf.setH
				propertieContainer.add(tf.getElement());
			}
			
			activePropertyElements.put(entry.getKey(), tf);
			
		}
		
		if(activePropertyElements.size() > 0){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
			
			JButton save = new JButton("Save");
			save.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					for(RasterCell cell : workList){
						
						for(Entry<String,PropertyComponent> entry : activePropertyElements.entrySet()){
							cell.getAttachedObject().setProperty(entry.getKey(), entry.getValue().getValue().toString());	
						}
					}
				}
			});
			
			JButton discard = new JButton("Discard");
			discard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					for(Entry<String,String> entry : lastItem.getProperties().entrySet()){
						
						activePropertyElements.get(entry.getKey()).setValue(entry.getValue());
					}
				}
			});
			
			buttonPanel.add(save);
			buttonPanel.add(discard);
			
			propertieContainer.add(buttonPanel);
		}

	}




	public void clearDetails() {
		
	}

}
