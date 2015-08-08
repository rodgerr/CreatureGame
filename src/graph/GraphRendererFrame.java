package graph;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GraphRendererFrame extends JFrame {

	private GraphRenderPanel panel;
	
	public GraphRendererFrame(String measure){
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setBounds(0, 0, 520, 200);
		
		panel = new GraphRenderPanel(500,200);
		panel.setMeasure(measure);
		getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	public void renderValue(int val){
		panel.addValue(val);
	}
}
