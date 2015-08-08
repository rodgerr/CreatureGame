package editor.view;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class TextPropertyComponent extends JTextField implements PropertyComponent{

	
	
	@Override
	public JComponent getElement() {
		return this;
	}

	@Override
	public Object getValue() {
		return getText();
	}

	@Override
	public void setValue(Object value) {
		setText(value.toString());
	}

	@Override
	public void setTitle(String key) {
		setBorder(BorderFactory.createTitledBorder(key));
	}
}
