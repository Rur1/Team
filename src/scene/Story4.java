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

public class Story4 extends Story implements IScene {

	public Story4(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay4", R.drawable.story4bg);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 4話
				"9,第4話",

				// (山の頂上)
				"1,ここが山の頂上・・・",
				"0,なんだ、あれは？",
				"1,あの中から強い妖気を感じる",
				"1,間違いない、あの中に鬼神がいる",

				// 目の前に黒い影が舞い降りおる
				"1,！？", "5,久しぶりだな、弟よ", "0,会いたかったよ、兄貴",
				"5,・・・驚かないんだな、こんな姿になった俺を見ても",
				"0,あぁ、兄貴が妖怪になって鬼神の手下になったのはさっき知ったから", "5,不知火か・・・お喋りな奴め",
				"5,だが、まぁ数々の鬼神の幹部を倒し、ここまできたのがお前だったとは、俺はうれしいぞ",
				"5,まさか、こんな形で弟を殺めることができるのだからな", "1,すごい殺気だ、魅月！来るよ！！", "0,・・・",
				"5,どうした？なにか言いたげだが", "1,魅月？", "0,俺のせいで、こんな姿にさせてしまって、すまなかった",
				"5,！？", "0,全部見たんだ、兄貴が苦しんでたこと、俺に対する憎しみを",
				"5,だまれ！お前に何がわかる！わかったような口をきくな！", "0,ああ、俺は兄貴の人間の部分を殺してしまった",
				"0,そして今、そのケジメをつけるために兄貴を、いや・・・夜月を斬る！",
				"5,この俺を斬る？つけ上がるなよ人間！殺してやる！", "1,魅月・・・本当にいいのかい？",
				"0,ああ、これで全力で斬れる",
				"0,・・・行くぞ！夜月！",
				"5,ウオォォォォ",

				// (戦闘開始)

				"9,勝利！！",
				"5,ミイィィツゥゥキイィィィィ！！",
				"0,さよなら・・・兄貴・・",

				// SE
				"5,ガハッ",

				// SE
				"0,・・・", "1,よかったの？これで", "0,・・・よかったさ", "1,力は・・・吸収しなくていい",
				"0,兄貴すまなかった、これが俺にできる精一杯の償いだ",

				// 一拍間を入れる
				"0,最後の戦いか", "1,うん、これで悲しい出来事は最後にしなくちゃ",
				"0,そうだな、じゃあ最後の仕事をしに行こうか", "1,うん",

				// SE
				"9, [4話 END]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
				"40", "41" };

		voiceID = new int[] {
				// 4話
				R.raw.rant1, R.raw.mt1, R.raw.rant2, R.raw.rant3, R.raw.rant4,
				R.raw.y1, R.raw.mt2, R.raw.y2, R.raw.mt3, R.raw.y3,
				R.raw.y4, R.raw.y5, R.raw.rant5, R.raw.mt4, R.raw.y6,
				R.raw.rant6, R.raw.mt5, R.raw.y7, R.raw.mt6, R.raw.y8,
				R.raw.mt7,  R.raw.mt8, R.raw.y9, R.raw.rant7, R.raw.mt9,
				R.raw.mt10, R.raw.y10, R.raw.muon, R.raw.y11, R.raw.mt11,
				R.raw.y12, R.raw.mt12, R.raw.rant8, R.raw.mt13, R.raw.mt14,
				R.raw.mt15, R.raw.mt16, R.raw.rant9, R.raw.mt17, R.raw.rant10,
				R.raw.muon

				// ナレーション
				/*R.raw.n, R.raw.ed,*/ };

		result = 26;
		count = -1;
		num = 0;

		if (Stage4.clearFlg) {
			result = 40;
			currentPage = 28;
			count = 27;
			num = 28;
		}

		tx = ty = 0;
	}

	public void update() {
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay4"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay4");
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
			push(26, 40, EScene.STAGE4);
			allChange(26, 40, EScene.STAGE4);
			break;
		}

		return false;
	}

}
