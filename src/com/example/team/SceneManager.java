package com.example.team;

import java.util.HashMap;
import java.util.Map;

import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

public class SceneManager {

	Map<EScene, IScene> scenes;
	IScene currentScene;
	Fade fade;

	public SceneManager(AGameCanvas agc) {
		scenes = new HashMap<EScene, IScene>();
		currentScene = null;
		fade = new Fade(agc, 30);
	}

	public void add(EScene name, IScene scene) {
		scenes.put(name, scene);
	}

	public void change(EScene name) {
		if (currentScene != null) {
			currentScene.finish();
		}
		currentScene = scenes.get(name);
		currentScene.initialize();
		fade.initialize();
	}

	public void update() {
		if (currentScene == null) {
			return;
		}

		if (!currentScene.isEnd()) {
			currentScene.update();
		} else if (currentScene.next() == EScene.RESULT) {
			fade.shutterUpdate();
		}
		else { fade.update(); }

		if (fade.isEnd()) {
			change(currentScene.next());
		}
	}

	public void draw(Graphics g) {
		if (currentScene == null) {
			return;
		}
		currentScene.draw(g);
		if (currentScene.isEnd()) {
			fade.draw(g);
		}

		if (currentScene.next() == EScene.RESULT) {
			fade.shutterDraw(g);
		}
	}

	public void finish() {
		if (currentScene == null) {
			return;
		}
		currentScene.finish();
		fade.finish();
	}

}
