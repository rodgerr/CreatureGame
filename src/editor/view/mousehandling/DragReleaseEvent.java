package editor.view.mousehandling;

import map.RasterCell;

public interface DragReleaseEvent {
	
	public void release(RasterCell[][] cells);
	
}
