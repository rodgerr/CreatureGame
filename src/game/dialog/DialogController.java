package game.dialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class DialogController {

	private static DialogRenderer renderUnit;
	private static Graphics graphicsContainer;
	
	private static ArrayList<String> activeDialog;
	private static int dialogIndex;
	
	
	
	public DialogController(){
	}
	
	public static void setGraphicContainer(Graphics g){
		graphicsContainer = g;
	}
	
	static public void showDialog(ArrayList<String> text){
			if(renderUnit == null){
				renderUnit = new DialogRenderer(Color.WHITE, Color.BLACK);
			}
			
			if(text != null && text.size() > 0){
				activeDialog = text; 
				dialogIndex = 0;
				renderUnit.paintDialog(graphicsContainer, activeDialog.get(dialogIndex));
			}
			
	}
	
	static public void setRenderUnitBounds(Rectangle r){
		if(renderUnit == null){
			renderUnit = new DialogRenderer(Color.WHITE, Color.BLACK);
		}
		renderUnit.setRenderBounds(r);
	}
	
	public boolean next(){
		if(activeDialog == null){
			return false;
		}
		dialogIndex++;
		if(dialogIndex == activeDialog.size()){
			return false;
		}
		else {
			renderUnit.paintDialog(graphicsContainer, activeDialog.get(dialogIndex));
			return true;
		}
	}
	
}
