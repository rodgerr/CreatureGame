package accessory.cres;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import accessory.CLog;

public class CResReader {
	
	private String COMMENT_TOKEN = "//";
	
	public CResReader(){
		
	}
	
	public ArrayList<CResNode> readFile(String src){
		CLog.info("Parsing file '"+src+"'");
		ArrayList<CResNode> output =  new ArrayList<CResNode>();
		try {
			File source = new File(src);
			BufferedReader reader = new BufferedReader(new FileReader(source));
			
			String stock = "";
			String line;
			
			while ((line = reader.readLine()) != null){
				if(!line.trim().startsWith(COMMENT_TOKEN) && !line.startsWith(" ")){
					if(!line.trim().isEmpty()){
						stock += line;
					}
				}
			}
			
			String[] itemList = stock.split("}");
			for (int i = 0; i < itemList.length; i++) {
				String item = itemList[i];
				String itemName = item.substring(0, item.indexOf("{"));
				String plainElements = item.substring(item.indexOf("{"));
				String[] elements = plainElements.split(";");
				
				HashMap<String, String> elementMap = new HashMap<String, String>();
				for (int j = 0; j < elements.length; j++) {
					try{
						String[] elementTokens = elements[j].replaceAll("[{]", "").split("=");
						String elementNode = elementTokens[0].replaceAll("[^_A-Za-z0-9]", "");
						String elementValue = elementTokens[1].replaceFirst(" ", "");
						elementMap.put(elementNode, elementValue);
					}
					catch(ArrayIndexOutOfBoundsException e){
						CLog.error("error reading line '"+elements[j]+"' ("+e.getLocalizedMessage()+")");
					}

				}
				output.add(new CResNode(itemName, elementMap));
			}
			
		} catch (FileNotFoundException e) {
			CLog.error("File '"+src+"' not found");
			e.printStackTrace();
		} catch (IOException e) {
			CLog.error("Error Parsing file '"+src+"'");
			e.printStackTrace();
		}
		CLog.info("Done parsing. Result: "+output.toString());
		return output;
	}
	
	public HashMap<String, CResNode> readFileIntoMap(String src){
		HashMap<String, CResNode> output = new HashMap<String, CResNode>();
		for(CResNode node : readFile(src)){
			output.put(node.getName().trim(), node);
		}
		return output;
	}
	
	
}

