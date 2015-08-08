package accessory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageStore {

	static HashMap<String, BufferedImage> storedImages;
	HashMap<String, BufferedImage> subImages;
	
	public static BufferedImage getImage(String url){
		//init store
		if(storedImages == null){
			storedImages = new HashMap<String, BufferedImage>();
			
		}
		
		//load image in store if not there already?
		if(storedImages.get(url) == null){
			 try {
				storedImages.put(url, ImageIO.read(new File(url)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return storedImages.get(url);
	}
		
}
