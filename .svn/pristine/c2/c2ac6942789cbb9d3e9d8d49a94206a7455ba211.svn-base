package editor.view;


import javax.swing.JCheckBox;
import javax.swing.JComponent;

public class BooleanPropertyComponent extends JCheckBox implements PropertyComponent {

	@Override
	public JComponent getElement() {
		return this;
	}

	@Override
	public Object getValue() {
		return isSelected();
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof String){
			setSelected(Boolean.parseBoolean((String) value));
		}
		else{
			System.err.println("error setting boolean component value");
		}
	}

	@Override
	public void setTitle(String key) {
		setText(key);
	}

}
