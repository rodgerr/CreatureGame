package accessory.cres;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import accessory.CLog;

public class CResWriter {
	
	public static final String FILE_EXTENSION = ".cres";
	
	private boolean append;
	
	public CResWriter(){
		this(true);
	}
	
	public CResWriter(boolean append){
		this.append = append;
	}
	
	public void writeNode(CResNode arg, String target){
		CLog.info("\nWriting node:"+arg.getName()+" into "+target);
		CLog.info("Node cContents:"+arg);
		try {
			File file = new File(target);
			FileWriter writer = new FileWriter(file, append);
			
			for(String line : generateNodeText(arg)){
				writer.write(line);
				writer.write(System.getProperty("line.separator"));
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		CLog.info("-done writing");
	}
	
	
	public void writeNode(ArrayList<CResNode> arg, String target){
		for(CResNode element : arg){
			writeNode(element, target);
		}
	}
	
	
	public void writeNode(HashMap<String, CResNode> arg, String target){		
		for(Entry<String, CResNode> entry : arg.entrySet()){
			writeNode(entry.getValue(), target);		
			
		}	
	}
	
	
	private ArrayList<String> generateNodeText(CResNode arg){
		ArrayList<String> output = new ArrayList<String>();
		
		//text: nodename {
		output.add(arg.getName()+" {");
		
		for(Entry<String, String> entry : arg.getNodes().entrySet()) {
			
			//text: id = value;
			output.add(entry.getKey()+" = "+entry.getValue()+";");
		}
		
		//text: }
		output.add("}");
		
		
		return output;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

		
	
}
