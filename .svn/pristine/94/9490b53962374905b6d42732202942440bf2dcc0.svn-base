package renderer;

import game.models.PlayerEntity;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import map.RasterCell;

public class GameBaseRenderer implements GameView{

	private static final Color BACKGROUND_COLOR = Color.DARK_GRAY.darker();
	private static final int XBUFFER = 3;
	private static final int YBUFFER = 2;
	

	private RasterCell[][] contents;
	PlayerEntity playerObject;
	
	public GameBaseRenderer( RasterCell[][] contents, PlayerEntity playerObject){
		this.playerObject = playerObject;
		this.contents = contents;
	}
	
	
	public void setContents(RasterCell[][] contents){
		this.contents = contents;
	}

	public void render(Graphics arg0, Container container, Rectangle pov) {
		
		arg0.setColor(BACKGROUND_COLOR);
		arg0.fillRect(0, 0, container.getWidth(), container.getHeight());
				
		arg0.setColor(Color.blue);
				
		int xpos = container.getWidth()/2-(RasterCell.RASTERSIZE/2);
		int ypos = container.getHeight()/2-(RasterCell.RASTERSIZE/2);
		
		int xcell = (xpos/RasterCell.RASTERSIZE);
		int ycell = (ypos/RasterCell.RASTERSIZE);

		for(int i = xcell-(xcell*2); i < (xcell+XBUFFER); i++){
			
			for(int j = ycell-(ycell*2); j < (ycell+YBUFFER); j++){
				try{				
					RasterCell cell = contents[(int) pov.getWidth()+i][(int) pov.getHeight()+j];
					cell.setCx(xcell+i);
					cell.setCy(ycell+j);
					cell.paintComponent(arg0, false);

				} catch(ArrayIndexOutOfBoundsException e){
					//System.out.print("["+ i +"/"+ j+"]" );
				}
			}
		}
		
		playerObject.paintComponent(arg0, contents[(int)pov.getWidth()][(int)pov.getHeight()]);
	}


	
}
