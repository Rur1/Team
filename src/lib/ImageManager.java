package lib;

import java.util.HashMap;
import java.util.Map;

import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Image;

public class ImageManager {

	AGameCanvas agc;
	Map<String, Image> images;

	public ImageManager(AGameCanvas agc) {
		this.agc = agc;
		images = new HashMap<String, Image>();
	}

	public void initialize() {
		images.clear();
	}

	public void load(String name, int id) {
		images.put(name, agc.gcGetImage(id, agc));
	}

	public Image getImage(String name) {
		return images.get(name);
	}

	public void dispose() {
		for (Image image : images.values()) {
			image.dispose();
		}
	}

	public void finish(String name) {
		images.get(name).dispose();
	}

}
