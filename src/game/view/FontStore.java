package game.view;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import accessory.CLog;
import accessory.cres.CResNode;
import accessory.cres.CResReader;

public class FontStore {
	
	private static final String FONT_NODE = "fonts";
	private static final String FONT_INDEX_FILE = "ressources/fonts/index.cres";
	
	private static HashMap<String, Font> fontStore;
	
	public static Font getFontByID(String id){
		if(fontStore == null){
			fontStore = new HashMap<String, Font>();
			
			loadFonts();
		}
		return fontStore.get(id);
	}

	private static void loadFonts() {
		CResReader resReader = new CResReader();
		CResNode fontNode = resReader.readFileIntoMap(FONT_INDEX_FILE).get(FONT_NODE);
		
		for(Entry<String,String> entry : fontNode.getNodes().entrySet()){

			try{
				InputStream fin = new BufferedInputStream(new FileInputStream(entry.getValue()));
				Font f = Font.createFont(Font.TRUETYPE_FONT, fin);
				fontStore.put(entry.getKey(), f);
			}
			catch(Exception e){
				CLog.error("Error while trying to create font "+entry.getValue() + "(" + e.getMessage() + ")");
				e.printStackTrace();
			}
			
			
		}
		
	}

}
