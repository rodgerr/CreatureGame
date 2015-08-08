package console.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import accessory.Command;
import accessory.CLogContainer;

public class CommandHandler {

	
	public static final String HELP_COMMAND_ALT = "?";
	public static final String HELP_COMMAND = "help";
	public static final String LOAD_MAP_COMMAND = "loadmap";
	public static final String SET_RENDERENGINE_COMMAND = "renderengine";
	public static final String RENDERDIALOG_COMMAND = "renderdialog";
	public static final String QUIT_COMMAND = "quit";
	public static final String COMMANDS_COMMAND = "commands";
	public static final String SHOW_FPS_COMMAND = "fps";
	public static final String SHOW_FPS_GRAPH_COMMAND = "fps_graph";
	public static final String SHOW_MAP_PROPERTIES_COMMAND = "map_properties";
	public static final String SHOW_X_Y_COMMAND = "show_xy";

	static {
		Command helpcommand = new Command() {
			
			@Override
			public String execute(String[] args) {
				String text = "";
				for(Entry entry : getCommandStack().entrySet()){
					text += "["+entry.getKey().toString()+"] -- " +entry.getValue().toString()+" \n";
				}
				return text;
			}
		};
		addCommand(HELP_COMMAND, helpcommand);
		addCommand(HELP_COMMAND_ALT, helpcommand);
	}
	
	private static HashMap<String, Command> commandStack;
	
		
	public static void addCommand(String ident, Command command){
		getCommandStack().put(ident, command);
	}
	
	
	public void executeCommand(String ident){
		if(getCommandStack().get(ident) != null){
			commandStack.get(ident).execute(new String[0]);
		}
	}
	
	public void executeCommand(String ident, CLogContainer container){
		executeCommand(ident, new String[0], container);
	}
	
	public void executeCommand(String ident, String[] args){
		if(getCommandStack().get(ident) != null){
			commandStack.get(ident).execute(args);
		}
	}
	
	public void executeCommand(String ident, String[] args, CLogContainer container){
		container.printText(ident+";");
		if(getCommandStack().get(ident) != null){			
			container.printText(commandStack.get(ident).execute(args));
		} else {
			container.printText("Command not found: " + ident+";");
		}
	}
	
	private static HashMap<String, Command> getCommandStack(){
		if(commandStack == null){
			commandStack = new HashMap<String, Command>();
		}
		return commandStack;
	}
	
}
