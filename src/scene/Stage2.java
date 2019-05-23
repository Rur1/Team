package scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.ImageManager;
import lib.Sound;
import lib.Timer;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;

import com.example.team.CharaManager;
import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.R;

import enemy.Enemy2;
import enemy.State;
import enemyattack.DistanceAttack;
import enemyattack.HeightAttack;
import enemyattack.WidthAttack;

public class Stage2 implements IScene {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;
	ImageManager imageManager;
	Sound sound;

	Timer timer;

	CharaManager charaManager;
	Enemy2 enemy2;

	Random rnd;
	List<WidthAttack> widths;
	List<HeightAttack> heights;
	List<DistanceAttack> distances;
	List<Object> delList;

	public static boolean clearFlg;

	public Stage2(AGameCanvas agc, ImageManager imageManager, Sound sound, Timer timer,
			CharaManager charaManager) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
		this.timer = timer;
		this.charaManager = charaManager;

		enemy2 = new Enemy2(agc, imageManager);

		rnd = new Random();
		widths = new ArrayList<WidthAttack>();
		heights = new ArrayList<HeightAttack>();
		distances = new ArrayList<DistanceAttack>();
		delList = new ArrayList<Object>();
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("gamePlay2", R.drawable.gameplaybg2);

		timer.initialize();

		charaManager.initialize();
		enemy2.initialize();
		charaManager.getEnemyHP().initialize(20, 35.0f);
		charaManager.getLaidoSword().initialize();

		widths.clear();
		heights.clear();
		distances.clear();

		sound.play("drum", false, true);

		clearFlg = false;
	}

	public void update() {
		timer.update();
		charaManager.update();

		if (!charaManager.getLaidoSword().getIsEnd()) {
			laido();
			return;
		}
		sound.play("gameplay", true, true);

		enemy2.update();

		transition();

		if (charaManager.getEnemyHP().count()) {
			enemy2.setDamage(true);
		}

		if (charaManager.getEnemyHP().getBar() <= 0) {
			clearFlg = true;
			next = EScene.RESULT;
			isEnd = true;
		}

		if (charaManager.getPlayer().getHp() <= 0) {
			next = EScene.RESULT;
			isEnd = true;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay2"), 0, 0);

		enemy2.draw(g);

		for (WidthAttack widthAttack : widths) {
			widthAttack.draw(g);
		}

		for (HeightAttack heightAttack : heights) {
			heightAttack.draw(g);
		}

		for (DistanceAttack distanceAttack : distances) {
			distanceAttack.draw(g);
		}

		charaManager.draw(g);
		charaManager.getLaidoSword().draw(g);
	}

	public void finish() {
		imageManager.finish("gamePlay2");
		// charaManager.finish();
		enemy2.finish();
		sound.pause("gameplay");
		sound.pause("drum");
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	private void laido() {
		charaManager.getLaidoSword().update();

		if (charaManager.getLaidoSword().successAttack()) {
		    charaManager.getEnemyHP().addHp(-4);
			enemy2.setDamage(true);
		}
		if (charaManager.getLaidoSword().missLaido()) {
			charaManager.getPlayer().addHp(-4);
			enemy2.setAttack(true);
		}
	}

	private void addWidthAttack() {
		if (rnd.nextInt(90) == 0) {
			widths.add(new WidthAttack(agc, imageManager));
		}
		for (WidthAttack widthAttack : widths) {
			widthAttack.update();
			widthAttack.collision(charaManager.getSlash().getLine());
			if (widthAttack.guard()) {
				charaManager.getEnemyHP().addDamageCount(1);
				delList.add(widthAttack);
			}
			if (widthAttack.getTimer() == 0) {
				charaManager.getPlayer().addHp(-1);
				enemy2.setAttack(true);
				delList.add(widthAttack);
			}
			if (charaManager.getEnemyHP().getDamageCount() >= 5) {
				delList.add(widthAttack);
			}
		}
		widths.removeAll(delList);
	}

	private void addHeightAttack() {
		if (rnd.nextInt(90) == 0) {
			heights.add(new HeightAttack(agc, imageManager));
		}
		for (HeightAttack heightAttack : heights) {
			heightAttack.update();
			heightAttack.collision(charaManager.getSlash().getLine());
			if (heightAttack.guard()) {
				charaManager.getEnemyHP().addDamageCount(1);
				delList.add(heightAttack);
			}
			if (heightAttack.getTimer() == 0) {
				charaManager.getPlayer().addHp(-1);
				enemy2.setAttack(true);
				delList.add(heightAttack);
			}
			if (charaManager.getEnemyHP().getDamageCount() >= 5) {
				delList.add(heightAttack);
			}
		}
		heights.removeAll(delList);
	}

	private void addDistanceAttack() {
		if (rnd.nextInt(60) == 0) {
			distances.add(new DistanceAttack(agc, imageManager));
		}
		for (DistanceAttack distanceAttack : distances) {
			distanceAttack.update();
			distanceAttack.permitCollision(charaManager.getSlash());
			distanceAttack.setState();
			if (distanceAttack.getDx() <= 130) {
				charaManager.getEnemyHP().addHp(-1);
				enemy2.setDamage(true);
				delList.add(distanceAttack);
			}
			if (distanceAttack.getDx() >= 400) {
				charaManager.getPlayer().addHp(-1);
				enemy2.setAttack(true);
				delList.add(distanceAttack);
			}
		}
		distances.removeAll(delList);
	}

	private void enemyAttackInit() {
		widths.clear();
		heights.clear();
		charaManager.getEnemyHP().damageCountInit();
	}

	private void transition() {
		if (enemy2.getState() == State.WAIT) {
			addWidthAttack();
			addHeightAttack();
		}
		if (enemy2.getState() == State.JUMP) {
			enemyAttackInit();
		}
		if (enemy2.getStateTimer() <= 0) {
			distances.clear();
			return;
		}
		if (enemy2.getState() == State.TACKLE) {
			addDistanceAttack();
		}
	}

}
