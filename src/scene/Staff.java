package scene;

import lib.ImageManager;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.R;

public class Staff implements IScene {

	AGameCanvas agc;
	ImageManager imageManager;
	boolean isEnd;

	float x;
	float[] y;

	public Staff(AGameCanvas agc, ImageManager imageManager) {
		this.agc = agc;
		this.imageManager = imageManager;
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("group", R.drawable.group);
		imageManager.load("voice", R.drawable.voice);
		imageManager.load("music", R.drawable.music);

		x = 55;
		y = new float[] { 1205, 2205, 3205 };
	}

	public void update() {
		for (int i = 0; i < 3; i++) {
			y[i] -= 5;
		}

		if (y[2] + 691 < 0 || agc.gcGetTouchTrigger()) {
			isEnd = true;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("group"), x, y[0]);
		g.drawImage(imageManager.getImage("voice"), x, y[1]);
		g.drawImage(imageManager.getImage("music"), x, y[2]);
	}

	public void finish() {
		imageManager.finish("group");
		imageManager.finish("voice");
		imageManager.finish("music");
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return EScene.TITLE;
	}

}
