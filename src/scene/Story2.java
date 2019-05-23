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

public class Story2 extends Story implements IScene {

	public Story2(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		super(agc, imageManager, sound);
	}

	public void initialize() {
		isEnd = false;
		imageManager.load("skip", R.drawable.skip);
		imageManager.load("gamePlay2", R.drawable.gameplaybg2);
		charaLoad();
		agc.setOnTouchListener(this);
		currentPage = 0;

		scenarioData = new String[] {
				// 2話
				"9,第2話",

				// 平原
				"1,魅月・・聞いてもいい？",
				"0,どうした",
				"1,魅月のお兄さんってどんな人なの？",
				"0,昔はとても優しくて努力を怠らない立派な人だった",
				"1,昔は？今は違うの？",
				"0,少し昔話をしようか・・・",
				"9,(魅月の過去)",
				"9,俺は昔、大きなお屋敷の用心棒を兄貴と・・・兄弟でしていたんだ",
				"9,俺たち兄弟は家の者を襲おうとする者全てを斬る、完璧な用心棒だった",
				"9,だが、ある夜、突然兄貴が家の人間を全員殺し、どこかへ姿を消した",

				// 現代
				"0,なんで、兄貴があんなことをしたかわからないけど、あの一家には色々世話になった",
				"0,そんなみんなを殺した兄貴を俺は許しちゃいけないと思う",
				"1,魅月・・ごめんね、聞いちゃいけなかったよね",
				"0,気にするな",

				// 東の山・入り口
				"0,着いたな",
				"0,心の準備はいいか？嵐丸",
				"1,もちろん",
				"3,あら、とうとうたどり着いてしまったの？",

				// (不知火登場)
				"0,お前は！",
				"3,私は不知火",
				"3,鬼神様の命により貴様の命を刈りに来た",
				"3,ん？なんだ、貴様、あの男の血縁者か？",
				"0,血縁者？まさか！やっぱり兄貴はここにいるのか！？",
				"3,少し遅かったな",
				"3,あいつはもう人として死んだぞ？",
				"0,てめぇ",

				// 魅月が不知火に斬りかかる
				"1,魅月待って、罠だ！",

				// 不知火・魅月・嵐丸の周りを炎が包む
				"3,さぁ、これで貴様は出られない、たっぷりなぶり殺してやる",

				// (戦闘開始)

				"9,勝利！！",

				// 周りの炎が消えた
				"3,グッ・・・ハァハァ・・クソ・・人間風情が",
				"3,貴様はここから先地獄を見ることになるぞ",
				"3,・・・あの世で待ってるからな・・・",

				// 不知火消滅
				"0,兄貴はやはり妖に・・・",
				"1,魅月・・・",
				"0,うっ・・・また頭が",

				// (不知火の過去の記憶)
				"9,(屋敷・夜)",

				"5,何者だ貴様のような者が立ち入っていい場所ではないぞ",
				"3,あら、怖いでもそんなこと言って、あなたなにかに恐れを感じているわね", "5,なぜそれを知っている！？",
				"3,話してみなさいよ、聞いてあげるわ？", "5,いや・・俺は・・・",
				"3,ためらう必要なんてないわ、人間誰でも悩みをしょい込んでるものよ", "3,あなたの悩みはどんなもの？",
				"5,俺は兄弟の長男だ・・・だが俺は弟よりも劣っている",
				"5,昔は剣の技術もお互い競い合っていた、だが今では、あいつと一緒にいるのが怖くなるんだ。",
				"5,あいつは自分でも気づいていないが、あいつの剣術はもう化け物の領域に達しようとしている",
				"5,このままでは雇い主に捨てられるかもしれない、存在理由が無くなってしまうのではないかと",
				"3,そう、あなたも大変な思いをしているのね",
				"3,弟と差がついてしまうのが、負けるのが、捨てられるのが、存在理由が無くなるのが怖い",
				"3,そんなあなたにとってもいい解決方法を提案するわ", "5,なんだ・・それは？",
				"3,ンフ、その雇い主ぜーんぶ皆殺しにしちゃって、キレイさっぱり新しい自分に生まれ変わればいいのよ", "5,！？",
				"3,簡単なことよ、夜みんなが寝静まっている時、サクッと ね", "3,そうすれば楽になれるわ",
				"5,全部・・・楽に？",
				"3,ええっ、あなたが望む弟への劣等感、雇い主からの期待を全部捨て去ることができるわ",
				"3,やるかやらないかはあなた次第だけど",

				// (現在)
				"0,兄貴、全然気づかなかった",
				"0,こんなに苦しんでたなんて",

				// 地面が揺れる
				"0,なんだ、これは", "1,魅月早くしないとなにかマズい事が起こりそうだよ", "0,・・・",
				"1,魅月、大丈夫？ 辛そうだよ？", "0,すまん 大丈夫だ、兄貴に会っても俺は迷わず、斬るよ", "0,さぁ、急ごう",
				"1,・・・魅月",

				"9, [2話 END]" };

		voiceName = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
				"30", "41", "42", "43", "44", "45", "46", "47", "48", "49",
				"50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
				"60", "61", "62", "63", "64", "65", "66", "67", "68" };

		voiceID = new int[] {
				// 2話
				R.raw.rand1, R.raw.md1, R.raw.rand2, R.raw.md2, R.raw.rand3,
				R.raw.md3, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.md4, R.raw.md5, R.raw.rand4, R.raw.md6, R.raw.md7,
				R.raw.md8, R.raw.rand5, R.raw.s1, R.raw.md9, R.raw.s2,
				R.raw.s3, R.raw.s4, R.raw.md10, R.raw.s5, R.raw.s6,
				R.raw.md11, R.raw.rand6, R.raw.s7, R.raw.muon, R.raw.s8,
				R.raw.s9,R.raw.s10, R.raw.md12, R.raw.rand7, R.raw.md13,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon, R.raw.muon,
				R.raw.muon, R.raw.muon, R.raw.muon, R.raw.md14, R.raw.md15,
				R.raw.md16, R.raw.rand8, R.raw.md17, R.raw.rand9, R.raw.md18,
				R.raw.md19, R.raw.rand10, R.raw.muon,

				// ナレーション
				R.raw.n, R.raw.ed };

		result = 27;
		count = -1;
		num = 0;

		if (Stage2.clearFlg) {
			result = 67;
			currentPage = 29;
			count = 28;
			num = 29;
		}

		tx = ty = 0;
	}

	public void update() {
		boolean bgmFlg1 = currentPage >= 7 && currentPage <= 10;
		boolean bgmFlg2 = currentPage >= 36 && currentPage <= 58;
		if (bgmFlg1 || bgmFlg2) {
			sound.play("sad", true, true);
		}
		if (currentPage == 11 || currentPage == 59) {
			sound.pause("sad");
		}
		nextLine();
		soundStop();
	}

	public void draw(Graphics g) {
		g.drawImage(imageManager.getImage("gamePlay2"), 0, 0);
		allDraw(g);
	}

	public void finish() {
		imageManager.finish("skip");
		imageManager.finish("gamePlay2");
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
			push(27, 67, EScene.STAGE2);
			allChange(27, 67, EScene.STAGE2);
			break;
		}

		return false;
	}

}
