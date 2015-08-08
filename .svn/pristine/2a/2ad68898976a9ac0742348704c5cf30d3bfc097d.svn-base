package editor;

import java.util.ArrayList;

import accessory.cres.CResNode;
import accessory.cres.CResReader;
import accessory.cres.CResWriter;

public class writerTest {

	public static void main(String args[]){
		
		String target = "writertest.cres";
		
		CResWriter writer = new CResWriter();
		
		CResNode node = new CResNode();
		node.setName("testnodename1");
		node.addNode("testnode1", "testnodevalue1");
		node.addNode("testnode2", "testnodevalue2");
		node.addNode("testnode3", "testnodevalue3");
		
		CResNode node2 = new CResNode();
		node2.setName("testnodename2");
		node2.addNode("valueee", "1");
		
		ArrayList<CResNode> values = new ArrayList<CResNode>();
		values.add(node);
		values.add(node2);
		
		writer.writeNode(values, target);
		
		CResReader reader = new CResReader();
		ArrayList<CResNode> readval = reader.readFile(target);
		System.out.println(readval);
		
	}
	
}
