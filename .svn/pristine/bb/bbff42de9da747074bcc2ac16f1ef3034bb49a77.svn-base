package game.view;

import game.models.Creature;
import game.models.CreatureFactory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import accessory.CLog;
import accessory.ImageStore;
import accessory.cres.CResNode;

public class CreatureRosterStatusFrame {
	
	
	private static final int PICTURE_OFFSET_PERCENT = 4;

	public static final Color BACKGROUND_COLOR = new Color(0.93f,0.93f,0.94f,1.0f);
	
	public static final String HP_BAR_COLOR_H = "hp_bar_color_h";
	public static final String HP_BAR_COLOR_S = "hp_bar_color_s";
	public static final String HP_BAR_COLOR_B = "hp_bar_color_b";
	
	public static final String MEDIUM_DAMAGE_TRESHOLD = "medium_dmg_treshhold";
	public static final String MEDIUM_DAMAGE_COLOR_H = "medium_dmg_color_h";
	public static final String MEDIUM_DAMAGE_COLOR_S = "medium_dmg_color_s";
	public static final String MEDIUM_DAMAGE_COLOR_B = "medium_dmg_color_b";

	public static final String CRITICAL_DAMAGE_TRESHHOLD = "critical_dmg_treshhold";
	public static final String CRITICAL_DAMAGE_COLOR_H = "critical_dmg_color_h";
	public static final String CRITICAL_DAMAGE_COLOR_S = "critical_dmg_color_s";
	public static final String CRITICAL_DAMAGE_COLOR_B = "critical_dmg_color_b";
	

	private static HashMap<String, Font> fontCache = new HashMap<String, Font>();
	
	private CResNode renderProperties;
	private Color hp_bar_color;
	private Color hp_bar_color_medium;
	private Color hp_bar_color_critical;

	private double hp_medium_treshhold;
	private double hp_critical_treshhold;
	
	public CreatureRosterStatusFrame(CResNode renderProperties){
		this.renderProperties = renderProperties;
		

		
		hp_bar_color = Color.getHSBColor(Float.parseFloat(renderProperties.getNodeValue(HP_BAR_COLOR_H)), Float.parseFloat(renderProperties.getNodeValue(HP_BAR_COLOR_S)), Float.parseFloat(renderProperties.getNodeValue(HP_BAR_COLOR_B)));
		hp_bar_color_medium = Color.getHSBColor(Float.parseFloat(renderProperties.getNodeValue(MEDIUM_DAMAGE_COLOR_H)), Float.parseFloat(renderProperties.getNodeValue(MEDIUM_DAMAGE_COLOR_S)), Float.parseFloat(renderProperties.getNodeValue(MEDIUM_DAMAGE_COLOR_B)));
		hp_bar_color_critical = Color.getHSBColor(Float.parseFloat(renderProperties.getNodeValue(CRITICAL_DAMAGE_COLOR_H)), Float.parseFloat(renderProperties.getNodeValue(CRITICAL_DAMAGE_COLOR_S)), Float.parseFloat(renderProperties.getNodeValue(CRITICAL_DAMAGE_COLOR_B)));
		
		hp_medium_treshhold = Double.parseDouble(renderProperties.getNodeValue(MEDIUM_DAMAGE_TRESHOLD));
		hp_critical_treshhold = Double.parseDouble(renderProperties.getNodeValue(CRITICAL_DAMAGE_TRESHHOLD));
		
		
	}
	
	
	public void render(Graphics g, Creature crt, Rectangle bounds){


		
		double currentHP = crt.getCurrentHP();
		double baseHP = crt.getBaseHP();
		double currentMP = crt.getCurrentMP();
		double baseMP = crt.getBaseMP();
				
		//frame container
		g.setColor(BACKGROUND_COLOR);
		g.fillRect((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
		
		//portrait
		int portrait_frame_x = bounds.x + bounds.width/100*(PICTURE_OFFSET_PERCENT/2);
		int portrait_frame_y = bounds.y + bounds.height/100*(PICTURE_OFFSET_PERCENT*2);
		int portrait_frame_width = (int) ((int)bounds.width/3.1);
		int portrait_frame_height = bounds.height-((bounds.height/100*PICTURE_OFFSET_PERCENT)*4);
		
		BufferedImage img = null;
		
		if(crt.getPortraitNode() != null){
			
			CResNode portraidNode = crt.getPortraitNode();
			
			if(portraidNode.getNodeValue("sprite") == null){
				img = ImageStore.getImage(crt.getSprite());
			}
			else {
				img = ImageStore.getImage(portraidNode.getNodeValue("sprite"));
			}
			
			if(portraidNode.getNodeValue("x") != null && portraidNode.getNodeValue("y") != null){
				
				try {
					img = img.getSubimage(	Integer.parseInt(portraidNode.getNodeValue("x")), 
							Integer.parseInt(portraidNode.getNodeValue("y"))/2+portrait_frame_height, 
							300, 
							187);
				}
				catch(Exception e){
					CLog.error(e);
				}
				
			}
		}
		else {
			img = ImageStore.getImage(crt.getSprite());
		}

		
		g.drawImage(img,portrait_frame_x, portrait_frame_y, portrait_frame_width, portrait_frame_height,null);

		
		g.setColor(Color.BLACK);
		//g.drawRect(	portrait_frame_x, portrait_frame_y, portrait_frame_width, portrait_frame_height);
		
		//status
		int left_bar_bound = portrait_frame_x+portrait_frame_width+5;
		int bar_lenght = (int) ((int)bounds.width-portrait_frame_width-(bounds.width/100*(PICTURE_OFFSET_PERCENT*1.2)));
		int bar_height = bounds.height/5;			
		
		//fonts
		Font nameFont;
		Font barFont;
		Font fontLvl ;
		
		int fontOffset = 5;
		
		if(FontStore.getFontByID(renderProperties.getNodeValue("label_font")) != null){
			Font base = FontStore.getFontByID(renderProperties.getNodeValue("label_font"));
			nameFont = base.deriveFont((float) (new Float(bar_height)+fontOffset));
			barFont = base.deriveFont(new Float(bar_height/2)+(fontOffset/2));
			fontLvl = base.deriveFont(new Float(bar_height-8)+fontOffset);
	
		}
		else {
			nameFont = new Font("Arial", Font.PLAIN, bar_height);
			barFont = new Font("Arial", Font.PLAIN, bar_height/2);
			fontLvl = new Font("Arial", Font.PLAIN, bar_height-8);
		}
		
		
		
		//name
		g.setFont(nameFont);
		
		int name_y = (int) (bounds.y+bar_height*1.5);
		g.drawString(crt.getName(), left_bar_bound-(fontOffset*2), name_y);
		
		//level
		g.setFont(fontLvl);
		g.drawString("lvl "+crt.getLevel(), (int) ((bounds.width+bounds.x-bar_height*1.5)-(fontOffset*2.2)), name_y);		
		
		
		//health
		if((currentHP/baseHP*100) < hp_critical_treshhold){
			g.setColor(hp_bar_color_critical);
		}
		else if((currentHP/baseHP*100) < hp_medium_treshhold){
			g.setColor(hp_bar_color_medium);
		}
		else {
			g.setColor(hp_bar_color);
		}
			
		int health_bar_y = name_y+10;
		
		g.fillRect(left_bar_bound, health_bar_y,  (int) (bar_lenght*(currentHP/baseHP)), bar_height);
		
		g.setColor(Color.BLACK);
		g.drawRect(left_bar_bound, health_bar_y, bar_lenght, bar_height);

		
		g.setFont(barFont);
		g.setColor(Color.white);
		
		g.drawString((int)currentHP+"/"+(int)baseHP, left_bar_bound+5, (int)(health_bar_y+(bar_height/1.4)));
		
		//mana
		g.setColor(Color.BLUE);
		g.fillRect(left_bar_bound, health_bar_y+bar_height+5, (int) (bar_lenght*(currentMP/baseMP)), bar_height);
		
		g.setColor(Color.BLACK);
		g.drawRect(left_bar_bound, health_bar_y+bar_height+5, bar_lenght, bar_height);
		
		g.setColor(Color.white);
		g.drawString((int)currentMP+"/"+(int)baseMP,left_bar_bound+5, (int) (health_bar_y+bar_height+5+(bar_height/1.4)));
		
	}

}
