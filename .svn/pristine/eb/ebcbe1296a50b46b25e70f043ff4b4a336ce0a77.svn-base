package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphRenderPanel extends JPanel {

	private static final int STACK_SIZE = 20; 
	
	private Color renderColor;
	private Color bgColor;
	
	private String measure;
	
	private int renderWidth;
	private int renderHeight;
	private int maxValue;
	private int y_offset;
	
	private ArrayList<Integer> valueStack;
	
	public GraphRenderPanel(int renderWidth, int renderHeight){
		this.renderColor = Color.RED;
		this.bgColor = Color.DARK_GRAY.darker();
		this.renderWidth = renderWidth;
		this.renderHeight = renderHeight;
		this.valueStack = new ArrayList<Integer>();
		this.measure = "";
		
		setBackground(bgColor);
		
	}
	
	
	
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		arg0.setColor(renderColor);
		
		if(valueStack.size() < STACK_SIZE){
			
			int x_offset = 0;
			Point lastPoint = null;
			
			for(int val : valueStack){
				arg0.fillOval(x_offset, y_offset*val, 3, 3);
				
				if(lastPoint != null){
					arg0.drawLine(lastPoint.x, lastPoint.y, x_offset, y_offset*val);
				}
				
				//arg0.drawString(val+measure, x_offset, y_offset*val);
				
				lastPoint = new Point(x_offset, y_offset*val);
				x_offset = x_offset + (renderWidth/STACK_SIZE);				
			}
		}
		else {
			
			int x_offset = 0;
			Point lastPoint = null;
			
			for(int i = valueStack.size() - STACK_SIZE; i < valueStack.size(); i++){
				int val = valueStack.get(i);
				
				arg0.fillRect(x_offset, y_offset*val, 3, 3);
				
				if(lastPoint != null){
					arg0.drawLine(lastPoint.x, lastPoint.y, x_offset, y_offset*val);
				}
				lastPoint = new Point(x_offset, y_offset*val);
				
				x_offset = x_offset + (renderWidth/STACK_SIZE);		
			}
			
		}
		
		
	}
	
	public void addValue(int val){
		valueStack.add(val);
		if(val > maxValue){
			maxValue = val;
			y_offset = renderHeight / maxValue;
			
			if(valueStack.size() == 1){
				y_offset = y_offset/2;
			}
		}
		repaint();
	}
	
	public void setMeasure(String meas){
		measure = meas;
	}
}
