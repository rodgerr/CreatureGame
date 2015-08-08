package accessory;

import graph.GraphRendererFrame;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import console.view.ConsoleFrame;

public class CLog {
	
	private static HashMap<String, GraphRendererFrame> graphRenderer = new HashMap<String, GraphRendererFrame>();
	private static ArrayList<CLogContainer> consoleContainer;
	private static ArrayList<String> logStack;
	
	public CLog(){
	}

	public static void info(String text){
		System.out.println(printAction(text));
	}
	
	public static void error(String text){
		System.err.println(printAction(text));
	}

	public static void error(Exception e){
		e.printStackTrace();
	}
	
	public static void renderGraph(String frame, int val){
		if(graphRenderer.get(frame) == null){
			GraphRendererFrame renderer = new GraphRendererFrame(frame);
			renderer.renderValue(40);
			renderer.renderValue(val);
			graphRenderer.put(frame, renderer);
		}
		else {
			graphRenderer.get(frame).renderValue(val);
		}
	}

	public static void addLogContainer(CLogContainer console) {
		if(consoleContainer == null){
			consoleContainer = new ArrayList<CLogContainer>();
		}
		consoleContainer.add(console); 
	}
	
	private static String printAction(String text){
		if(logStack == null){
			logStack = new ArrayList<String>();
		}
		logStack.add(text);
		
		if(consoleContainer != null){
			for(CLogContainer c : consoleContainer){
				c.printText(text);
			}
		}
		return text;
	}

	public static String getInfo() {
		String info = "";
		
	    Properties props = System.getProperties();
	    Enumeration<?> e = props.propertyNames();

	    while (e.hasMoreElements()) {
	      String key = (String) e.nextElement();
	      info += key + " -- " + props.getProperty(key)+ "\n";
	    }
		
		
		return info;
	}

	public static ArrayList<String> getLogStack() {
		return logStack;
	}
	
	
}