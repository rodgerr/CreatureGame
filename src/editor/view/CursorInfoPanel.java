package editor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import map.RasterCell;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import editor.view.mousehandling.MousePointer;

import java.awt.GridBagConstraints;

public class CursorInfoPanel extends JPanel implements InfoContainer{

	
	JLabel objectBox;
	JLabel terrainBox;
	JLabel cursorBox;
	
	public CursorInfoPanel(){
		setPreferredSize(new Dimension(300, 35));
		setLayout(new BorderLayout(0, 0));
		

		
		objectBox = new JLabel();
		objectBox.setPreferredSize(new Dimension(100,25));
		objectBox.setBorder(BorderFactory.createTitledBorder("Object"));
		add(objectBox, BorderLayout.CENTER);
		
		terrainBox = new JLabel();
		terrainBox.setPreferredSize(new Dimension(350,25));
		terrainBox.setBorder(BorderFactory.createTitledBorder("Terrain"));
		add(terrainBox, BorderLayout.WEST);
		
		cursorBox = new JLabel();
		cursorBox.setPreferredSize(new Dimension(175,25));
		cursorBox.setBorder(BorderFactory.createTitledBorder("Cursor"));
		add(cursorBox, BorderLayout.EAST);
	}
	
	@Override
	public void print(RasterCell arg) {
		cursorBox.setText("Cell: "+arg.getCx()+"/"+arg.getCy()+" Abs: "+(int)arg.getX()+"/"+(int)arg.getY());
		
		terrainBox.setText(arg.getTerrain().getClass().getSimpleName()+" ("+arg.getTerrain().toString()+")");
		objectBox.setText(arg.getAttachedObject().getClass().getSimpleName()+" ("+arg.getAttachedObject().toString()+")");
	}

	public void printToolInfo(MousePointer currPointer) {
		
	}
	


}
