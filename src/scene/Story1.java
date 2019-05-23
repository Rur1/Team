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

public class Story1 extends Story implements IScene {

	public Story1(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay1", R.drawable.gameplaybg1);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 1話
				"9,第1話",

				"6,この世に弱き者はいらん！強き者がこの世界に存在し続け、そして争いの時代を築き上げるのだ",
				"7,ウワーーーーーーッ！",
				"8,嫌だ・・・死に・・たくな・・・い・・・",
				"1,みんな・・・そんな・・嫌だ・・・やめてよ！！",

				// 画面暗転
				"1,やめろーーーーーーーー！",
				"1,また・・あの夢か・・",
				"0,かなりうなされていたが大丈夫か？",
				"1,魅月・・・うん、平気だよ",
				"0,そろそろ出発しよう",
				"1,うん、鬼神・・・待ってろよ",

				// (平原)
				"0,本当にこの先を東に行くと鬼神の根城なのか？",
				"1,うん、この先に微かに鬼神の妖気を感じる",
				"1,・・・",
				"0,どうした？",
				"1,いや、なんで魅月は僕に協力してくれたのかなって",
				"0,俺は旅をしながら失踪した兄貴を探しているんだ",
				"0,嵐丸と一緒に旅してたらなにか手掛かりくらいは掴めそうだなと思ってな",
				"1,そうだったんだ・・・見つかるといいね！",
				"0,ああ",

				// ワンテンポおいて次のテキスト表示
				"1,！？",
				"1,あの時の妖気に似てる・・・",
				"1,この先に鬼神の手下がいるかもしれない",
				"0,そいつは知ってるやつなのか？",
				"1,僕の故郷で暴れた妖の妖気に似てるんだ！",
				"0,もしその妖だったら丁度いいじゃねーか",
				"1,えっ？",
				"0,一緒に仇を取ろうぜ！",
				"1,魅月・・・うん！",

				// (平原・中央)
				"2,なんだ・・・貴様",
				"1,ああ、やっぱりあの時の",
				"0,お前が嵐丸の故郷を荒らした張本人か！",
				"2,なんの用だ？",
				"0,鬼神の首を獲りに来た",
				"2,鬼神様・・守る・・",
				"2,お前・・倒す",
				"0,来い・・嵐丸",
				"1,うん",

				// 嵐丸の身体が刀の形状に変化していく
				"2,妖が・・武器に？",
				"1,かまいたちの妖は人間が扱う武器に変身できるんだ",
				"2,ん？だが・・形だけで、抜刀も容易ではないな？",
				"0,刀の形になっただけで十分だ、うまく扱って見せる！",
				"0,さぁ・・いくぜ！",

				// 戦闘開始

				"9,勝利！！", "2,グハァーーーーーー", "1,やったの！？", "0,らしいな", "1,みんな仇は取ったよ",
				"0,喜ぶのはまだ早いぞ",
				"0,まだ鬼神が残ってる",
				"1,うん",
				"1,この妖の力を吸収するよ",
				"0,そんなことができるのか",
				"1,うん、ちょっと待ってて",
				"0,うっ・・頭が・・",

				// 過去
				"9,	(雷獣の過去)", "6,ようこそ、雷獣よ", "6,今日からここが貴様の居場所だ",
				"6,我が根城を守る番人をしてもらう", "2,俺・・鬼神の・・番人？", "6,そうだ、元人間である貴様が我の下で働ける",
				"6,うれしいだろう？",
				"2,元・・人間？",
				"6,覚えていないのか？お前はもう妖だぞ",
				"2,ウッ・・ウッ・ウオオオオ",

				// 現代
				"1,魅月・・大丈夫？", "0,クッ・・ああ・・大丈夫だ、それより今のは？",
				"1,たぶん、力を吸収した時にあの妖の記憶の一部が魅月に流れてきたんだよ",
				"0,そうか、ということはあの妖は元々人間だったのか、もしかしたら・・・", "1,魅月？",
				"0,いや、なんでもない 先を急ごう", "1,そうだね",

				"9, [1話 END]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
				"40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
				"50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
				"60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
				"70", "71", "72" };

		voiceID = new int[] {
				// 1話
				R.raw.k, R.raw.muon, R.raw.muon, R.raw.ran1, R.raw.ran2,
				R.raw.ran3, R.raw.m1, R.raw.ran4, R.raw.m2, R.raw.ran5,
				R.raw.m3, R.raw.ran6, R.raw.ran7, R.raw.m4, R.raw.ran8,
				R.raw.m5, R.raw.m6, R.raw.ran9, R.raw.m7, R.raw.ran10,
				R.raw.ran11, R.raw.ran12, R.raw.m8, R.raw.ran13, R.raw.m9,
				R.raw.ran14, R.raw.m10, R.raw.ran15, R.raw.rai1, R.raw.ran16,
				R.raw.m11, R.raw.rai2, R.raw.m12, R.raw.rai3, R.raw.rai4,
				R.raw.m13, R.raw.ran17, R.raw.rai5, R.raw.ran18, R.raw.rai6,
				R.raw.m14, R.raw.m15,

				// 戦闘終わり
				R.raw.rai7, R.raw.ran19, R.raw.m16, R.raw.ran20, R.raw.m17,
				R.raw.m18, R.raw.ran21, R.raw.ran22, R.raw.m19, R.raw.ran23,
				R.raw.m20, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.ran24, R.raw.m21, R.raw.ran25, R.raw.m22,
				R.raw.ran26, R.raw.m23, R.raw.ran27, R.raw.muon,

				// ナレーション
				R.raw.n, R.raw.ed };

		result = 41;
		count = -1;
		num = 0;

		if (Stage1.clearFlg) {
			result = 70;
			currentPage = 43;
			count = 41;
			num = 42;
		}

		tx = ty = 0;
	}

	public void update() {
		if (currentPage >= 55 && currentPage <= 64) {
			sound.play("sad", true, true);
		}
		if (currentPage == 65) {
			sound.pause("sad");
		}
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay1"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay1");
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
			push(41, 70, EScene.STAGE1);
			allChange(41, 70, EScene.STAGE1);
			break;
		}

		return false;
	}

}