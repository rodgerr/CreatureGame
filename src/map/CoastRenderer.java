package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import editor.view.MapOrientation;

public class CoastRenderer {

	private static final int COAST_MULTI = 2;
	private static String RIGHT_COAST_PNG = "ressources/sprites/map/coast_right.png";
	private static String LEFT_COAST_PNG = "ressources/sprites/map/coast_left.png";
	private static String TOP_COAST_PNG = "ressources/sprites/map/coast_up.png";
	private static String DOWN_COAST_PNG = "ressources/sprites/map/coast_down.png";
	
	BufferedImage rightImage;
	BufferedImage leftImage;
	BufferedImage topImage;
	BufferedImage downImage;
	
	
	
	public CoastRenderer(){
		int x = 0;
		
		try {
			rightImage = ImageIO.read(new File(RIGHT_COAST_PNG));
			x++;
			leftImage = ImageIO.read(new File(LEFT_COAST_PNG));
			x++;
			topImage = ImageIO.read(new File(TOP_COAST_PNG));
			x++;
			downImage = ImageIO.read(new File(DOWN_COAST_PNG));
			x++;
			
		} catch (IOException e) {
			System.err.println("Error while reading Coast images ("+ x +")");
			e.printStackTrace();
		}
		
	}
	
	public void paintComponent(Graphics arg0, RasterCell inputLevel, RasterCell neighborLevel, MapOrientation direction){
		if(inputLevel.getTerrain().getSurfaceLevel() == SurfaceLevel.GROUND && neighborLevel.getTerrain().getSurfaceLevel() == SurfaceLevel.WATER){
			arg0.setColor(Color.getHSBColor(0.0f,0.40f,0.47f));
			switch (direction) {
			case BOTTOM:
				
				arg0.fillRect(inputLevel.getCx()*RasterCell.RASTERSIZE, (inputLevel.getCy()*RasterCell.RASTERSIZE)+(RasterCell.RASTERSIZE/COAST_MULTI), RasterCell.RASTERSIZE, RasterCell.RASTERSIZE/COAST_MULTI);
				break;
			
			case TOP:
				arg0.fillRect(inputLevel.getCx()*RasterCell.RASTERSIZE, inputLevel.getCy()*RasterCell.RASTERSIZE, RasterCell.RASTERSIZE, RasterCell.RASTERSIZE/COAST_MULTI);
				break;
				
			case RIGHT:
				arg0.fillRect((inputLevel.getCx()*RasterCell.RASTERSIZE)+(RasterCell.RASTERSIZE/COAST_MULTI), (inputLevel.getCy()*RasterCell.RASTERSIZE), RasterCell.RASTERSIZE, RasterCell.RASTERSIZE);
				break;
				
			case LEFT:
				arg0.fillRect(inputLevel.getCx()*RasterCell.RASTERSIZE, inputLevel.getCy()*RasterCell.RASTERSIZE, RasterCell.RASTERSIZE/COAST_MULTI, RasterCell.RASTERSIZE);
				break;
				
			default:
				break;
			}
			
		}
	}
}
