package lib;

import java.util.HashMap;
import java.util.Map;

import net.pddin.agc.AGameCanvas;
import android.media.MediaPlayer;

public class Sound {

	AGameCanvas agc;
	Map<String, MediaPlayer> sounds;

	public Sound(AGameCanvas agc) {
		this.agc = agc;
		sounds = new HashMap<String, MediaPlayer>();
	}

	public void initialize() {
		sounds.clear();
	}

	public void load(String name, int id) {
		sounds.put(name, agc.gcGetMediaPlayer(id, agc));
	}

	public void play(String name, boolean looping, boolean stop) {
		if (sounds.get(name).isPlaying()) {
			if (!stop) return;
			sounds.get(name).pause();
		}
		// sounds.get(name).seekTo(0);
		sounds.get(name).start();
		sounds.get(name).setLooping(looping);
	}

	public void pause(String name) {
		sounds.get(name).pause();
	}

	public void stop(String name) {
		sounds.get(name).stop();
	}

	public void setVolume(String name, float volume) {
		sounds.get(name).setVolume(volume, volume);
	}

	public void release() {
		for (MediaPlayer mp : sounds.values()) {
			mp.stop();
			mp.release();
		}
	}

	public void finish(String name) {
		sounds.get(name).release();
	}

}
