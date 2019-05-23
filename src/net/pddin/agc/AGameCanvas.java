package net.pddin.agc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * AGameCanvas なるべく簡単にAndroidの2Dゲームを作るためのフレームワーク
 *
 * @author HARADA Toshinobu
 * @version 0.400 (H26/07/08)
 */

/*
 * ver 0.400 H26/07/08 描画解像度の端末依存の問題、特に縦横比の問題をなるべく修正。 *
 * 解像度調整を横幅で行うように仕様を変更した。これにより縦横比の問題は解消された。 *
 * これにともない、設定解像度の縦幅の縮尺率が端末の解像度の縦幅の縮尺率と異なる場合には縦の描画位置を調整するようにした。 *
 * よって、設定解像度の縦幅よりも端末の縦幅の方が大きくなる場合は、上下が切れてしまうことになる。 * 基本的にnexus7v2012仕様（800,
 * 1205）で作っておけば大きな問題は無い。
 * AGameCanvas#gameDraw(Canvas)を抽象メソッドから具象メソッドに変更。強制オーバーライドをしないようにした。 *
 * 原則として描画処理についてはAGameCanvas.gameDraw(Graphics)で行うことを推奨。
 */
/*
 * ver 0.302 H25/12/24 net.pddin.agc.geomパッケージを追加。
 * net.pddin.agc.geom.Point2Dクラスを追加。 net.pddin.agc.geom.Line2Dクラスを追加。 *
 * Line2D#intersectsLineメソッドによる線と線の交差判定機能を実装。
 */
/*
 * ver 0.301 H25/12/12 gcGetTouchTriggerのTouchPosのnullチェックを追加。（まれに落ちる不具合があったので。）
 */
/*
 * ver 0.300 H25/11/04 ポーズ処理を実装
 */
/*
 * ver 0.299 H25/10/04 描画解像度を端末依存ではなく指定できるようにした。詳しくはドキュメント（コンストラクタ周りを参照。）。
 * これに伴いタッチ処理なども修正。 GalaxyS3のタッチキャンセル処理の挙動がおかしいことを確認。他端末では問題なし。
 * ソースコードの問題ではないように思われるので、GalaxyS3の仕様であると思いこむことにする。
 * 一応対応策は講じてあるので、無理やりなタッチをしなければ動作に支障はない。
 */
/*
 * ver 0.298 H25/03/07 とりあえず仕様はそのままに、ソースコードをプチリファクタリング。
 *
 * スマホを縦持ち＆片手持ちで遊べるゲームを作るためのフレームワークです。 端末の対応バージョンは、2,2.X、2.3.X、4.0.X～です。
 * 画面サイズサポートは、Pは縦持ちPORTRAIT、Lは横持ちLANDSCAPE。 WVGA-P(横width 480, 縦height 800),
 * WVGA-L(横width 800, 縦height 480)
 *
 * WXGA-P(横width 720, 縦height 1280), WXGA-L(横width 1280, 縦height 720)
 *
 * nexus7(2012)仕様：専用キー部分はPとLとで異なるため画面サイズが変動する。専用キー部分のタッチは条件付き。 WXGA-nP(横width
 * 800, 縦height 1205(1280)) WXGA-nL(横width 1280, 縦height 736( 800))
 *
 * nexus7(2013)仕様：専用キー部分はPとLとで異なるため画面サイズが変動する。専用キー部分のタッチは条件付き。 WXGA-nP(横width
 * 1200, 縦height 1824(1920)) WXGA-nL(横width 1920, 縦height 1104(1200))
 *
 * 標準動作確認端末は、 * SC-02B(GalaxyS ): 2.3.6: WVGA: マルチタッチ数5 * SC-06D(GalaxyS3):
 * 4.1.2: WXGA: マルチタッチ数10 * ME370T(Nexus7 2012): 4.2.2: WXGA: マルチタッチ数10 *
 * Nexus7(Nexus7 2013): 4.3.0: : マルチタッチ数10
 *
 * 2.1でも使えるようにするためには、onTouchEvent内の me.getActionIndex(); を (me.getAction() &
 * MotionEvent.ACTION_POINTER_ID_MASK ) >> MotionEvent.ACTION_POINTER_ID_SHIFT ;
 * にすればよい。 一応動くことをXperiaで確認しました。
 */

public abstract class AGameCanvas extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {
	/**
	 * WAIT_10
	 * 10.000000ms(10000000ns)ウェイト用フィールド。FPS100相当。（スペックのかなり高い実機じゃないと出ません。）
	 */
	public static final long WAIT_10 = 10000000L;
	/**
	 * WAIT_17
	 * 16.666667ms(16666667ns)ウェイト用フィールド。FPS60相当。（2011年春夏モデルぐらいからならいけるかも。）
	 */
	public static final long WAIT_17 = 16666667L;
	/** WAIT_20 20.000000ms(20000000ns)ウェイト用フィールド。FPS50相当。 */
	public static final long WAIT_20 = 20000000L;
	/** WAIT_30 30.000000ms(30000000ns)ウェイト用フィールド。FPS33相当。標準。 */
	public static final long WAIT_30 = 30000000L;
	/** WAIT_50 50.000000ms(50000000ns)ウェイト用フィールド。FPS20相当。ロースペックモデルにはこれくらいで。 */
	public static final long WAIT_50 = 50000000L;

	private int fps; // FPS（フレームパーセカンド：１秒ごとのフレーム数）
	private int frameCounter; // フレームカウント用変数
	private long fpsCalcTime; // FPS算出用変数
	private long ideWaitTime; // 想定ウェイトタイム
	private long actWaitTime; // 実ウェイトタイム
	private long gameTime; // ゲーム実行時間
	private long firstTime; // ゲーム開始時間

	// ゲームポーズ（一時中断）処理関連
	private boolean pause;

	/**
	 * onPause ゲームをポーズ状態にするメソッド
	 */
	public final void onPause() {
		this.setPause(true);
	}

	/**
	 * offPause ゲームのポーズ状態を解除するメソッド
	 */
	public final void offPause() {
		this.setPause(false);
	}

	/**
	 * getPause ゲームが中断状態かどうかを返すメソッド
	 *
	 * @return 通常動作状態（false）、中断状態（true）
	 */
	public final boolean getPause() {
		return pause;
	}

	/**
	 * setPause ゲームを中断するか解除するかを設定するメソッド
	 *
	 * @param pause
	 *            （trueで中断、falseで解除）
	 */
	private final void setPause(boolean pause) {
		this.pause = pause;
	}

	// 描画解像度調整関連
	private float rateW; // 横幅の調整レート 実横幅width / 調整横幅widthA
	private float rateH; // 縦幅の調整レート 実縦幅height / 調整縦幅heightA
	private int widthA; // 調整（Adjust）横幅
	private int heightA; // 調整（Adjust）縦幅
	private float offsetY; // 縦位置調整用

	/**
	 * getRateW 横幅の調整レートを返すメソッド
	 *
	 * @return 実横幅width / 調整横幅widthA の値を返す
	 */
	public final float getRateW() {
		return rateW;
	}

	/**
	 * getRateH 縦幅の調整レートを返すメソッド
	 *
	 * @return 実縦幅height / 調整縦幅heightA の値を返す
	 */
	public final float getRateH() {
		return rateH;
	}

	/**
	 * getWidthA 調整横幅を返すメソッド コンストラクタによって指定された値から算出された調整横幅を返します。
	 * 端末の実際の横幅が欲しい場合はView#getWidthを用います。
	 *
	 * @return 調整横幅
	 */
	public final int getWidthA() {
		return widthA;
	}

	/**
	 * getHeightA 調整縦幅を返すメソッド コンストラクタによって指定された値から算出された調整縦幅を返します。
	 * 端末の実際の縦幅が欲しい場合はView#getHeightを用います。
	 *
	 * @return 調整縦幅
	 */
	public final int getHeightA() {
		return heightA;
	}

	/**
	 * getFPS 毎秒ごとのFPS値を返すメソッド
	 *
	 * @return FPS 毎秒ごとのFPS値
	 */
	public final int getFPS() {
		return fps;
	}

	/**
	 * getWaitTime 想定ウェイトタイムを返すメソッド
	 *
	 * @return 想定ウェイトタイム
	 */
	public final long getWaitTime() {
		return ideWaitTime;
	}

	/**
	 * setWaitTime ウェイトタイムを設定するメソッド
	 *
	 * @param wait
	 *            設定したいウェイトタイム（WAIT_10, WAIT_17, WAIT_30, WAIT_50）
	 */
	public final void setWaitTime(long wait) {
		if ((AGameCanvas.WAIT_10 <= wait) && (wait <= AGameCanvas.WAIT_50)) {
			this.ideWaitTime = wait;
		}
	}

	/**
	 * getGameTime ゲーム開始時からの経過時間を返すメソッド return ゲーム経過時間（ms）
	 */
	public final long getGameTime() {
		return gameTime;
	}

	/**
	 * gcGetMediaPlayer 「\res\raw」に保存したogg,midi等の音楽データをR.raw.****で指定し、
	 * そのMediaPlayerオブジェクトを取得するメソッド
	 *
	 * @param res_raw_id
	 *            , agc(今動作しているagcを指定する)
	 * @return MediaPlayerオブジェクト
	 */
	public MediaPlayer gcGetMediaPlayer(int res_raw_id, AGameCanvas agc) {
		return MediaPlayer.create(agc.getContext(), res_raw_id);
	}

	/**
	 * gcGetBitmap 「\res\drawable-nodpi」に保存したpng等のリソースデータをR.drawable.****で指定し、
	 * そのBitmapオブジェクトを取得するメソッド
	 *
	 * @param res_drawable_id
	 *            , agc(今動作しているagcを指定する)
	 * @return Bitmapオブジェクト
	 */
	public Bitmap gcGetBitmap(int res_drawable_id, AGameCanvas agc) {
		return BitmapFactory.decodeResource(agc.getContext().getResources(),
				res_drawable_id);
	}

	/**
	 * gcGetImage 「\res\drawable-nodpi」に保存したpng等のリソースデータをR.drawable.****で指定し、
	 * そのImageオブジェクトを取得するメソッド
	 *
	 * @param res_drawable_id
	 *            , agc(今動作しているagcを指定する)
	 * @return AGameCanvas用のImageオブジェクト
	 */
	public Image gcGetImage(int res_drawable_id, AGameCanvas agc) {
		return new Image(gcGetBitmap(res_drawable_id, agc));
	}

	private SurfaceHolder holder;
	private Thread thread;
	private boolean threadFlag;

	/**
	 * コンストラクタ （画面の向き設定、描画解像度調整あり） 第１引数には、Activityのインスタンスを指定する（通常はthis）。
	 * 第２引数には、画面の向きを指定する
	 * 。（ScreenOrientation.PORTRAIT（縦）、ScreenOrientation.LANDSCAPE（横））。
	 * 第３＆４引数には、指定したい調整横幅＆縦幅を指定する。（例えば、720, 1280）。
	 */
	@SuppressWarnings("deprecation")
	public AGameCanvas(Context context, ScreenOrientation so, int widthA,
			int heightA) {
		super(context);

		this.pause = false; // ゲーム起動時は非中断状態

		// 描画解像度調整関連
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		int w = 0;
		w = display.getWidth();
		int h = 0;
		h = display.getHeight();
		// APILevel13(3.2)以上なら下記の処理で対応可能、上記はApiLevel8(2.2)で対応可能とするためしょうがない
		// Point p = new Point();
		// display.getSize(p);
		// w = p.x;
		// h = p.y;

		if (widthA == 0) {
			widthA = w;
		}
		if (heightA == 0) {
			heightA = h;
		}

		this.widthA = widthA; // 調整横幅の初期化
		this.heightA = heightA; // 調整縦幅の初期化

		// 実横＆縦幅と、調整横＆縦幅から、調整レートを算出
		this.rateW = (float) w / (float) widthA;
		// this.rateH = (float)h / (float)heightA;
		this.rateH = this.rateW;

		// offsetYの算出
		this.offsetY = ((float) h - rateW * (float) this.heightA) * 0.5f;

		// コンストラクタ
		// ●ゲーム時間，FPS関連の処理はサーフェス生成のタイミングに記述しています。

		// ●コールバック関連の設定
		holder = getHolder(); // SurfaceHolderの取得
		holder.addCallback(this); // コールバック追加。

		// 画面の向き設定
		// 本来は、AndroidManifest.xmlを開き、アプリケーションタブの、
		// Application Nodesで、Activityを選び、
		// 右項目のScreen orientation をportraitかlandscapeにする。
		// ↓の処理で、↑のxmlファイルの設定の代用となる。
		// 本質的にはxmlで設定すべきだがAGCフレームワークではそうしない。
		if (so == ScreenOrientation.PORTRAIT) { // 縦画面固定モード
			((Activity) context)
					.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 縦画面固定
		} else if (so == ScreenOrientation.LANDSCAPE) { // 横画面固定モード
			((Activity) context)
					.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 横画面固定
		}
		((Activity) context).getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // スリープさせない
		((Activity) context).getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// フルスクリーンに設定
		((Activity) context).requestWindowFeature(Window.FEATURE_NO_TITLE);// タイトル非表示に設定
		gameInitBefore(); // ゲーム初期化事前処理
		onTouchEventInit(); // タッチイベント処理関連の初期化

	}

	/**
	 * コンストラクタ（画面の向き設定あり、描画解像度調整なし）
	 */
	public AGameCanvas(Context context, ScreenOrientation so) {
		this(context, so, 0, 0);
	}

	/**
	 * コンストラクタ （画面の向き設定なし（縦固定（PORTRAIT）、描画解像度指定あり）
	 */
	public AGameCanvas(Context context, int widthA, int heightA) {
		this(context, ScreenOrientation.PORTRAIT, widthA, heightA);
	}

	/**
	 * コンストラクタ （画面の向き設定なし（縦固定（PORTRAIT））、描画解像度指定なし）
	 */
	public AGameCanvas(Context context) {
		this(context, ScreenOrientation.PORTRAIT);
	}

	/** gameInitBefore gameInitよりも前に呼ばなければならない処理はこのメソッドをオーバーライドして記述する。 */
	protected void gameInitBefore() {
	}

	/** gameInit ゲーム初期化処理メソッド */
	public abstract void gameInit();

	/** gameMainBefore gameMain処理直前に呼ばなければならない処理はこのメソッドをオーバーライドして記述する。 */
	protected void gameMainBefore() {
	}

	/** gameMain ゲームメイン処理メソッド */
	public abstract void gameMain();

	/** gameMainAfter gameMain処理直後に呼ばなければならない処理はこのメソッドをオーバーライドして記述する。 */
	protected void gameMainAfter() {
	}

	/**
	 * gameDraw ゲーム描画処理メソッド。
	 * どうしてもAndroid標準のCanvasを用いた描画処理を記述したい場合はこちらをオーバーライドして処理を実装して下さい。
	 * ただし、解像度調整などは対応していません。
	 * */
	public void gameDraw(Canvas canvas) {
	}

	/**
	 * gameDraw ゲーム描画処理メソッド
	 * AGameCanvasには描画処理を記述するためのメソッドが複数定義してありますが、原則これを使用ください。
	 * */
	public abstract void gameDraw(Graphics g);

	/** gameFinishBefore gameFinish処理直前に呼ばなければならない処理はこのメソッドをオーバーライドして記述する。 */
	protected void gameFinishBefore() {
	}

	/** gameFinish ゲーム終了化処理メソッド */
	public abstract void gameFinish();

	public final void run() {
		// スレッド実行前の初期設定
		gameTime = 0L;
		setWaitTime(AGameCanvas.WAIT_30); // 標準のウェイトに設定する。（ＦＰＳ３３程度。）
		frameCounter = 0;
		fps = 1000000000 / (int) ideWaitTime;
		firstTime = System.nanoTime();
		fpsCalcTime = firstTime;

		this.pause = false; // ゲームは通常状態（非中断状態）

		gameInit();
		Canvas canvas = null;
		Graphics g = Graphics.getInstance();

		// 背景の設定
		Rect bgRect = new Rect(0, 0, this.getWidth(), this.getHeight());
		Paint bgPaint = new Paint(Color.BLACK);

		// 中断時のマスクの設定
		Paint pausePaint = new Paint(Color.BLACK);
		pausePaint.setAlpha(127); // αを設定

		while (threadFlag) {
			long startTime = System.nanoTime(); // マイフレーム処理開始時刻を取得

			// メイン処理（通常状態のときのみ更新処理を実行する）
			if (!pause) {
				gameMainBefore();
				gameMain();
				gameMainAfter();
			}

			canvas = holder.lockCanvas(); // キャンバスの取得（ロック）
			// 描画
			if (canvas != null) {
				canvas.drawRect(bgRect, bgPaint);
				gameDraw(canvas);
				// 縦位置の調整。
				canvas.translate(0.0f, offsetY);
				// これにより描画解像度の調整が可能となる。
				canvas.scale(rateW, rateH);

				g.setCanvas(canvas, this); // キャンバスをGraphicsに渡す
				gameDraw(g);
				// 調整した描画枠をうっすら描画
				g.setColor(Color.DKGRAY);
				g.drawRect(1, 1, getWidthA() - 2, getHeightA() - 2);

				// 中断状態でも描画はやめない。ただし若干暗くする⇒勝手に暗くなるので不要でした。。
				/*
				 * if(pause) { canvas.drawRect(bgRect, pausePaint); }
				 */
				holder.unlockCanvasAndPost(canvas); // キャンバスの開放（アンロック）
			}

			// ゲームウェイト系処理
			long frameTime = System.nanoTime() - startTime; // １フレーム処理に要した時間を取得
			actWaitTime = ideWaitTime - frameTime; // 実待ち時間の算出
			if (actWaitTime < 0) {
				actWaitTime = 0; // もし実待ち時間がマイナスなら待たずに次のフレームへ
			}
			// 実待ち時間ウェイト
			long sTime = System.nanoTime();
			while ((System.nanoTime() - sTime) < actWaitTime)
				;

			// 現在のゲーム時間を算出
			gameTime = (System.nanoTime() - firstTime) / 1000000L;

			// ＦＰＳ算出処理
			frameCounter = frameCounter + 1;
			if ((System.nanoTime() - fpsCalcTime) > 1000000000L) {
				fps = frameCounter; // ＦＰＳを更新
				frameCounter = 0; // フレームカウンターをリセット
				fpsCalcTime = System.nanoTime(); // FPS算出用時間をリセット
			}
		}
		gameFinishBefore();
		gameFinish(); // スレッドが終了するタイミングでFinishを呼んでおく。
	}

	// サーフェスがチェンジしたときの対応はしない。申し訳ないけど。
	public final void surfaceChanged(SurfaceHolder holder, int format,
			int width, int height) {
	}

	// サーフェスが生成されたときにスレッドを実行するようにする。
	public final void surfaceCreated(SurfaceHolder holder) {
		threadFlag = true;
		thread = new Thread(this);
		thread.start();
	}

	// サーフェスが削除されたときにスレッドを終了するようにする。
	public final void surfaceDestroyed(SurfaceHolder holder) {
		threadFlag = false;
	}

	// タッチイベント処理用のフィールドの初期化処理用メソッド
	private final void onTouchEventInit() {
		// touchPosListの実体をLinkedListからCopyOnWriteArrayListに変更。
		// touchPosList = new LinkedList<TouchPos>();
		touchPosList = new CopyOnWriteArrayList<TouchPos>();
		deleteTouchPosList = new CopyOnWriteArrayList<TouchPos>();
	}

	// タッチイベント発生時に呼ばれるメソッドonTouchEvent
	public final boolean onTouchEvent(MotionEvent me) {
		int pointerCount = me.getPointerCount();
		int action = me.getAction();
		int type = action & MotionEvent.ACTION_MASK;
		// 画面がタッチされたときの処理
		// タッチされたポジションの値でAGameCanvas.TouchPosオブジェクトを生成し、touchPosListに格納します。
		// ACTION_DOWNはタッチされていない状態でタッチがあった場合、ACTION_POINTER_DOWNはすでに一つ以上タッチされてる状態でタッチがあった場合
		if (type == MotionEvent.ACTION_DOWN
				|| type == MotionEvent.ACTION_POINTER_DOWN) {
			int i = me.getActionIndex();
			// int i = (me.getAction() & MotionEvent.ACTION_POINTER_ID_MASK ) >>
			// MotionEvent.ACTION_POINTER_ID_SHIFT ;

			int id = me.getPointerId(i);
			float x = me.getX(i);
			float y = me.getY(i);
			float xa = x / this.rateW;
			// float ya = y/this.rateH;
			float ya = (y - offsetY) / this.rateH;
			if (ya < 0.0f) {
				ya = 0.0f;
			}
			if (ya > this.getHeightA()) {
				ya = this.getHeightA() - 1;
			}
			float p = me.getPressure(i);
			touchPosList.add(new TouchPos(id, x, y, xa, ya, p));
		}
		// 画面にタッチされている指が動いたときの処理
		// 移動された指に対応するAGameCanvas.TouchPosオブジェクトの座標値を、移動先の座標値に書き換えます。
		else if (type == MotionEvent.ACTION_MOVE) {
			for (int i = 0; i < pointerCount; i = i + 1) {
				int id = me.getPointerId(i);
				float x = me.getX(i);
				float y = me.getY(i);
				float xa = x / this.rateW;
				// float ya = y/this.rateH;
				float ya = (y - offsetY) / this.rateH;
				if (ya < 0.0f) {
					ya = 0.0f;
				}
				if (ya > this.getHeightA()) {
					ya = this.getHeightA() - 1;
				}
				float p = me.getPressure(i);
				for (TouchPos tp : touchPosList) {
					if (tp.id == id) {
						tp.x = x;
						tp.y = y;
						tp.xa = xa;
						tp.ya = ya;
						tp.p = p;
					}
				}
			}
		}
		// 画面にタッチされている指が離されたときの処理
		// 離された指に対応するAGameCanvas.TouchPosオブジェクトを、touchPosListから削除します。
		// ACTION_UPはこれによって一つもタッチが無くなる場合、ACTION_POINTER_UPはこのタッチが離れたときにほかのタッチが残る場合
		// else if( type == MotionEvent.ACTION_UP || type ==
		// MotionEvent.ACTION_POINTER_UP ) {
		else if (type == MotionEvent.ACTION_POINTER_UP) {
			int i = me.getActionIndex();
			// int i = (me.getAction() & MotionEvent.ACTION_POINTER_ID_MASK ) >>
			// MotionEvent.ACTION_POINTER_ID_SHIFT ;
			// 削除対象用TouchPosの格納用Listを準備する
			// List<TouchPos> dtpl = new LinkedList<TouchPos>();
			// List<TouchPos> dtpl = new CopyOnWriteArrayList<TouchPos>();
			deleteTouchPosList.clear();

			// 削除対象のTouchPosオブジェクトを調べる
			int id = me.getPointerId(i);
			for (TouchPos t : touchPosList) {
				if (t.id == id) { // 一致したidがあれば
					deleteTouchPosList.add(t); // 削除対象として格納する
				}
			}
			// 削除対象用TouchPosのリストを、TouchPosListから削除する。
			touchPosList.removeAll(deleteTouchPosList);

		} else if (type == MotionEvent.ACTION_UP) {
			touchPosList.clear();
		}
		// タッチキャンセル対応（何もしないようにする）
		else if (type == MotionEvent.ACTION_CANCEL) {
		}

		return true;
	}

	/**
	 * 指定したIDのTouchPosが存在するかどうか（指定したIDのTouchPosが押されているかどうか）
	 *
	 * @param id
	 * @return
	 */
	public final boolean isTouchPos(int id) {
		if (touchPosList.isEmpty()) {
			return false; // タッチリストが空ならどのIDも押されてるわけないのでfalse
		}
		// タッチリストが空でなければ、チェックする
		for (TouchPos tp : touchPosList) {
			if (tp.id == id) { // 指定したIDのタッチを発見したら
				return true; // それは押されていることの証明なのでtrue
			}
		}
		return false; // ここまで到達するということは押されていないのでfalse
	}

	/**
	 * 指定したIDのTouchPosオブジェクトを返す（押されていない場合はnull）
	 *
	 * @param id
	 * @return
	 */
	public final TouchPos getTouchPos(int id) {
		for (TouchPos tp : touchPosList) {
			if (tp.id == id) { // 指定したIDのタッチを発見したら
				return tp; // それは押されていることの証明なのでtrue
			}
		}
		return null;
	}

	/**
	 * gcGetTouchState IDによらず、指定した場所が押されているかどうかを返す
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            （画面左部1/3） （画面中部1/3） （画面右部1/3）
	 *            =============================================== （画面上部1/3）:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]で判定可 （画面中部1/3）:
	 *            [LEFT ] [CENTER ] [RIGHT ] | （画面下部1/3）: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]で判定可
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]で判定可 [RIGHT_SIDE]で判定可
	 *
	 * @return 指定した場所が押されているかどうか
	 */
	public final boolean gcGetTouchState(Position pos) {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchState(tp.id, pos);
		}
		return flag;
	}

	/**
	 * gcGetTouchState 指定したIDのTouchPosについて指定した場所が押されているかどうかを返す
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            （画面左部1/3） （画面中部1/3） （画面右部1/3）
	 *            =============================================== （画面上部1/3）:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]で判定可 （画面中部1/3）:
	 *            [LEFT ] [CENTER ] [RIGHT ] | （画面下部1/3）: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]で判定可
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]で判定可 [RIGHT_SIDE]で判定可
	 *
	 * @return 指定した場所が押されているかどうか
	 */
	public final boolean gcGetTouchState(int id, Position pos) {
		TouchPos tp = getTouchPos(id);
		if (tp != null) {
			// 上部
			if (0 <= tp.y && tp.y < this.getHeight() / 3) {
				if (0 <= tp.x && tp.x < this.getWidth() / 3) {
					if (pos == Position.LEFT_SIDE) {
						return true;
					}
					if (pos == Position.TOP_SIDE) {
						return true;
					}
					if (pos == Position.LEFT_TOP) {
						return true;
					}
				}
				if (this.getWidth() / 3 <= tp.x
						&& tp.x < this.getWidth() / 3 * 2) {
					if (pos == Position.TOP_SIDE) {
						return true;
					}
					if (pos == Position.TOP) {
						return true;
					}
				}
				if (this.getWidth() / 3 * 2 <= tp.x && tp.x < this.getWidth()) {
					if (pos == Position.RIGHT_SIDE) {
						return true;
					}
					if (pos == Position.TOP_SIDE) {
						return true;
					}
					if (pos == Position.RIGHT_TOP) {
						return true;
					}
				}
			}
			// 中部（左，中，右）
			if (this.getHeight() / 3 <= tp.y && tp.y < this.getHeight() / 3 * 2) {
				if (0 <= tp.x && tp.x < this.getWidth() / 3) {
					if (pos == Position.LEFT_SIDE) {
						return true;
					}
					if (pos == Position.LEFT) {
						return true;
					}
				}
				if (this.getWidth() / 3 <= tp.x
						&& tp.x < this.getWidth() / 3 * 2) {
					if (pos == Position.CENTER) {
						return true;
					}
				}
				if (this.getWidth() / 3 * 2 <= tp.x && tp.x < this.getWidth()) {
					if (pos == Position.RIGHT_SIDE) {
						return true;
					}
					if (pos == Position.RIGHT) {
						return true;
					}
				}
			}
			// 下部
			if (this.getHeight() / 3 * 2 <= tp.y && tp.y < this.getHeight()) {
				if (0 <= tp.x && tp.x < this.getWidth() / 3) {
					if (pos == Position.LEFT_SIDE) {
						return true;
					}
					if (pos == Position.BOTTOM_SIDE) {
						return true;
					}
					if (pos == Position.LEFT_BOTTOM) {
						return true;
					}
				}
				if (this.getWidth() / 3 <= tp.x
						&& tp.x < this.getWidth() / 3 * 2) {
					if (pos == Position.BOTTOM_SIDE) {
						return true;
					}
					if (pos == Position.BOTTOM) {
						return true;
					}
				}
				if (this.getWidth() / 3 * 2 <= tp.x && tp.x < this.getWidth()) {
					if (pos == Position.RIGHT_SIDE) {
						return true;
					}
					if (pos == Position.BOTTOM_SIDE) {
						return true;
					}
					if (pos == Position.RIGHT_BOTTOM) {
						return true;
					}
				}
			}
		}
		// TouchPosが無い（押されていなければ）無論false
		return false;
	}

	/**
	 * gcGetTouchState 指定したIDのTouchPosが存在するか （場所によらずどこかが押されているか）を返す
	 *
	 * @param id
	 * @return 指定したIDによって、どこかが押されているかどうか
	 */
	public final boolean gcGetTouchState(int id) {
		TouchPos tp = getTouchPos(id);
		if (tp != null) {
			return true; // 指定されたIDのTouchPosオブジェクトがあれば
		}
		return false; // 指定されたIDのTouchPosオブジェクトがなければ
	}

	/**
	 * gcGetTouchState とにかくどこかが押されているかを返す
	 *
	 * @return どこかが押されているかどうか
	 */
	public final boolean gcGetTouchState() {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchState(tp.id);
		}
		return flag;
	}

	/**
	 * gcGetTouchTrigger 指定したIDのTouchPosがこのときタッチされたのかどうかを返す
	 *
	 * @param id
	 * @return
	 */
	public final boolean gcGetTouchTrigger(int id) {
		boolean flag = this.gcGetTouchState(id);
		if (flag) {
			TouchPos tp = this.getTouchPos(id);
			if (tp != null) {
				long sTime = tp.sTime;
				if (System.nanoTime() - sTime <= this.getWaitTime()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * gcGetTouchTrigger とにかくどこかがいまタッチされたのかを返す
	 *
	 * @return
	 */
	public final boolean gcGetTouchTrigger() {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchTrigger(tp.id);
		}
		return flag;
	}

	/**
	 * gcGetTouchTrigger IDによらず、指定した場所がそのとき押されたのかどうかを返す
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            （画面左部1/3） （画面中部1/3） （画面右部1/3）
	 *            =============================================== （画面上部1/3）:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]で判定可 （画面中部1/3）:
	 *            [LEFT ] [CENTER ] [RIGHT ] | （画面下部1/3）: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]で判定可
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]で判定可 [RIGHT_SIDE]で判定可
	 *
	 * @return 指定した場所がこのタイミングで押されたのかどうか
	 */
	public final boolean gcGetTouchTrigger(Position pos) {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchTrigger(tp.id, pos);
		}
		return flag;
	}

	/**
	 * gcGetTouchTrigger 指定したIDのTouchPosについて指定した場所がそのとき押されたのかどうかを返す
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            （画面左部1/3） （画面中部1/3） （画面右部1/3）
	 *            =============================================== （画面上部1/3）:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]で判定可 （画面中部1/3）:
	 *            [LEFT ] [CENTER ] [RIGHT ] | （画面下部1/3）: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]で判定可
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]で判定可 [RIGHT_SIDE]で判定可
	 *
	 * @return 指定した場所がこのタイミングで押されたのかどうか
	 */
	public final boolean gcGetTouchTrigger(int id, Position pos) {
		boolean flag = gcGetTouchState(id, pos); // とりあえず押されているのかどうか
		if (flag) { // 押されていた場合に、それがいつ押されたのかを調べる
			TouchPos tp = this.getTouchPos(id);
			if (tp != null) {
				long sTime = tp.sTime;
				if (System.nanoTime() - sTime <= getWaitTime()) { // そのフレーム内に押されていたらＯＫ
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 現在のTouchPosオブジェクトを格納したリストを返す
	 *
	 * @return AGameCanvasが現在保持しているTouchPosを格納したListオブジェクト
	 */
	public List<TouchPos> getTouchPosList() {
		return touchPosList;
	}

	private List<TouchPos> touchPosList;
	private List<TouchPos> deleteTouchPosList;

	/** AGameCanvas.TouchPos AGameCanvasにおいて、画面にタッチされているポジションを表すクラス */
	public class TouchPos {
		/** TouchPosオブジェクトの管理用IDです。 */
		private int id;

		/** TouchPosオブジェクトの管理用IDのgetterです。 */
		public int getId() {
			return id;
		}

		/** TouchPosオブジェクトのｘ座標値です。 */
		private float x;

		/** TouchPosオブジェクトのｘ座標値のgetterです。 */
		public float getX() {
			return x;
		}

		/** TouchPosオブジェクトのｙ座標値です。 */
		private float y;

		/** TouchPosオブジェクトのｙ座標値のgetterです。 */
		public float getY() {
			return y;
		}

		/** TouchPosオブジェクトの描画調整ｘ座標値です。 */
		private float xa;

		/** TouchPosオブジェクトの描画調整ｘ座標値のgetterです。 */
		public float getXA() {
			return xa;
		}

		/** TouchPosオブジェクトの描画調整ｙ座標値です。 */
		private float ya;

		/** TouchPosオブジェクトの描画調整ｙ座標値のgetterです。 */
		public float getYA() {
			return ya;
		}

		/** TouchPosオブジェクトの押されている強さ（pressure）です。端末によっては固定値になります。 */
		private float p;

		/** TouchPosオブジェクトの押されている強さ（pressure）のgetterです。 */
		public float getPressure() {
			return p;
		}

		/**
		 * TouchPosオブジェクトの押されている強さ（pressure）のgetterです。使用制限しています。
		 * getPressureを使用してください。
		 */
		@Deprecated
		public float getP() {
			return getPressure();
		}

		/* TouchPosオブジェクトの押されている強さ（pressure）のsetterです。 */
		// public void setPressure(float p) { this.p = p; }
		// public void setP(float p) { setPressure(p); }

		/** TouchPosオブジェクトの押された時刻sTime（ns）です。 */
		private long sTime;

		/** TouchPosオブジェクトが生成された時刻（sTime）のgetterです。 */
		public float getSTime() {
			return sTime;
		}

		/** TouchPosオブジェクトが生成された時刻（sTime）のgetterです。使用制限しています。getSTimeを使用してください。 */
		@Deprecated
		public float getST() {
			return getSTime();
		}

		/* TouchPosオブジェクトが生成された時刻（sTime）のsetterです。 */
		// public void setSTime(long st) { this.sTime = st; }
		// public void setST(long st) { setSTime(st); }

		/**
		 * AGameCanvas.TouchPos コンストラクタ 第１引数には、IDを指定します。 第２引数には、ｘ座標値を指定します。
		 * 第３引数には、ｙ座標値を指定します。 第４引数には、描画調整ｘ座標値を指定します。 第５引数には、描画調整ｙ座標値を指定します。
		 * 第６引数には、強さを指定します。
		 */
		public TouchPos(int id, float x, float y, float xa, float ya, float p) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.xa = xa;
			this.ya = ya;
			this.p = p;
			this.sTime = System.nanoTime();
		}

		/** toString TouchPosオブジェクトの文字列表現（ID、x、y）を返します。 */
		public String toString() {
			return "ID: " + id + " [X: " + (int) x + " ,Y: " + (int) y + " ]"
					+ " [xA: " + (int) xa + " ,yA: " + (int) ya + " ]";
		}
	}

	/**
	 * AGameCanvas.Position AGameCanvasにおいて、タッチされている場所を表すための列挙クラス
	 */
	public enum Position {
		/** TOP_SIDE 画面上部を表します */
		TOP_SIDE,
		/** RIGHT_SIDE 画面右部を表します */
		RIGHT_SIDE,
		/** BOTTOM_SIDE 画面下部を表します */
		BOTTOM_SIDE,
		/** LEFT_SIDE 画面左部を表します */
		LEFT_SIDE,
		/** CENTER 画面中央部を表します */
		CENTER,
		/** TOP 画面、上中央を表します */
		TOP,
		/** RIGHT_TOP 画面、右上を表します */
		RIGHT_TOP,
		/** RIGHT 画面、右中央を表します */
		RIGHT,
		/** RIGHT_BOTTOM 画面、右下部を表します */
		RIGHT_BOTTOM,
		/** BOTTOM 画面、下中央を表します */
		BOTTOM,
		/** LEFT_BOTTOM 画面、左下を表します */
		LEFT_BOTTOM,
		/** LEFT 画面、左中央を表します */
		LEFT,
		/** LEFT_TOP 画面、左上を表します */
		LEFT_TOP,
	}

	/**
	 * AGameCanvas.ScreenOrientation
	 * AGameCanvasにおいて、縦持ち（PORTRAIT）か横持ち（LANDSCAPE）かを設定するための列挙クラス
	 */
	public enum ScreenOrientation {
		/** 縦持ち設定 */
		PORTRAIT,
		/** 横持ち設定 */
		LANDSCAPE,
	}

	/**
	 * 引数で指定したメッセージ付きのダイアログを表示します。
	 *
	 * @param message
	 *            表示したいメッセージを指定します。
	 */
	public void showMessageDialog(final String message) {
		showMessageDialog(message, "Back to the Game.");
	}

	/**
	 * 引数で指定したメッセージ付きのダイアログを表示します。
	 *
	 * @param message
	 *            表示したいメッセージを指定します。
	 * @param backMessage
	 *            ゲームに戻るためのボタンに表示されるメッセージを指定します。
	 */
	public void showMessageDialog(final String message, final String backMessage) {
		onPause();
		final AgcActivity activity = (AgcActivity) this.getContext();
		activity.postRunnable(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder adb = new AlertDialog.Builder(activity);
				adb.setTitle(message);
				adb.setCancelable(false); // アラートダイアログからバックキーの処理を排除
				adb.setNegativeButton(backMessage,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								offPause();
							}
						});
				AlertDialog ad = adb.create(); // ダイアログを生成
				ad.show(); // ダイアログの表示
			}
		});
	}

	private String inputDialogText = "";

	/**
	 * メッセージ付き入力用ダイアログshowInputDialogによって入力されたテキストを返します。
	 *
	 * @return メッセージ付き入力用ダイアログshowInputDialogによって入力されたテキストを返します。
	 */
	public String getInputDialogText() {
		return inputDialogText;
	}

	/**
	 * メッセージ付き入力用ダイアログを表示します。 入力ダイアログのテキストはinputDialogTextに保存されます。
	 * 正しい入力またはYesに相当するボタンが押された場合はテキストボックスの文字列がそのまま入ります。
	 * 不正な入力またはNoに相当するボタンが押された場合は空文字列""が入ります。 getInputDialogTextで取得できます。
	 *
	 * @param message
	 *            入力を促すメッセージを指定します。
	 * @param yesMessage
	 *            Yesのボタンに表示されるメッセージを指定します。
	 * @param noMessage
	 *            Noのボタンに表示されるメッセージを指定します。
	 */
	public void showInputDialog(final String message, final String yesMessage,
			final String noMessage) {
		onPause();
		final AgcActivity activity = (AgcActivity) this.getContext();
		// final EditText et = new EditText(activity);
		activity.postRunnable(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder adb = new AlertDialog.Builder(activity);
				adb.setTitle(message);
				final EditText et = new EditText(activity);
				adb.setView(et);
				adb.setCancelable(false); // アラートダイアログからバックキーの処理を排除
				adb.setPositiveButton(yesMessage,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								AGameCanvas.this.inputDialogText = et.getText()
										.toString();
								offPause();
							}
						});
				adb.setNegativeButton(noMessage,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								AGameCanvas.this.inputDialogText = "";
								offPause();
							}
						});
				AlertDialog ad = adb.create(); // ダイアログを生成
				ad.show(); // ダイアログの表示
			}
		});
	}

	/**
	 * メッセージ付き入力用ダイアログを表示します。 入力ダイアログのテキストはinputDialogTextに保存されます。
	 * 正しい入力またはYesに相当するボタンが押された場合はテキストボックスの文字列がそのまま入ります。
	 * 不正な入力またはNoに相当するボタンが押された場合は空文字列""が入ります。 getInputDialogTextで取得できます。
	 *
	 * @param message
	 *            入力を促すメッセージを指定します。
	 */
	public void showInputDialog(String message) {
		showInputDialog(message, "YES", "NO");
	}
}
