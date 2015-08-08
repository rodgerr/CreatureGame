package game.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DialogRenderer {
	
	private Rectangle renderBounds;
	
	private Color backgroundColor;
	private Color fontColor;
	
	private boolean active;
	
	public DialogRenderer(Color backgroundColor, Color fontColor){
		renderBounds = new Rectangle(0, 0, 50, 50);
		this.backgroundColor = backgroundColor;
		this.fontColor = fontColor;
		this.active = true;
	}
	
	

	public Rectangle getRenderBounds() {
		return renderBounds;
	}



	public void setRenderBounds(Rectangle renderBounds) {
		this.renderBounds = renderBounds;
	}



	public void paintDialog(Graphics g, String text){
		System.out.println(text);
		//g.setColor(backgroundColor);
		//g.fillRect((int)renderBounds.getX(), (int)renderBounds.getY(), (int)renderBounds.getWidth(), (int)renderBounds.getHeight());
		g.setFont(Font.getFont("Arial"));
		g.setColor(fontColor);
		g.drawString(text, 5, 5);
	}

}
