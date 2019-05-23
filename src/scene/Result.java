package scene;

import lib.Timer;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import android.graphics.Color;

import com.example.team.CharaManager;
import com.example.team.EScene;
import com.example.team.IScene;

public class Result implements IScene {

	AGameCanvas agc;
	EScene next;
	boolean isEnd;

	Timer timer;
	CharaManager charaManager;

	int rtimer, rhp, h, time;
	int rankHpPoint, rankTimePoint, rankPoint;
	String rank;

	public Result(AGameCanvas agc, Timer timer, CharaManager charaManager) {
		this.agc = agc;
		this.timer = timer;
		this.charaManager = charaManager;
	}

	public void initialize() {
		isEnd = false;
		rtimer = rhp = h = time = 0;
		rankPoint = 0;
	}

	public void update() {
		rtimer = timer.getTimer();
		rhp = charaManager.getPlayer().getHp();

		rank();

		if (h <= agc.getHeightA()) {
			h += 50;
			return;
		}
		time++;

		if (time <= 50) {
			return;
		}
		if (agc.gcGetTouchTrigger()) {
			if (Stage1.clearFlg) {
				next = EScene.STORY1;
			}
			if (Stage2.clearFlg) {
				next = EScene.STORY2;
			}
			if (Stage3.clearFlg) {
				next = EScene.STORY3;
			}
			if (Stage4.clearFlg) {
				next = EScene.STORY4;
			}
			if (Stage5.clearFlg) {
				next = EScene.STORY5;
			}
			if (rankPoint <= 0) {
				next = EScene.STAGESELECT;
			}
			isEnd = true;
		}
	}

	public void draw(Graphics g) {
		// g.setColor(Color.BLACK);
		// int alpha = 100;
		// g.fillRect(0, 0, agc.getWidthA(), h, alpha);

		g.setColor(Color.WHITE);
		g.setFontSize(50);

		g.drawString("ƒŠƒUƒ‹ƒg", 300, h - 1000);
		if (time >= 10) {
			g.drawString("Œo‰ßŽžŠÔ:" + rtimer + "•b", 250, h - 750);
		}
		if (time >= 30) {
			g.drawString("ŽcHP:" + rhp, 300, h - 600);
		}
		if (time >= 50) {
			g.drawString("•]‰¿:" + rank, 600, h - 300);
		}
	}



	public void finish() {

	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	private void rank() {
		rankHpPoint = 3;
		if (rhp >= 4 && rhp <= 6) {
			rankHpPoint = 2;
		}
		if (rhp >= 1 && rhp <= 3) {
			rankHpPoint = 1;
		}

		rankTimePoint = 1;
		if (rtimer >= 31 && rtimer <= 60) {
			rankTimePoint = 2;
		}
		if (rtimer >= 0 && rtimer <= 30) {
			rankTimePoint = 3;
		}

		rankPoint = rankHpPoint + rankTimePoint;

		if (rhp <= 0) {
			rankPoint = 0;
		}

		rank = "A";
		if (rankPoint >= 3 && rankPoint <= 4) {
			rank = "B";
		}
		if (rankPoint >= 1 && rankPoint <= 2) {
			rank = "C";
		}
		if (rankPoint <= 0) {
			rank = "D";
		}
	}

}
