package net.pddin.agc;

import android.graphics.Bitmap;
import android.graphics.Paint;

/**
 * Imageクラス（AndroidGameCanvas用） DoJa(i-mode
 * Java)のImageクラスライクなBitmap&Paintラッパークラス
 *
 * @author HARADA Toshinobu
 * @version 1.0 (H23/06/10)
 */
/*
 * 以下、未実装メソッド
 *
 * static Image createImage(int width, int height) イメージを新たに生成します。
 *
 * static Image createImage(int width, int height, int[] data, int off)
 * RGB配列を指定してイメージオブジェクトを生成します。
 *
 * Graphics getGraphics() イメージに描画するためのグラフィックスオブジェクトを取得します。
 *
 * int getTransparentColor() setTransparentColor(int) にて指定されている透過色を取得します。
 *
 * void setTransparentColor(int color) 透過色を指定します。
 *
 * void setTransparentEnabled(boolean enabled) 透過色指定の有効・無効を設定します。
 */
public class Image {
	private Bitmap bmp;
	private Paint paint;

	/**
	 * Imageコンストラクタ 引数で指定したBitmapオブジェクトをもとにImageオブジェクトを生成します
	 *
	 * @param bmp
	 */
	public Image(Bitmap bmp) {
		this.bmp = bmp;
		this.paint = new Paint(); // Imageオブジェクトには固有のpaintオブジェクトを持たせておく。それぞれにα値などを設定するため。
	}

	/**
	 * getHeight イメージオブジェクトの縦のサイズ（Height）を返します 内包するBitmapオブジェクトの縦のサイズをそのまま返します。
	 *
	 * @return height（縦のサイズ）
	 */
	public int getHeight() {
		return this.bmp.getHeight();
	}

	/**
	 * getWidth イメージオブジェクトの横のサイズ（width）を返します 内包するBitmapオブジェクトの横のサイズをそのまま返します。
	 *
	 * @return width（横のサイズ）
	 */
	public int getWidth() {
		return this.bmp.getWidth();
	}

	public Bitmap getBitmap() {
		return this.bmp;
	}

	/**
	 * setAlpha イメージオブジェクトにα値を0～255の範囲で設定します。
	 *
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		this.paint.setAlpha(alpha);
	}

	/**
	 * getAlpha 現在設定されているα値を返します。
	 *
	 * @return
	 */
	public int getAlpha() {
		return this.paint.getAlpha();
	}

	public Paint getPaint() {
		return this.paint;
	}

	// 一応 データ破棄用のメソッドを作っておく。
	/**
	 * dispose 不要になったImageオブジェクトを処分するときに呼び出します。
	 */
	public void dispose() {
		this.bmp.recycle(); // 使用していたbmpを解放
		this.paint = null; // 一応ペイントも解放（こちらはnullクリアで良しとする。）
	}
}
