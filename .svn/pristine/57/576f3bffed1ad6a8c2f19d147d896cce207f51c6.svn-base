package editor;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import map.RasterCell;

public class ReaderApp {

	private static String FILENAME = "test.crtm";

	public static void main(String args[]) {
		try {

			
			
			FileInputStream file = new FileInputStream(FILENAME);
			ObjectInputStream o = new ObjectInputStream(file);
			Dimension dim = (Dimension) o.readObject();
			
			RasterCell[][] stock = new RasterCell[new Double(dim.getWidth()).intValue()][new Double(dim.getHeight()).intValue()];

			for (int i = 0; i < dim.getWidth(); i++) {
				for (int j = 0; j < dim.getHeight(); j++) {
					System.out.print("| ");
					RasterCell cell = (RasterCell) o.readObject();
					System.out.print(cell);
					stock[i][j] = cell;
				}
				System.out.print("\n");
			}
			
			// Dimension rastersize = (Dimension) o.readObject();
			o.close();

			// for()
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		}

	}

}
