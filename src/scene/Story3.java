package scene;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import android.view.MotionEvent;
import android.view.View;

import com.example.team.EScene;
import com.example.team.IScene;
import com.example.team.R;
import com.example.team.Story;

public class Story3 extends Story implements IScene {

	public Story3(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay3", R.drawable.gameplaybg3);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 3話
				"9,第3話",

				// (山の中腹)
				"0,もうすぐ山の頂上か・・・", "1,この戦いももうすぐ終わるね",
				"1,！",
				"1,魅月！敵だ！避けて",
				"0,なっ！",

				// 魅月はかろうじで敵の攻撃を避けた
				"4,私は鬼神様の部下の一人、玉藻前", "4,ここから先は行かせないわよ", "0,邪魔だ！どけ！",
				"1,玉藻前・・・聞き覚えがある、鬼神の右腕と呼ばれている奴だ", "1,今まで倒した２体とは格が違う、気を付けて！",
				"0,いくぞ",

				// 戦闘開始

				"9,勝利！！", "4,申し訳ありません・・・鬼神様", "0,クッ・・ハァハァ", "1,かなり疲労してるけど大丈夫？",
				"0,問題ない、こいつの力を吸収してくれ", "1,うん",

				"9,(玉藻前の過去)",

				// 鬼神の根城
				"6,玉藻前よ、雷獣と不知火を葬った者を消せ", "4,御意", "6,我はここでまだ力を蓄え、全ての領土を奪いに行く",

				"9,(現代・山の中腹)",

				"0,急がないと、どうやら本当にマズいようだな",

				// 地面が揺れる
				"1,・・・！？また地震！？", "0,早く頂上に行くぞ",

				"9, [3話 END]", };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26" };

		voiceID = new int[] {
				// 3話
				R.raw.ma1, R.raw.rana1, R.raw.rana2, R.raw.rana3, R.raw.ma2,
				R.raw.t1, R.raw.t2, R.raw.ma3, R.raw.rana4, R.raw.rana5,
				R.raw.ma4, R.raw.muon, R.raw.t3, R.raw.ma5, R.raw.rana6,
				R.raw.ma6, R.raw.rana7, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.ma7, R.raw.rana8, R.raw.ma8,
				R.raw.muon

				// ナレーション
				/*R.raw.n, R.raw.ed*/ };

		result = 10;
		count = -1;
		num = 0;

		if (Stage3.clearFlg) {
			result = 25;
			currentPage = 12;
			count = 11;
			num = 12;
		}

		tx = ty = 0;
	}

	public void update() {
		if (currentPage >= 18 && currentPage <= 21) {
			sound.play("sad", true, true);
		}
		if (currentPage == 22) {
			sound.pause("sad");
		}
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay3"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay3");
		charaFinish();
	}

	public boolean isEnd() {
		return isEnd;
	}

	public EScene next() {
		return next;
	}

	public boolean onTouch(View v, MotionEvent event) {
		tx = event.getX() / agc.getRateW();
		ty = event.getY() / agc.getRateH();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			push(10, 25, EScene.STAGE3);
			allChange(10, 25, EScene.STAGE3);
			break;
		}

		return false;
	}

}
