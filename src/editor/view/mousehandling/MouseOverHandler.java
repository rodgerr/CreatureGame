package editor.view.mousehandling;

import java.awt.event.MouseEvent;
import java.io.Serializable;

import map.RasterCell;

public interface MouseOverHandler extends Serializable{

	public void entered(RasterCell arg0, MouseEvent arg1);
	public void clicked(RasterCell arg0, MouseEvent arg1);
}
