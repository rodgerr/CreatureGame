package editor.view;

public class PropertyComponentFactory {

	public static PropertyComponent buildComponent(String key, String value){
		PropertyComponent component;
		
		if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
			component = new BooleanPropertyComponent();
		}
		else {
			component = new TextPropertyComponent();
		}
		
		component.setTitle(key);
		component.setValue(value);
		
		return component;
	}
	
}
