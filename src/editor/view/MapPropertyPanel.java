package editor.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import accessory.cres.CResNode;

import map.MapEntity;

public class MapPropertyPanel extends JPanel {

	private HashMap<String,JTextField> elements;
	private CResNode activeNode;
	
	public MapPropertyPanel() {
		setPreferredSize(new Dimension(100,250));
		elements = new HashMap<String, JTextField>();
		activeNode = null;
		
		setBorder(BorderFactory.createTitledBorder("Map properties"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
		
	public void loadContents(CResNode cResNode){
		removeAll();
		elements.clear();
		activeNode = cResNode;
		
		for(Entry<String,String> entry: cResNode.getNodes().entrySet()){
			
			JTextField tf = elements.get(entry.getKey());
			
			if(tf == null){
				tf = new JTextField(entry.getValue());
				tf.setBorder(BorderFactory.createTitledBorder(entry.getKey()));
				add(tf);
				
				
				elements.put(entry.getKey(), tf);
			}
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Entry<String, JTextField> entry : elements.entrySet()){
					activeNode.setNodeValue(entry.getKey(), entry.getValue().getText());
				}
			}
		});
		
		JButton discard = new JButton("Discard");
		discard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Entry<String, JTextField> entry : elements.entrySet()){
					
					entry.getValue().setText(activeNode.getNodeValue(entry.getKey()));
				}
			}
		});
		
		buttonPanel.add(save);
		buttonPanel.add(discard);
		
		add(buttonPanel);
	}
	
	
	
}
