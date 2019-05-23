package com.example.team;

import lib.ImageManager;
import lib.Sound;
import lib.Timer;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import scene.Demo;
import scene.Result;
import scene.Staff;
import scene.Stage1;
import scene.Stage2;
import scene.Stage3;
import scene.Stage4;
import scene.Stage5;
import scene.StageSelect;
import scene.Story1;
import scene.Story2;
import scene.Story3;
import scene.Story4;
import scene.Story5;
import scene.Title;
import android.content.Context;
import android.widget.VideoView;

public class Main extends AGameCanvas {

	VideoView videoView;

	ImageManager imageManager;
	Sound sound;

	Timer timer;

	CharaManager charaManager;
	Stage1 stage1;
	Stage2 stage2;
	Stage3 stage3;
	Stage4 stage4;
	Stage5 stage5;
	SceneManager sceneManager;

	public Main(Context context, VideoView videoView) {
		super(context, 800, 1205);
		this.videoView = videoView;
	}

	public void gameInit() {
		imageManager = new ImageManager(this);
		imageManager.load("slash1", R.drawable.slash1);
		imageManager.load("slash2", R.drawable.slash2);
		imageManager.load("slash3", R.drawable.slash3);
		imageManager.load("effect", R.drawable.effect);
		imageManager.load("circle", R.drawable.ring_rotation);
		imageManager.load("blueCircle", R.drawable.ring_rotation2);
		imageManager.load("bullet", R.drawable.bullet);

		sound = new Sound(this);
		sound.initialize();
		sound.load("titleBGM", R.raw.titlebgm);
		sound.load("sad", R.raw.sad);
		sound.load("gameplay", R.raw.gameplay1);
		sound.load("gameplay2", R.raw.gameplay2);
		sound.load("drum", R.raw.drum);
		sound.load("slash", R.raw.slash);
		sound.load("damage", R.raw.damage);
		sound.load("guard1", R.raw.g01);
		sound.load("guard2", R.raw.g02);
		sound.load("guard3", R.raw.g03);
		sound.load("renda", R.raw.renda);
		sound.load("tackle", R.raw.tackle);

		timer = new Timer();

		charaManager = new CharaManager(this, imageManager, sound);
		stage1 = new Stage1(this, imageManager, sound, timer, charaManager);
		stage2 = new Stage2(this, imageManager, sound, timer, charaManager);
		stage3 = new Stage3(this, imageManager, sound, timer, charaManager);
		stage4 = new Stage4(this, imageManager, sound, timer, charaManager);
		stage5 = new Stage5(this, imageManager, sound, timer, charaManager);
		sceneManager = new SceneManager(this);
		sceneManager.add(EScene.TITLE, new Title(this, imageManager, sound));
		sceneManager.add(EScene.STAGESELECT, new StageSelect(this,
				imageManager, sound));
		sceneManager.add(EScene.DEMO, new Demo(this, videoView));
		sceneManager.add(EScene.STAFF, new Staff(this, imageManager));
		sceneManager.add(EScene.STORY1, new Story1(this, imageManager, sound));
		sceneManager.add(EScene.STORY2, new Story2(this, imageManager, sound));
		sceneManager.add(EScene.STORY3, new Story3(this, imageManager, sound));
		sceneManager.add(EScene.STORY4, new Story4(this, imageManager, sound));
		sceneManager.add(EScene.STORY5, new Story5(this, imageManager, sound));
		sceneManager.add(EScene.STAGE1, stage1);
		sceneManager.add(EScene.STAGE2, stage2);
		sceneManager.add(EScene.STAGE3, stage3);
		sceneManager.add(EScene.STAGE4, stage4);
		sceneManager.add(EScene.STAGE5, stage5);
		sceneManager.add(EScene.RESULT, new Result(this, timer, charaManager));

		sceneManager.change(EScene.TITLE);
	}

	public void gameMain() {
		sceneManager.update();
	}

	public void gameDraw(Graphics g) {
		sceneManager.draw(g);
	}

	public void gameFinish() {
		sceneManager.finish();
	}

}
