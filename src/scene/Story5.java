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

public class Story5 extends Story implements IScene {

	public Story5(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay4", R.drawable.gameplaybg4);
		imageManager.load("gamePlay5", R.drawable.gameplaybg5);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 5話
				"9,第5話",

				// (次元の狭間)
				"0,・・・", "6,待っていたぞ、魅月そして嵐丸", "0,名乗った覚えは無いが？",
				"6,ずっとここで見ていたからな、この場所は世界を見渡せる場所なんだ",
				"0,なるほど、監視されてたのか",
				"1,鬼神！なんでみんなを殺したの？そんなに争いが大好きなの",
				"6,そうだ、我は戦を求め、この世を戦乱の渦に巻き込み、時代を変える、この先の時代に弱者はいらん",
				"6,死んだのは実力が無かっただけの話だ",
				"6,だが貴様ら違う、我の配下を全て倒し、見事ここまでたどり着いてみせた",
				"6,魅月よ、妖に転じ我に降れ、世界の半分をくれてやろう",
				"0,断る、俺が何しに来たか分かってるだろ",
				"0,お前を斬りに来たんだ、俺も嵐丸も",
				"6,ならば仕方あるまい",
				"6,貴様らとの戦いに興じるとしよう",
				"0,嵐丸、これが最後の戦いだ、行くぞ！",

				// (戦闘開始)

				"9,勝利！！",
				"6,魅月、嵐丸・・・やはり貴様らは素晴らしい存在だ",
				"6,この高揚感！、我をここまで昂らせたのは貴様らが初めてだぞ",
				"6,最後に貴様らと戯れたこと嬉しく・・思う・・ぞ・・・魅・・月",

				// 鬼神消滅
				"0,終わった・・・の・・・か",
				"1,やったよ！僕ら勝ったんだ",

				// SE
				"1,え、なに？、何が始まるの？",
				"0,おそらく鬼神が消滅した今、この空間が崩れようとしている",
				"0,早く脱出するぞ！",

				// 画面暗転

				// (山の頂上)

				// SE(フィードアウト)
				"0,なんとか間に合ったな", "1,・・・", "1,今まで感じてた強い妖怪の気配が全くなくなってる",
				"1,本当に終わったんだね", "0,ああ", "1,魅月、ありがとう",
				"0,俺も嵐丸のおかげで自分の目的を果たせた、礼を言うのは俺もだ、ありがとう",
				"9,こうして魅月たちの長きに渡る戦いが終わった", "9,嵐丸は自分の領土で新たな仲間を作り、ひっそりと暮らし",
				"9,魅月は新たな雇い主を見つけ再び用心棒へ戻った。",
				"9,人々は鬼神が倒されたことを大いに喜んだ。",
				"9,だが倒した者の名を知る者は誰もいなかった。",
				"9,そして後に、鬼神を倒した名も知らぬ英雄のことを斬幽鬼と呼び後世に語り継いだという・・・",

				// ED 終わり
				"9, [5話 END 斬幽鬼 完結]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38" };

		voiceID = new int[] {
				// 5話
				R.raw.mf1, R.raw.k1, R.raw.mf2, R.raw.k2, R.raw.mf3,
				R.raw.ranf1, R.raw.k3, R.raw.k4, R.raw.k5, R.raw.k6,
				R.raw.mf4, R.raw.mf5, R.raw.k7, R.raw.k8, R.raw.mf6,
				R.raw.muon, R.raw.k9, R.raw.k10, R.raw.k11, R.raw.mf7,
				R.raw.ranf2, R.raw.ranf3, R.raw.mf8, R.raw.mf9, R.raw.mf10,
				R.raw.ranf4, R.raw.ranf5, R.raw.ranf6, R.raw.mf11, R.raw.ranf7,
				R.raw.mf12, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon

				// ナレーション
				// R.raw.n,R.raw.ed
				};

		result = 14;
		count = -1;
		num = 0;

		if (Stage5.clearFlg) {
			result = 37;
			currentPage = 16;
			count = 15;
			num = 16;
		}

		tx = ty = 0;
	}

	public void update() {
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay5"), 0, 0);
		if (currentPage > 24) {
			g.drawImage(imageManager.getImage("gamePlay4"), 0, 0);
		}
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay4");
		imageManager.finish("gamePlay5");
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
			push(14, 37, EScene.STAGE5);
			allChange(14, 37, EScene.STAGE5);
			break;
		}

		return false;
	}

}
