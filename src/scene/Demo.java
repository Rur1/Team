package scene;

import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import android.view.View;
import android.widget.VideoView;

import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.MainActivity;

public class Demo implements IScene {

	AGameCanvas agc;
	boolean isEnd;

	VideoView videoView;

	public Demo(AGameCanvas agc, VideoView videoView) {
		this.agc = agc;
		this.videoView = videoView;
	}

	public void initialize() {
		isEnd = false;
	}

	public void update() {
		if (!videoView.isPlaying()) {
			((MainActivity) agc.getContext()).postRunnable(new Runnable() {
				public void run() {
					videoView.setVisibility(View.VISIBLE);
					videoView.start();
				}
			});
		}

		if (agc.gcGetTouchTrigger()) {
			((MainActivity) agc.getContext()).postRunnable(new Runnable() {
				public void run() {
					videoView.pause();
					videoView.setVisibility(View.INVISIBLE);
				}
			});

			isEnd = true;
		}
	}

	public void draw(Graphics g) {

	}

	public void finish() {

	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return EScene.TITLE;
	}

}
