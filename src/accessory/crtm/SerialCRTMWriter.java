package accessory.crtm;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import accessory.cres.CResNode;
import accessory.cres.CResWriter;

import map.MapEntity;
import map.RasterCell;
public class SerialCRTMWriter implements CRTMWriter {

	@Override
	public void writeToFile(String filename, MapEntity map, int xDimension, int yDimension, CResNode map_properties) throws Exception {
		 RasterCell[][] src = map.getRaster();
		 FileOutputStream file;
		 file = new FileOutputStream( filename );
		 ObjectOutputStream o = new ObjectOutputStream( file );
		 Dimension rastersize = new Dimension(xDimension, yDimension);
		
		 o.writeObject(rastersize);
		
			for(int i = 0 ; i < xDimension; i++){
				for (int j = 0; j < yDimension; j++) {
				     o.writeObject(src[i][j]);
				}
			}
		 
		 o.close();
		 
		 String propertiesFile = filename+CResWriter.FILE_EXTENSION;
		 CResWriter writer = new CResWriter();
		 writer.setAppend(false);
		 writer.writeNode(map.getProperties(), propertiesFile);
	}

}
