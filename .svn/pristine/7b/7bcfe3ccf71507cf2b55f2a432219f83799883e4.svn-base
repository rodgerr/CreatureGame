package accessory.cres;

import java.util.HashMap;

import accessory.CLog;

public class CResNode {

	private String name;
	private HashMap<String, String> nodes;
	
	public CResNode(){
		name = "";
		nodes = new HashMap<String, String>();
	}
	
	public CResNode(String name, HashMap<String, String> nodes){
		this.name = name;
		this.nodes = nodes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addNode(String nodeName, String nodeValue){
		nodes.put(nodeName, nodeValue);
	}

	public HashMap<String, String> getNodes() {
		return nodes;
	}

	public void setNodes(HashMap<String, String> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+name+":"+nodes+"]";
	}
	
	public String getNodeValue(String nodeName){
		if(nodes.get(nodeName) == null || nodes.get(nodeName).isEmpty()){
			//CLog.error("Node '"+nodeName+"' is empty ("+nodes.get(nodeName)+")");
		}
		return nodes.get(nodeName);
	}	
	
	public void setNodeValue(String node, String value){
		nodes.put(node, value);
	}
	
}
